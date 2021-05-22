const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class OpportunityPhase extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      OpportunityPhase._models = models
      OpportunityPhase.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
    }

    /**
     * Get OpportunityPhase by id
     * @param {Number} id the OpportunityPhase id
     * @returns {OpportunityPhase} the OpportunityPhase instance
     */
    static async findById (id) {
      const opportunityPhase = await OpportunityPhase.findOne({
        where: {
          id
        }
      })
      if (!opportunityPhase) {
        throw new errors.NotFoundError(`id: ${id} "OpportunityPhase" doesn't exists.`)
      }
      return opportunityPhase
    }
  }
  OpportunityPhase.init(
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
      status: {
        field: 'status',
        type: Sequelize.STRING(128),
        allowNull: false
      },
      startDate: {
        field: 'start_date',
        type: Sequelize.STRING(45),
        allowNull: false
      },
      endDate: {
        field: 'end_date',
        type: Sequelize.STRING(45),
        allowNull: true
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_phase',
      timestamps: false
    }
  )

  return OpportunityPhase
}
