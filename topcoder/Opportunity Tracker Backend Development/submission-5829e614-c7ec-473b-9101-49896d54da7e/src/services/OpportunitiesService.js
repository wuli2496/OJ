/**
 * Service for users endpoints
 */
const Joi = require('joi')
const config = require('config')
const { Op } = require('sequelize')
const models = require('../models')
const logger = require('../common/logger')
const errors = require('../common/errors')
const _ = require('lodash')
const model = models.Opportunity

/**
 * Get opportunities.
 * @params currentUser the user who perform this operation
 * @returns the opportunities list.
 */
async function searchOpportunities (currentUser, criteria) {
  logger.info({ component: 'OpportunitiesService', context: 'searchJobs', message: 'Searching from DB' })
  const page = criteria.page > 0 ? criteria.page : 1
  const perPage = criteria.perPage > 0 ? criteria.perPage : 20
  if (!criteria.sortBy) {
    criteria.sortBy = 'id'
  } else {
    criteria.sortBy = criteria.sortBy === 'ProductName' ? 'name' : criteria.sortBy
    criteria.sortBy = criteria.sortBy === 'Company' ? 'company' : criteria.sortBy
    criteria.sortBy = criteria.sortBy === 'LastUpdated' ? 'updatedOn' : criteria.sortBy
  }
  if (!criteria.sortOrder) {
    criteria.sortOrder = 'desc'
  }
  const filter = { [Op.and]: [] }
  if (criteria.productName) {
    filter[Op.and].push({ name: criteria.productName })
  }
  if (criteria.companyName) {
    filter[Op.and].push({ company: criteria.companyName })
  }
  if (criteria.technologyTheme) {
    filter[Op.and].push({ techThemeId: criteria.technologyTheme })
  }
  if (criteria.lastUpdatedStart) {
    filter[Op.and].push({ updatedOn: { [Op.gte]: new Date(criteria.lastUpdatedStart) } })
  }
  if (criteria.lastUpdatedEnd) {
    filter[Op.and].push({ updatedOn: { [Op.lte]: new Date(criteria.lastUpdatedEnd) } })
  }
  if (criteria.owner) {
    const user = await models.User.findByName(criteria.owner)
    filter[Op.and].push({ ownerId: user.id })
  }
  if (criteria.tags) {
    const oppTags = await models.OpportunityTag.findAll({ where: { tag_id: criteria.tags } })
    filter[Op.and].push({ id: _.map(oppTags, ot => (ot.opportunity_id)) })
  }
  if (criteria.status) {
    const phases = await models.OpportunityPhase.findAll({ where: { status: criteria.status } })
    filter[Op.and].push({ id: _.map(phases, ot => (ot.opportunityId)) })
  }
  const results = await model.findAll({
    where: filter,
    offset: ((page - 1) * perPage),
    limit: perPage,
    order: [[criteria.sortBy, criteria.sortOrder]]
  })
  const result = []
  for (const entity of results) {
    result.push(await getOpportunityDetails(currentUser, entity.id))
  }
  return {
    total: results.length,
    page,
    perPage,
    result: result
  }
}

searchOpportunities.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  criteria: Joi.object().keys({
    page: Joi.number().integer(),
    perPage: Joi.number().integer(),
    sortBy: Joi.string().valid('ProductName', 'Company', 'TechTheme', 'Status', 'LastUpdated', 'Owner',
      'Tags', 'Views'),
    sortOrder: Joi.string().valid('desc', 'asc'),
    status: Joi.string().valid('UnderEvaluation', 'EvaluationPass', 'EvaluationFail', 'InPilotTest',
      'PilotTestPass', 'PilotTestFail', 'Implemented'),
    productName: Joi.string(),
    companyName: Joi.string(),
    technologyTheme: Joi.alternatives(
      Joi.string(),
      Joi.array().items(Joi.number().integer())
    ),
    lastUpdatedStart: Joi.date(),
    lastUpdatedEnd: Joi.date(),
    owner: Joi.string(),
    tags: Joi.alternatives(
      Joi.string(),
      Joi.array().items(Joi.number().integer())
    )
  }).required()
}).required()

/**
 * Create or update the basic Opportunity details
 * @param entity the entity to be created
 * @param t the transaction
 * @returns the created entity
 */
async function _createOrUpdateOpportunityDetails (entity, t, update = false) {
  try {
    const techTheme = await models.TechTheme.findById(entity.techThemeId)
    const source = await models.Source.findById(entity.sourceId)

    const created = update
      ? (await model.update(entity, { returning: true, where: { id: entity.id }, transaction: t }))[1][0]
      : await model.create(entity, { transaction: t })
    created.dataValues.technologyTheme = techTheme
    created.dataValues.source = source
    const oppId = created.id
    // creating tag
    const tags = []
    if (entity.tags) {
      for (const tagId of entity.tags) {
        const tag = await models.Tag.findById(tagId)
        await models.OpportunityTag.create({ opportunity_id: oppId, tag_id: tagId }, { transaction: t })
        tags.push(tag)
      }
    }
    created.dataValues.tags = tags
    // creating documents
    const documents = []
    if (entity.supportingDocuments) {
      for (const documentId of entity.supportingDocuments) {
        const document = await models.Document.findById(documentId)
        await models.OpportunityDocument.create({
          opportunity_id: oppId,
          document_id: documentId
        }, { transaction: t })
        documents.push(document)
      }
    }
    created.dataValues.documents = documents
    // creating links
    const links = []
    if (entity.usefulLinks) {
      for (const linkUrl of entity.usefulLinks) {
        const link = await models.OpportunityLink.create({
          opportunityId: oppId,
          link: linkUrl
        }, { transaction: t })
        links.push(link)
      }
    }
    created.dataValues.links = links

    return created
  } catch (error) {
    if (error.httpStatus === 404) {
      throw new errors.BadRequestError(error.message)
    }
    throw error
  }
}

