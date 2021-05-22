const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityDocument extends Model {
  }
  OpportunityDocument.init(
    { },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_document',
      timestamps: false
    }
  )

  return OpportunityDocument
}
