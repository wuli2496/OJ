const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
module.exports = (sequelize) => {
  class OpportunityPhase extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityPhase._models = models
      OpportunityPhase.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
    }
  }

  OpportunityPhase.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true
      },
      opportunityId: {
        field: 'opportunity_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      status: {
        type: Sequelize.STRING(128),
        allowNull: false
      },
      startDate: {
        field: 'start_date',
        type: Sequelize.DATE,
        allowNull: true
      },
      endDate: {
        field: 'end_date',
        type: Sequelize.DATE,
        allowNull: true
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_phase'
    }
  )
  return OpportunityPhase
}
