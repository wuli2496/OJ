const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class Evaluation extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      Evaluation._models = models
      Evaluation.belongsTo(models.User, { foreignKey: 'userId' })
      Evaluation.hasMany(models.EvaluationResponse, { foreignKey: 'evaluationId' })
    }

    /**
     * Get Evaluation by id
     * @param {Number} id the Evaluation id
     * @returns {Evaluation} the Evaluation instance
     */
    static async findById (id) {
      const evaluation = await Evaluation.findOne({
        where: {
          id
        }
      })
      if (!evaluation) {
        throw new errors.NotFoundError(`id: ${id} "Evaluation" doesn't exists.`)
      }
      return evaluation
    }
  }
  Evaluation.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      evaluationNotes: {
        field: 'evaluation_notes',
        type: Sequelize.TEXT,
        allowNull: true
      },
      userId: {
        field: 'user_id',
        type: Sequelize.INTEGER,
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'evaluation',
      timestamps: false
    }
  )

  return Evaluation
}
