/**
 * The Opportunity service
 */
const Joi = require('joi')
const errors = require('../helpers/errors')
const _ = require('lodash')
const config = require('config')
const {
  Opportunity, User, TechTheme, Source, Tag, Document, OpportunityTag,
  OpportunityDocument, OpportunityLink, OpportunityPhase, DiscussionPost,
  OpportunityMembers, Sequelize, sequelize
} = require('../models')
const Op = Sequelize.Op

/**
  * Find and return the user
  * @param {string} username the okta_username
  */
async function _checkIfUserExistByUsername (username) {
  const user = await User.findOne({ where: { oktaUsername: username }, raw: true })
  if (_.isNil(user)) {
    throw new errors.NotFoundError(`user with username ${username} doesn't exist`)
  }
  return user
}

/**
  * Create a new opportunity
  * @param {Object} currentUser the user who perform this operation.
  * @param {Object} opportunity the opportunity data
  */
async function createOpportunity (currentUser, opportunity) {
  const owner = await _checkIfUserExistByUsername(currentUser.username)
  const technologyTheme = await TechTheme.findById(opportunity.technologyTheme)
  const source = await Source.findById(opportunity.source)
  let tags = []
  if (opportunity.tags) {
    tags = await Tag.findAll({
      where: {
        id: opportunity.tags
      },
      raw: true
    })
    if (tags.length !== opportunity.tags.length) {
      const nonexistentTags = _.differenceBy(opportunity.tags, _.map(tags, 'id'))
      throw new errors.NotFoundError(`tag ids ${nonexistentTags} not found`)
    }
  }
  let supportingDocuments = []
  if (opportunity.supportingDocuments) {
    supportingDocuments = await Document.findAll({
      where: {
        id: opportunity.supportingDocuments
      },
      raw: true
    })
    if (supportingDocuments.length !== opportunity.supportingDocuments.length) {
      const nonexistentSupportingDocuments = _.differenceBy(opportunity.supportingDocuments, _.map(supportingDocuments, 'id'))
      throw new errors.NotFoundError(`document ids ${nonexistentSupportingDocuments} not found`)
    }
  }
  const transaction = await sequelize.transaction()
  let newOpportunity
  try {
    newOpportunity = await Opportunity.create({
      name: opportunity.name,
      tech_theme_id: opportunity.technologyTheme,
      sourceId: opportunity.source,
      description: opportunity.description,
      company: opportunity.company,
      owner_id: owner.id,
      createdBy: owner.id
    }, { transaction })
    await OpportunityTag.bulkCreate(_.reduce(opportunity.tags, (acc, tag) => {
      acc.push({ opportunity_id: newOpportunity.dataValues.id, tag_id: tag })
      return acc
    }, []), { transaction })
    await OpportunityDocument.bulkCreate(_.reduce(opportunity.supportingDocuments, (acc, doc) => {
      acc.push({ opportunity_id: newOpportunity.dataValues.id, document_id: doc })
      return acc
    }, []), { transaction })
    await OpportunityLink.bulkCreate(_.reduce(opportunity.usefulLinks, (acc, link) => {
      acc.push({ opportunityId: newOpportunity.dataValues.id, link })
      return acc
    }, []), { transaction })
    await OpportunityPhase.create({
      opportunityId: newOpportunity.dataValues.id,
      status: 'UnderEvaluation',
      startDate: new Date().toISOString()
    }, { transaction })
    await transaction.commit()
  } catch (err) {
    await transaction.rollback()
    throw err
  }

  return {
    id: newOpportunity.dataValues.id,
    name: newOpportunity.dataValues.name,
    updatedOn: newOpportunity.dataValues.updatedOn,
    technologyTheme: {
      icon: technologyTheme.dataValues.icon,
      name: technologyTheme.dataValues.name
    },
    company: newOpportunity.dataValues.company,
    source: source.dataValues,
    description: newOpportunity.dataValues.description,
    tags: _.map(tags, 'label'),
    documents: _.map(supportingDocuments, doc => _.omit(doc, 'id')),
    links: _.isUndefined(opportunity.usefulLinks) ? [] : opportunity.usefulLinks
  }
}

