const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
module.exports = (sequelize) => {
  class OpportunityLink extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityLink._models = models
      OpportunityLink.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
    }
  }

  OpportunityLink.init(
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
      link: {
        type: Sequelize.STRING(256),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_link'
    }
  )
  return OpportunityLink
}
