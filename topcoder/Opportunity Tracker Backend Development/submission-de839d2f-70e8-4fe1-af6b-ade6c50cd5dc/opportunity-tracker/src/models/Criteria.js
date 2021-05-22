const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Criteria extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Criteria._models = models
      Criteria.hasMany(models.EvaluationResponse, { foreignKey: 'criteriaId' })
    }

    /**
     * Get Criteria by id
     * @param {Number} id the Criteria id
     * @returns {Criteria} the Criteria instance
     */
    static async findById (id) {
      const criteria = await Criteria.findOne({
        where: {
          id
        }
      })
      if (!criteria) {
        throw new errors.NotFoundError(`id: ${id} "Criteria" doesn't exists.`)
      }
      return criteria
    }
  }
  Criteria.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      questionText: {
        field: 'question_text',
        type: Sequelize.TEXT,
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'criteria',
      timestamps: false
    }
  )

  return Criteria
}