/**
 * Delete related tags, documents, links of a given Opportunity.
 * @params currentUser the user who perform this operation
 * @params id the Opportunity id
 * @param t current transaction
 */
async function _deleteOpportunityDetailsRelated (currentUser, id, t) {
  const exist = await model.findById(id, true, true)
  if (currentUser !== exist.ownerId) {
    throw new errors.UnauthorizedError(`You are not the owner of id: ${id} "Opportunity".`)
  }
  // delete related tags, documents, links
  await models.OpportunityTag.destroy({ where: { opportunity_id: id } }, { transaction: t })
  await models.OpportunityDocument.destroy({ where: { opportunity_id: id } }, { transaction: t })
  await models.OpportunityLink.destroy({ where: { opportunityId: id } }, { transaction: t })
}

/**
 * Create Opportunity.
 * @params currentUser the user who perform this operation
 * @params entity the opportunity to be created
 * @returns the created opportunity
 */
async function createOpportunity (currentUser, entity) {
  try {
    return await models.sequelize.transaction(async (t) => {
      entity.createdBy = currentUser
      entity.ownerId = currentUser
      const created = await _createOrUpdateOpportunityDetails(entity, t)

      // The initial phase for the opportunity should also be created and set to "UnderEvaluation".
      await models.OpportunityPhase.create({
        opportunityId: created.id,
        status: config.INITIAL_PHASE_STATUS,
        startDate: created.createdOn
      }, { transaction: t })
      created.dataValues = _.omit(created.dataValues,
        ['techThemeId', 'sourceId', 'ownerId', 'createdBy', 'createdOn', 'updatedBy'])
      return created.dataValues
    })
  } catch (error) {
    logger.logFullError(error, { component: 'OpportunitiesService', context: 'createOpportunity' })
    throw error
  }
}

const opportunityDetailsSchema = Joi.object().keys({
  name: Joi.string().trim().required().max(256),
  techThemeId: Joi.number().integer().required(),
  sourceId: Joi.number().integer().required(),
  company: Joi.string().trim().required().max(256),
  description: Joi.string().trim().required(),
  tags: Joi.array().items(Joi.number().integer().required()).unique(),
  supportingDocuments: Joi.array().items(Joi.number().integer().required()).unique(),
  usefulLinks: Joi.array().items(Joi.string().uri().required()).unique()
}).required()

createOpportunity.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  entity: opportunityDetailsSchema
}).required()

/**
 * Get Opportunity by id.
 * @params currentUser the user who perform this operation
 * @params id the opportunity id
 * @returns the opportunity found
 */
async function getOpportunity (currentUser, id) {
  // the discussion posts should only contain top-level posts.
  return await model.findById(id)
}

getOpportunity.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required()
}).required()

/**
 * Delete Opportunity by id.
 * @params currentUser the user who perform this operation
 * @params id the opportunity id
 * @returns the opportunity to be deleted
 */
async function deleteOpportunity (currentUser, id) {
  try {
    await models.sequelize.transaction(async (t) => {
      // delete related tags, documents, links
      await _deleteOpportunityDetailsRelated(currentUser, id, t)
      // delete views relationship
      await models.OpportunityViews.destroy({ where: { opportunity_id: id } }, { transaction: t })
      // delete members relationship
      await models.OpportunityMembers.destroy({ where: { opportunity_id: id } }, { transaction: t })
      // delete related phases
      await models.OpportunityPhase.destroy({ where: { opportunityId: id } }, { transaction: t })
      // delete related posts
      await models.DiscussionPost.destroy({ where: { opportunityId: id } }, { transaction: t })
      // delete the opportunity
      await model.destroy({ where: { id: id } }, { transaction: t })
    })
  } catch (error) {
    logger.logFullError(error, { component: 'OpportunitiesService', context: 'deleteOpportunity' })
    throw error
  }
}

deleteOpportunity.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required()
}).required()

/**
 * Get Opportunity details by id.
 * @params currentUser the user who perform this operation
 * @params id the opportunity id
 * @returns the opportunity details
 */
async function getOpportunityDetails (currentUser, id) {
  return await model.findById(id, false, true)
}

getOpportunityDetails.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required()
}).required()

/**
 * Update Opportunity details.
 * @params currentUser the user who perform this operation
 * @params id the Opportunity id
 * @params entity the opportunity to be updated
 * @returns the updated opportunity details
 */