createOpportunity.schema = Joi.object({
  currentUser: Joi.object().required(),
  opportunity: Joi.object().keys({
    name: Joi.string().required().max(256),
    technologyTheme: Joi.number().integer().min(1).required(),
    company: Joi.string().required().max(128),
    source: Joi.number().integer().min(1).required(),
    description: Joi.string().required(),
    tags: Joi.array().items(Joi.number().integer().min(1)),
    supportingDocuments: Joi.array().items(Joi.number().integer().min(1)),
    usefulLinks: Joi.array().items(Joi.string())
  }).required()
})

/**
  * Search Opportunities
  * @param {Object} currentUser the user who perform this operation.
  * @param {Object} criteria the search criteria
  */
async function searchOpportunities (currentUser, criteria) {
  const statusQuery = Sequelize.literal(`(SELECT TOP 1 op.status FROM ${config.DB_SCHEMA}.opportunity_phase op WHERE op.opportunity_id=opportunity.id AND op.end_date IS NULL ORDER BY op.start_date DESC)`)
  const viewCountQuery = Sequelize.literal(`(SELECT COUNT(1) FROM ${config.DB_SCHEMA}.opportunity_views ov WHERE ov.opportunity_id=opportunity.id)`)
  const query = {
    where: { [Op.and]: [] },
    offset: ((criteria.page - 1) * criteria.perPage),
    limit: criteria.perPage,
    attributes: ['id', 'description', 'name', 'company',
      [statusQuery, 'status'],
      ['updated_on', 'lastUpdatedOn']],
    include: [{
      model: TechTheme,
      as: 'technologyTheme',
      required: false,
      attributes: ['icon', 'name']
    },
    {
      model: User,
      as: 'owner',
      required: false,
      attributes: ['id', 'name', 'profilePicture']
    },
    {
      model: Tag,
      as: 'tags',
      required: false,
      through: { attributes: [] }
    }]
  }

  if (criteria.status) {
    query.include.push(
      {
        model: OpportunityPhase,
        as: 'phases',
        required: true,
        where: { endDate: null, status: criteria.status },
        attributes: []
      })
  }
  if (criteria.productName) {
    query.where[Op.and].push({ name: criteria.productName })
  }
  if (criteria.companyName) {
    query.where[Op.and].push({ company: criteria.companyName })
  }
  if (criteria.technologyTheme) {
    // `criteria`.technologyTheme` could be array of ids, or comma separated string of ids
    // in case it's comma separated string of ids we have to convert it to an array of ids
    if ((typeof criteria.technologyTheme) === 'string') {
      criteria.technologyTheme = criteria.technologyTheme.trim().split(',').map(technologyThemeRaw => {
        const technologyThemeRawTrimmed = technologyThemeRaw.trim()
        const technologyTheme = Number(technologyThemeRawTrimmed)
        if (_.isNaN(technologyTheme)) {
          throw new errors.BadRequestError(`technologyTheme id ${technologyThemeRawTrimmed} is not a valid number`)
        }
        return technologyTheme
      })
    }
    query.where[Op.and].push({ tech_theme_id: criteria.technologyTheme })
  }
  if (criteria.lastUpdatedStart) {
    query.where[Op.and].push({ updatedOn: { [Op.gte]: criteria.lastUpdatedStart } })
  }
  if (criteria.lastUpdatedEnd) {
    query.where[Op.and].push({ updatedOn: { [Op.lte]: criteria.lastUpdatedEnd } })
  }
  if (criteria.owner) {
    query.include[1].required = true
    query.include[1].where = { name: criteria.owner }
  }
  if (criteria.tags) {
    // `criteria`.tags` could be array of ids, or comma separated string of ids
    // in case it's comma separated string of ids we have to convert it to an array of ids
    if ((typeof criteria.tags) === 'string') {
      criteria.tags = criteria.tags.trim().split(',').map(tagRaw => {
        const tagRawTrimmed = tagRaw.trim()
        const tag = Number(tagRawTrimmed)
        if (_.isNaN(tag)) {
          throw new errors.BadRequestError(`tag id ${tagRawTrimmed} is not a valid number`)
        }
        return tag
      })
    }
    query.where[Op.and].push(Sequelize.literal(`EXISTS (SELECT 1 FROM ${config.DB_SCHEMA}.opportunity_tag ot WHERE ot.opportunity_id=opportunity.id AND ot.tag_id IN (${criteria.tags}))`))
  }
  criteria.sortBy = criteria.sortBy || 'id'
  criteria.sortOrder = criteria.sortOrder || 'desc'

  const orderMap = {
    id: ['id', criteria.sortOrder],
    ProductName: ['name', criteria.sortOrder],
    Company: ['company', criteria.sortOrder],
    TechTheme: [{ model: TechTheme, as: 'technologyTheme' }, 'name', criteria.sortOrder],
    Status: [statusQuery, criteria.sortOrder],
    LastUpdated: ['updated_on', criteria.sortOrder],
    Owner: [{ model: User, as: 'owner' }, 'name', criteria.sortOrder],
    Tags: [{ model: Tag, as: 'tags' }, 'label', criteria.sortOrder],
    Views: [viewCountQuery, criteria.sortOrder]

  }
  query.order = [orderMap[criteria.sortBy]]
  const res = await Opportunity.findAndCountAll(query)
  return { total: res.count, result: res.rows, page: criteria.page, perPage: criteria.perPage }
}

