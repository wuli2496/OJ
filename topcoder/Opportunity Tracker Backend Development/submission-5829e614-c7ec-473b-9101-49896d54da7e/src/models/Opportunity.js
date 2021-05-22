const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')
const { Op } = require('sequelize')
const _ = require('lodash')

module.exports = (sequelize) => {
  class Opportunity extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      Opportunity._models = models
      Opportunity.belongsTo(models.TechTheme, { foreignKey: 'techThemeId' })
      Opportunity.belongsTo(models.Source, { foreignKey: 'sourceId' })
      Opportunity.belongsTo(models.User, { foreignKey: 'ownerId' })
      Opportunity.hasMany(models.OpportunityPhase, { foreignKey: 'opportunityId', as: 'phases' })
      Opportunity.hasMany(models.OpportunityLink, { foreignKey: 'opportunityId', as: 'usefulLinks' })
      Opportunity.hasMany(models.DiscussionPost, { foreignKey: 'opportunityId', as: 'posts' })
    }

    /**
     * Get opportunity by id
     * @param id the opportunity id
     * @package checkExistence if only for existence check
     * @package returnDetails if only Opportunity details is returned
     * @returns the Opportunity instance
     */
    static async findById (id, checkExistence = false, returnDetails = false) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await Opportunity.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "Opportunity" doesn't exists.`)
      }
      if (checkExistence) {
        return entity
      }
      const result = { id: entity.id, ownerId: entity.ownerId }
      const details = {
        name: entity.name,
        updatedOn: entity.updatedOn,
        company: entity.company,
        description: entity.description
      }
      details.technologyTheme = await Opportunity._models.TechTheme.findByPk(entity.techThemeId)
      details.source = await Opportunity._models.Source.findByPk(entity.sourceId)
      // get tags
      const tags = []
      const oppTags = await Opportunity._models.OpportunityTag.findAll({
        where: { opportunity_id: { [Op.eq]: entity.id } }
      })
      for (const oppTag of oppTags) {
        tags.push(await Opportunity._models.Tag.findById(oppTag.tag_id))
      }
      details.tags = tags
      // get documents
      const documents = []
      const oppDocuments = await Opportunity._models.OpportunityDocument.findAll({
        where: { opportunity_id: { [Op.eq]: entity.id } }
      })
      for (const oppDocument of oppDocuments) {
        documents.push(await Opportunity._models.Document.findById(oppDocument.document_id))
      }
      details.documents = documents
      // get links
      const links = await Opportunity._models.OpportunityLink.findAll({
        where: { opportunityId: { [Op.eq]: entity.id } }
      })
      details.links = links
      // get views
      details.views = await Opportunity._models.OpportunityViews.count({
        where: { opportunity_id: { [Op.eq]: entity.id } }
      })
      // return details only if specified
      if (returnDetails) {
        return details
      }

      result.opportunityDetails = details
      // get opportunity phases
      const phases = await Opportunity._models.OpportunityPhase.findAll({
        where: { opportunityId: { [Op.eq]: entity.id } }
      })

      result.opportunityPhases = _.map(_.map(phases, 'dataValues'), p => (_.assign(p, { isActive: !p.endDate })))
      // get discussions
      const filter = {}
      filter.opportunityId = {
        [Op.eq]: entity.id
      }
      const model = Opportunity._models.DiscussionPost
      const posts = await model.findAll({
        where: {
          opportunityId: {
            [Op.eq]: entity.id
          },
          parentId: {
            [Op.eq]: null
          }
        }
      })
      const discussions = []
      for (const post of posts) {
        const author = await model._models.User.findById(post.authorId)
        const replies = await model.count({ where: { parentId: { [Op.eq]: post.id } } })
        discussions.push({
          author: author,
          date: post.createdOn,
          text: post.content,
          replyCount: replies,
          id: post.id
        })
      }
      result.discussions = discussions
      return result
    }
  }

  Opportunity.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true
      },
      name: {
        field: 'name',
        type: Sequelize.STRING(256),
        allowNull: false
      },
      techThemeId: {
        field: 'tech_theme_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      sourceId: {
        field: 'source_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      description: {
        type: Sequelize.STRING,
        allowNull: false
      },
      company: {
        type: Sequelize.STRING(128),
        allowNull: false
      },
      ownerId: {
        field: 'owner_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      createdBy: {
        field: 'created_by',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      updatedBy: {
        field: 'updated_by',
        type: Sequelize.INTEGER
      },
      createdOn: {
        field: 'created_on',
        type: Sequelize.DATE
      },
      updatedOn: {
        field: 'updated_on',
        type: Sequelize.DATE
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: true,
      createdAt: 'createdOn',
      updatedAt: 'updatedOn',
      deletedAt: false,
      tableName: 'opportunity'
    }
  )
  return Opportunity
}
