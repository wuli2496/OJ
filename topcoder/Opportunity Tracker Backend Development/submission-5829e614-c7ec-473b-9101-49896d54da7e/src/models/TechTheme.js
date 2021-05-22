const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class TechTheme extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      TechTheme._models = models
      TechTheme.hasMany(models.Opportunity, { foreignKey: 'techThemeId', as: 'opportunities' })
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
      const entity = await TechTheme.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "TechTheme" doesn't exists.`)
      }
      return entity
    }
  }

  TechTheme.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false
      },
      icon: {
        type: Sequelize.STRING(128),
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
      tableName: 'tech_theme'
    }
  )
  return TechTheme
}