searchOpportunities.schema = Joi.object({
  currentUser: Joi.object().required(),
  criteria: Joi.object().keys({
    page: Joi.page(),
    perPage: Joi.perPage(),
    sortBy: Joi.sortBy(),
    sortOrder: Joi.sortOrder(),
    status: Joi.opportunityStatus(),
    productName: Joi.string(),
    companyName: Joi.string(),
    technologyTheme: Joi.alternatives(
      Joi.string(),
      Joi.array().items(Joi.number().integer().min(1))
    ),
    lastUpdatedStart: Joi.date(),
    lastUpdatedEnd: Joi.date(),
    owner: Joi.string(),
    tags: Joi.alternatives(
      Joi.string(),
      Joi.array().items(Joi.number().integer().min(1))
    )
  }).required()
})

/**
  * Get Opportunity with complete details by id
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the opportunity ID
  */
async function getOpportunityCompleteById (currentUser, id) {
  const query = {
    where: { id },
    attributes: ['id', ['owner_id', 'ownerId'], 'name', 'updatedOn', 'company', 'description'],
    include: [{
      model: TechTheme,
      as: 'technologyTheme',
      required: false,
      attributes: ['icon', 'name']
    },
    {
      model: Source,
      as: 'source',
      required: false
    },
    {
      model: User,
      as: 'owner',
      required: false,
      attributes: ['id', 'name', 'profilePicture']
    },
    {
      model: Tag,
      as: 'tags',
      required: false,
      through: { attributes: [] },
      attributes: ['label']
    },
    {
      model: Document,
      as: 'documents',
      required: false,
      through: { attributes: [] },
      attributes: ['name', 'documentType', 'location']
    },
    {
      model: OpportunityLink,
      as: 'links',
      required: false,
      attributes: ['link']
    },
    {
      model: OpportunityPhase,
      as: 'phases',
      required: false,
      attributes: ['id', 'status', 'startDate', 'endDate']
    },
    {
      model: DiscussionPost,
      as: 'discussions',
      required: false,
      attributes: [['updated_on', 'date'], ['content', 'text'], [Sequelize.fn('COUNT', Sequelize.col('discussions.replies.id')), 'replyCount']],
      include: [{
        model: DiscussionPost,
        as: 'replies',
        required: false,
        attributes: []
      },
      {
        model: User,
        as: 'author',
        required: false,
        attributes: ['id', 'name', 'profilePicture']
      }]
    }],
    group: ['opportunity.id', 'opportunity.owner_id', 'opportunity.name', 'opportunity.updated_on', 'opportunity.company', 'opportunity.description',
      'technologyTheme.id', 'technologyTheme.icon', 'technologyTheme.name', 'source.id', 'source.name', 'owner.id', 'owner.name', 'owner.profile_picture',
      'tags.id', 'tags.label', 'documents.id', 'documents.name', 'documents.document_type', 'documents.location', 'links.id', 'links.link',
      'phases.id', 'phases.status', 'phases.start_date', 'phases.end_date', 'discussions.id', 'discussions.updated_on', 'discussions.content',
      'discussions.author.id', 'discussions.author.name', 'discussions.author.profile_picture']
  }
  const opportunity = await Opportunity.findOne(query)
  if (!opportunity) {
    throw new errors.NotFoundError(`id: ${id} "Opportunity" doesn't exists.`)
  }
  const result = opportunity.toJSON()
  result.links = _.map(result.links, 'link')
  result.tags = _.map(result.tags, 'label')
  result.phases = _.map(result.phases, phase => {
    phase.isActive = _.isNil(phase.endDate)
    return phase
  })
  return result
}

