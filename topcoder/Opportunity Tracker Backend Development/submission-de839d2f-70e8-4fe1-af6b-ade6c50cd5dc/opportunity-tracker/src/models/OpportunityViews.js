const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityViews extends Model {
  }
  OpportunityViews.init(
    {},
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'opportunity_views',
      timestamps: false
    }
  )

  return OpportunityViews
}
