const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class Source extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      Source._models = models
      Source.hasMany(models.Opportunity, { foreignKey: 'sourceId', as: 'opportunities' })
    }

    /**
     * Get entity by id
     * @param id the entity id
     * @returns the entity found
     */
    static async findById (id) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await Source.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "Source" doesn't exists.`)
      }
      return entity
    }
  }

  Source.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false
      },
      name: {
        type: Sequelize.STRING(128),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'source'
    }
  )
  return Source
}