getOpportunityCompleteById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

/**
 * Delete an opportunity
 * @param {Object} currentUser the user who perform this operation.
 * @param {Number} id the Opportunity ID
 */
async function deleteOpportunity (currentUser, id) {
  const opportunity = await Opportunity.findById(id)
  await opportunity.destroy()
}

deleteOpportunity.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

/**
 * Perform an update operation
 * @param {Object} currentUser the user who perform this operation.
 * @param {String} id the opportunity ID
 * @param {Object} data the updated data
 */
async function editOpportunityById (currentUser, id, data) {
  const user = await _checkIfUserExistByUsername(currentUser.username)
  const opportunity = await Opportunity.findById(id)
  if (opportunity.dataValues.owner_id !== user.id) {
    throw new errors.ForbiddenError('Only the owner is allowed to perform this operation.')
  }
  const newData = {}
  if (data.technologyTheme && data.technologyTheme !== opportunity.dataValues.tech_theme_id) {
    await TechTheme.findById(data.technologyTheme)
    newData.tech_theme_id = data.technologyTheme
  }
  if (data.source && data.source !== opportunity.dataValues.sourceId) {
    await Source.findById(data.source)
    newData.sourceId = data.source
  }
  _.each(_.pick(data, ['name', 'description', 'company']), (value, key) => {
    newData[key] = value
  })
  newData.updatedBy = user.id
  let tags = []
  if (!_.isNil(data.tags)) {
    tags = await Tag.findAll({
      where: {
        id: data.tags
      },
      raw: true
    })
    if (tags.length !== data.tags.length) {
      const nonexistentTags = _.differenceBy(data.tags, _.map(tags, 'id'))
      throw new errors.NotFoundError(`tag ids ${nonexistentTags} not found`)
    }
  }
  let supportingDocuments = []
  if (!_.isNil(data.supportingDocuments)) {
    supportingDocuments = await Document.findAll({
      where: {
        id: data.supportingDocuments
      },
      raw: true
    })
    if (supportingDocuments.length !== data.supportingDocuments.length) {
      const nonexistentSupportingDocuments = _.differenceBy(data.supportingDocuments, _.map(supportingDocuments, 'id'))
      throw new errors.NotFoundError(`document ids ${nonexistentSupportingDocuments} not found`)
    }
  }
  await opportunity.update(newData)
  if (!_.isUndefined(data.tags)) {
    await OpportunityTag.destroy({
      where: {
        opportunity_id: id
      }
    })
    await Promise.all(_.map(data.tags, async tag => await OpportunityTag.create(
      {
        opportunity_id: id,
        tag_id: tag
      })))
  }
  if (!_.isUndefined(data.supportingDocuments)) {
    await OpportunityDocument.destroy({
      where: {
        opportunity_id: id
      }
    })
    await Promise.all(_.map(data.supportingDocuments, async supportingDocument => await OpportunityDocument.create(
      {
        opportunity_id: id,
        document_id: supportingDocument
      })))
  }
  if (!_.isUndefined(data.usefulLinks)) {
    await OpportunityLink.destroy({
      where: {
        opportunityId: id
      }
    })
    await Promise.all(_.map(data.usefulLinks, async usefulLink => await OpportunityLink.create(
      {
        opportunityId: id,
        link: usefulLink
      })))
  }
  return await getOpportunityDetailsById(currentUser, id)
}