async function updateOpportunityDetails (currentUser, id, entity) {
  try {
    await models.sequelize.transaction(async (t) => {
      await _deleteOpportunityDetailsRelated(currentUser, id, t)
      entity.updatedBy = currentUser
      entity.ownerId = currentUser
      entity.id = id
      await _createOrUpdateOpportunityDetails(entity, t, true)
    })
    return await model.findById(id, false, true)
  } catch (error) {
    logger.logFullError(error, { component: 'OpportunitiesService', context: 'updateOpportunity' })
    throw error
  }
}

updateOpportunityDetails.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required(),
  entity: opportunityDetailsSchema
}).required()

/**
 * Get Opportunity Phases by id.
 * @params currentUser the user who perform this operation
 * @params id the opportunity id
 * @returns the opportunity details
 */
async function getOpportunityPhases (currentUser, id) {
  // check existence of the Opportunity
  await model.findById(id, true, true)
  // get opportunity phases
  const phases = await models.OpportunityPhase.findAll({
    where: { opportunityId: { [Op.eq]: id } }
  })
  return _.map(_.map(phases, 'dataValues'), p => (_.assign(p, { isActive: !p.endDate })))
}

getOpportunityPhases.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required()
}).required()

/**
 * Update Opportunity Phases.
 * @params currentUser the user who perform this operation
 * @params id the Opportunity id
 * @params entity the phase to be updated
 * @returns the updated opportunity details
 */
async function updateOpportunityPhases (currentUser, id, entity) {
  // check existence of the Opportunity
  await model.findById(id, true, true)
  try {
    await models.sequelize.transaction(async (t) => {
      const phases = await models.OpportunityPhase.findAll({
        where: { opportunityId: { [Op.eq]: id } }
      })
      // Per https://sequelize.org/master/class/lib/model.js~Model.html#static-method-bulkCreate
      // MSSQL does not support bulk update.
      for (const phase of phases) {
        phase.dataValues.endDate =
          phase.dataValues.endDate ? phase.dataValues.endDate : models.sequelize.fn('getdate')
        await models.OpportunityPhase.update(phase.dataValues,
          { returning: true, where: { id: phase.dataValues.id }, transaction: t })
      }
      await models.OpportunityPhase.create({
        opportunityId: id,
        status: entity.status,
        startDate: models.sequelize.fn('getdate')
      }, { transaction: t })
    })
    return await getOpportunityPhases(currentUser, id)
  } catch (error) {
    logger.logFullError(error, { component: 'OpportunitiesService', context: 'updateOpportunityPhases' })
    throw error
  }
}

updateOpportunityPhases.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required(),
  entity: Joi.object().keys({
    status: Joi.string().valid('UnderEvaluation', 'EvaluationPass', 'EvaluationFail', 'InPilotTest',
      'PilotTestPass', 'PilotTestFail', 'Implemented')
  }).required()
}).required()

/**
 * Get Opportunity members by id.
 * @params currentUser the user who perform this operation
 * @params id the opportunity id
 * @returns the opportunity members
 */
async function getOpportunityMembers (currentUser, id) {
  // check existence of the Opportunity
  const exist = await model.findById(id, true, true)
  // get opportunity members
  const oppMembers = await models.OpportunityMembers.findAll({
    where: { opportunity_id: { [Op.eq]: id } }
  })
  const owner = await models.User.findById(exist.ownerId)
  const members = []
  for (const oppMember of oppMembers) {
    members.push(await models.User.findById(oppMember.dataValues.user_id))
  }
  return { owner: owner, members: members }
}

getOpportunityMembers.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required()
}).required()

/**
 * Update Opportunity Members.
 * @params currentUser the user who perform this operation
 * @params id the Opportunity id
 * @params entity the members to be updated
 * @returns the updated opportunity members
 */
async function updateOpportunityMembers (currentUser, id, entity) {
  // check existence of the Opportunity
  await model.findById(id, true, true)
  try {
    await models.sequelize.transaction(async (t) => {
      // remove all members first
      await models.OpportunityMembers.destroy({ where: { opportunity_id: id } }, { transaction: t })
      for (const userId of entity.members) {
        // check the existence of the user
        await models.User.findById(userId)
        await models.OpportunityMembers.create({ opportunity_id: id, user_id: userId }, { transaction: t })
      }
    })
  } catch (error) {
    logger.logFullError(error, { component: 'OpportunitiesService', context: 'updateOpportunity' })
    if (error.httpStatus === 404) {
      throw new errors.BadRequestError(error.message)
    }
    throw error
  }
}

updateOpportunityMembers.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  id: Joi.number().integer().required(),
  entity: Joi.object().keys({
    members: Joi.array().items(Joi.number().integer().required()).unique().required()
  }).required()
}).required()

module.exports = {
  searchOpportunities,
  createOpportunity,
  getOpportunity,
  deleteOpportunity,
  getOpportunityDetails,
  updateOpportunityDetails,
  getOpportunityPhases,
  updateOpportunityPhases,
  getOpportunityMembers,
  updateOpportunityMembers
}
