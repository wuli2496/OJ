const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityMembers extends Model {
  }
  OpportunityMembers.init(
    {},
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_members',
      timestamps: false
    }
  )

  return OpportunityMembers
}
