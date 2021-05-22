const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Source extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Source._models = models
      Source.hasMany(models.Opportunity, { foreignKey: 'sourceId' })
    }

    /**
     * Get Source by id
     * @param {Number} id the Source id
     * @returns {Source} the Source instance
     */
    static async findById (id) {
      const source = await Source.findOne({
        where: {
          id
        }
      })
      if (!source) {
        throw new errors.NotFoundError(`id: ${id} "Source" doesn't exists.`)
      }
      return source
    }
  }
  Source.init(
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
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'source',
      timestamps: false
    }
  )

  return Source
}
