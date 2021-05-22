const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class Document extends Model {
    /**
     * Get entity by id
     * @param id the entity id
     * @returns the Document instance
     */
    static async findById (id) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await Document.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "Document" doesn't exists.`)
      }
      return entity
    }
  }

  Document.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false
      },
      name: {
        type: Sequelize.STRING(128),
        allowNull: false
      },
      documentType: {
        field: 'document_type',
        type: Sequelize.STRING(32),
        allowNull: false
      },
      location: {
        type: Sequelize.STRING(128),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'document'
    }
  )
  return Document
}
