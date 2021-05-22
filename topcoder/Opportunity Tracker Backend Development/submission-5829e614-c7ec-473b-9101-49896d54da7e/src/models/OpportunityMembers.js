const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityMembers extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityMembers._models = models
      models.Opportunity.belongsToMany(models.User, { through: OpportunityMembers, foreignKey: 'opportunity_id' })
      models.User.belongsToMany(models.Opportunity, { through: OpportunityMembers, foreignKey: 'user_id' })
    }
  }

  OpportunityMembers.init(
    {},
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_members'
    }
  )
  return OpportunityMembers
}
