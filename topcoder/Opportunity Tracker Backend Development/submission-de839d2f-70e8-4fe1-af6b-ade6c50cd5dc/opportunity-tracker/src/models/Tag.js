const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Tag extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Tag._models = models
      Tag.belongsToMany(models.Opportunity, { foreignKey: 'tag_id', through: models.OpportunityTag })
    }

    /**
     * Get Tag by id
     * @param {Number} id the Tag id
     * @returns {Tag} the Tag instance
     */
    static async findById (id) {
      const tag = await Tag.findOne({
        where: {
          id
        }
      })
      if (!tag) {
        throw new errors.NotFoundError(`id: ${id} "Tag" doesn't exists.`)
      }
      return tag
    }
  }
  Tag.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      label: {
        field: 'label',
        type: Sequelize.STRING(128),
        allowNull: true
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'tag',
      timestamps: false
    }
  )

  return Tag
}
