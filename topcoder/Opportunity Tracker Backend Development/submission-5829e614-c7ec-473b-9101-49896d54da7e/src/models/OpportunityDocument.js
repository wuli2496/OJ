const { Model } = require('sequelize')
const config = require('config')

module.exports = (sequelize) => {
  class OpportunityDocument extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      OpportunityDocument._models = models
      models.Opportunity.belongsToMany(models.Document, {
        through: OpportunityDocument,
        foreignKey: 'opportunity_id',
        onDelete: 'Cascade'
      })
      models.Document.belongsToMany(models.Opportunity, {
        through: OpportunityDocument,
        foreignKey: 'document_id',
        onDelete: 'Cascade'
      })
    }
  }

  OpportunityDocument.init(
    {},
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'opportunity_document'
    }
  )
  return OpportunityDocument
}
