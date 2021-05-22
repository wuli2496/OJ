const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class TechTheme extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      TechTheme._models = models
      TechTheme.hasMany(models.Opportunity, { foreignKey: 'tech_theme_id' })
    }

    /**
     * Get TechTheme by id
     * @param {Number} id the TechTheme id
     * @returns {TechTheme} the TechTheme instance
     */
    static async findById (id) {
      const techTheme = await TechTheme.findOne({
        where: {
          id
        }
      })
      if (!techTheme) {
        throw new errors.NotFoundError(`id: ${id} "TechTheme" doesn't exists.`)
      }
      return techTheme
    }
  }
  TechTheme.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      icon: {
        field: 'icon',
        type: Sequelize.STRING(128),
        allowNull: false
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
      tableName: 'tech_theme',
      timestamps: false
    }
  )

  return TechTheme
}
