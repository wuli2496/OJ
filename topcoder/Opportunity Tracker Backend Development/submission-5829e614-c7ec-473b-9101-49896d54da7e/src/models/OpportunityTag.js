const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityTag extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityTag._models = models
      models.Opportunity.belongsToMany(models.Tag, {
        through: OpportunityTag,
        foreignKey: 'opportunity_id',
        onDelete: 'Cascade'
      })
      models.Tag.belongsToMany(models.Opportunity, { through: OpportunityTag, foreignKey: 'tag_id', onDelete: 'Cascade' })
    }
  }

  OpportunityTag.init(
    {},
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_tag'
    }
  )
  return OpportunityTag
}
