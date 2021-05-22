const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Opportunity extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Opportunity._models = models
      Opportunity.belongsTo(models.TechTheme, { as: 'technologyTheme', foreignKey: 'tech_theme_id' })
      Opportunity.belongsTo(models.Source, { as: 'source', foreignKey: 'sourceId' })
      Opportunity.belongsTo(models.User, { as: 'owner', foreignKey: 'owner_id' })
      Opportunity.belongsToMany(models.Tag, { as: 'tags', foreignKey: 'opportunity_id', through: models.OpportunityTag })
      Opportunity.belongsToMany(models.Document, { as: 'documents', foreignKey: 'opportunity_id', through: models.OpportunityDocument })
      Opportunity.hasMany(models.OpportunityLink, { as: 'links', foreignKey: 'opportunityId' })
      Opportunity.hasMany(models.OpportunityPhase, { as: 'phases', foreignKey: 'opportunityId' })
      Opportunity.belongsToMany(models.User, { foreignKey: 'opportunity_id', through: models.OpportunityViews })
      Opportunity.belongsToMany(models.User, { as: 'members', foreignKey: 'opportunity_id', through: models.OpportunityMembers })
      Opportunity.hasMany(models.DiscussionPost, { as: 'discussions', foreignKey: 'opportunityId' })
    }

    /**
     * Get Opportunity by id
     * @param {Number} id the Opportunity id
     * @returns {Opportunity} the Opportunity instance
     */
    static async findById (id) {
      const opportunity = await Opportunity.findOne({
        where: {
          id
        }
      })
      if (!opportunity) {
        throw new errors.NotFoundError(`id: ${id} "Opportunity" doesn't exists.`)
      }
      return opportunity
    }
  }
  Opportunity.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      name: {
        field: 'name',
        type: Sequelize.STRING(256),
        allowNull: false
      },
      tech_theme_id: {
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
        field: 'description',
        type: Sequelize.TEXT,
        allowNull: false
      },
      company: {
        field: 'company',
        type: Sequelize.STRING(128),
        allowNull: false
      },
      owner_id: {
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
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity',
      createdAt: 'createdOn',
      updatedAt: 'updatedOn',
      timestamps: true
    }
  )

  return Opportunity
}
