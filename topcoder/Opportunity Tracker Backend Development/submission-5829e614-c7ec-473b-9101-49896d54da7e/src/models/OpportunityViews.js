const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityViews extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityViews._models = models
      models.Opportunity.belongsToMany(models.User, { through: OpportunityViews, foreignKey: 'opportunity_id' })
      models.User.belongsToMany(models.Opportunity, { through: OpportunityViews, foreignKey: 'user_id' })
    }
  }

  OpportunityViews.init(
    {},
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_views'
    }
  )
  return OpportunityViews
}
