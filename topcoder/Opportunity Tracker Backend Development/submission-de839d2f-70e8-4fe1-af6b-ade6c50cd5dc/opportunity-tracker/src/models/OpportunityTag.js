const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityTag extends Model {
  }
  OpportunityTag.init(
    { },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_tag',
      timestamps: false
    }
  )

  return OpportunityTag
}