editOpportunityById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required(),
  data: Joi.object().keys({
    name: Joi.string().max(256),
    technologyTheme: Joi.number().integer().min(1),
    company: Joi.string().max(128),
    source: Joi.number().integer().min(1),
    description: Joi.string(),
    tags: Joi.array().items(Joi.number().integer().min(1)).allow(null),
    supportingDocuments: Joi.array().items(Joi.number().integer().min(1)).allow(null),
    usefulLinks: Joi.array().items(Joi.string()).allow(null)
  }).required()
})

/**
  * Get Opportunity with details by id
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the opportunity ID
  */
async function getOpportunityDetailsById (currentUser, id) {
  const opportunity = await Opportunity.findOne({
    where: {
      id
    },
    attributes: ['name', 'updatedOn', 'company', 'description'],
    include: [{
      model: TechTheme,
      as: 'technologyTheme',
      required: false,
      attributes: ['icon', 'name']
    },
    {
      model: Source,
      as: 'source',
      required: false
    },
    {
      model: Tag,
      as: 'tags',
      required: false,
      through: { attributes: [] },
      attributes: ['label']
    },
    {
      model: Document,
      as: 'documents',
      required: false,
      through: { attributes: [] },
      attributes: ['name', 'documentType', 'location']
    },
    {
      model: OpportunityLink,
      as: 'links',
      required: false,
      attributes: ['link']
    }]
  })
  if (!opportunity) {
    throw new errors.NotFoundError(`id: ${id} "Opportunity" doesn't exists.`)
  }
  const result = opportunity.toJSON()
  result.links = _.map(result.links, 'link')
  result.tags = _.map(result.tags, 'label')
  return result
}

getOpportunityDetailsById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

/**
  * Create a top level discussion post
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id opportunity id
  * @param {Object} post the discussion post data
  */
async function addTopLevelDiscussionPost (currentUser, id, post) {
  const author = await _checkIfUserExistByUsername(currentUser.username)
  await Opportunity.findById(id)
  await DiscussionPost.create({
    opportunityId: id,
    authorId: author.id,
    content: post.text,
    createdBy: author.id
  })
}

addTopLevelDiscussionPost.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required(),
  post: Joi.object().keys({
    text: Joi.string().required()
  }).required()
})

/**
  * Get top level discussion post of opportunity
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the opportunity ID
  */
async function getOpportunityDiscussionsById (currentUser, id) {
  await Opportunity.findById(id)
  return await DiscussionPost.findAll({
    where: {
      opportunityId: id,
      parentId: null
    },
    attributes: [['updated_on', 'date'], ['content', 'text'], [Sequelize.fn('COUNT', Sequelize.col('replies.id')), 'replyCount']],
    include: [{
      model: DiscussionPost,
      as: 'replies',
      required: false,
      attributes: []
    },
    {
      model: User,
      as: 'author',
      required: false,
      attributes: ['id', 'name', 'profilePicture']
    }],
    group: ['author.id', 'author.name', 'author.profile_picture', 'DiscussionPost.id', 'DiscussionPost.updated_on', 'DiscussionPost.content'],
    raw: false
  })
}

getOpportunityDiscussionsById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

/**
 * Perform an update operation
 * @param {Object} currentUser the user who perform this operation.
 * @param {Number} id the opportunity ID
 * @param {Object} data the updated data
 */
