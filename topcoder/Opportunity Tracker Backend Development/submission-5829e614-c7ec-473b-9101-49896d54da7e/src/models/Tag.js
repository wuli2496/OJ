const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class Tag extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      Tag._models = models
      Tag.hasMany(models.OpportunityTag, { foreignKey: 'tag_id', as: 'opportunities' })
    }

    /**
     * Get entity by id
     * @param id the entity id
     * @returns the Tag instance
     */
    static async findById (id) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await Tag.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "Tag" doesn't exists.`)
      }
      return entity
    }
  }

  Tag.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false
      },
      label: {
        type: Sequelize.STRING(128),
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'tag'
    }
  )
  return Tag
}
