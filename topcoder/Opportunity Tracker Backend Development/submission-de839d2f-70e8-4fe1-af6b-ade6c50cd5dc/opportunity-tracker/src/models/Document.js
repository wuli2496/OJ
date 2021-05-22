const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Document extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Document._models = models
      Document.belongsToMany(models.Opportunity, { foreignKey: 'document_id', through: models.OpportunityDocument })
    }

    /**
     * Get Document by id
     * @param {Number} id the Document id
     * @returns {Document} the Document instance
     */
    static async findById (id) {
      const document = await Document.findOne({
        where: {
          id
        }
      })
      if (!document) {
        throw new errors.NotFoundError(`id: ${id} "Document" doesn't exists.`)
      }
      return document
    }
  }
  Document.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      name: {
        field: 'name',
        type: Sequelize.STRING(128),
        allowNull: false
      },
      documentType: {
        field: 'document_type',
        type: Sequelize.STRING(32),
        allowNull: false
      },
      location: {
        field: 'location',
        type: Sequelize.STRING(128),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'document',
      timestamps: false
    }
  )

  return Document
}