async function updateOpportunityMembersById (currentUser, id, data) {
  const user = await _checkIfUserExistByUsername(currentUser.username)
  const opportunity = await Opportunity.findById(id)
  if (opportunity.dataValues.owner_id !== user.id) {
    throw new errors.ForbiddenError('Only the owner is allowed to perform this operation.')
  }
  const currentMembers = await OpportunityMembers.findAll({
    where: {
      opportunity_id: id
    },
    attributes: ['user_id'],
    raw: true
  })
  const oldMembers = _.difference(_.map(currentMembers, 'user_id'), data.members)
  const newMembers = _.difference(data.members, _.map(currentMembers, 'user_id'))
  if (newMembers.length > 0) {
    const newMembersResult = await User.findAll({
      where: {
        id: newMembers
      },
      attributes: ['id'],
      raw: true
    })
    if (newMembersResult.length !== newMembers.length) {
      const nonexistentNewMembers = _.differenceBy(newMembers, _.map(newMembersResult, 'id'))
      throw new errors.NotFoundError(`User ids ${nonexistentNewMembers} not found`)
    }
  }

  if (oldMembers.length > 0 || newMembers.length > 0) {
    const transaction = await sequelize.transaction()
    try {
      await OpportunityMembers.bulkCreate(_.reduce(newMembers, (acc, member) => {
        acc.push({ opportunity_id: id, user_id: member })
        return acc
      }, []), { transaction })
      await OpportunityMembers.destroy({
        where: {
          opportunity_id: id,
          user_id: oldMembers
        },
        transaction
      })
      await transaction.commit()
    } catch (err) {
      await transaction.rollback()
      throw err
    }
  }
}
updateOpportunityMembersById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required(),
  data: Joi.object().keys({
    members: Joi.array().items(Joi.number().integer().min(1)).required()
  }).required()
})

/**
  * Get Opportunity members
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the opporunity ID
  */
async function getOpportunityMembersById (currentUser, id) {
  const result = await Opportunity.findOne({
    where: {
      id
    },
    attributes: [],
    include: [{
      model: User,
      as: 'owner',
      attributes: ['id', 'name', ['profile_picTure', 'profilePicture']]
    },
    {
      model: User,
      as: 'members',
      required: false,
      through: { attributes: [] },
      where: {
        id: { [Op.ne]: Sequelize.col('owner.id') }
      },
      attributes: ['id', 'name', ['profile_picTure', 'profilePicture']]
    }]
  })
  if (_.isNull(result)) {
    throw new errors.NotFoundError(`id: ${id} "Opportunity" doesn't exists.`)
  }
  return result
}

getOpportunityMembersById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

/**
  * Add new opportunity phase
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the Opportunity ID
  * @param {Object} phase phase data
  */
async function addOpportunityPhase (currentUser, id, phase) {
  await Opportunity.findById(id)
  const transaction = await sequelize.transaction()
  try {
    await OpportunityPhase.update({
      endDate: new Date().toISOString()
    },
    {
      where: {
        opportunityId: id,
        endDate: null
      },
      transaction
    })
    await OpportunityPhase.create({
      opportunityId: id,
      status: phase.status,
      startDate: new Date().toISOString()
    }, { transaction })
    await transaction.commit()
  } catch (err) {
    await transaction.rollback()
    throw err
  }
  return await getOpportunityPhasesById(currentUser, id)
}

addOpportunityPhase.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required(),
  phase: Joi.object().keys({
    status: Joi.opportunityStatus().required()
  }).required()
})

/**
  * Get Phases of opportunity
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the Opportunity ID
  */
async function getOpportunityPhasesById (currentUser, id) {
  await Opportunity.findById(id)
  const phases = await OpportunityPhase.findAll({
    where: {
      opportunityId: id
    },
    attributes: ['id', 'status', 'startDate', 'endDate'],
    raw: true
  })
  return _.map(phases, phase => {
    phase.isActive = _.isNil(phase.endDate)
    return phase
  })
}

getOpportunityPhasesById.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

module.exports = {
  createOpportunity,
  searchOpportunities,
  getOpportunityCompleteById,
  deleteOpportunity,
  editOpportunityById,
  getOpportunityDetailsById,
  addTopLevelDiscussionPost,
  getOpportunityDiscussionsById,
  updateOpportunityMembersById,
  getOpportunityMembersById,
  addOpportunityPhase,
  getOpportunityPhasesById
}
