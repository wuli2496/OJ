const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class OpportunityLink extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      OpportunityLink._models = models
      OpportunityLink.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
    }

    /**
     * Get OpportunityLink by id
     * @param {Number} id the OpportunityLink id
     * @returns {OpportunityLink} the OpportunityLink instance
     */
    static async findById (id) {
      const opportunityLink = await OpportunityLink.findOne({
        where: {
          id
        }
      })
      if (!opportunityLink) {
        throw new errors.NotFoundError(`id: ${id} "OpportunityLink" doesn't exists.`)
      }
      return opportunityLink
    }
  }
  OpportunityLink.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      opportunityId: {
        field: 'opportunity_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      link: {
        field: 'link',
        type: Sequelize.STRING(256),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_link',
      timestamps: false
    }
  )

  return OpportunityLink
}
