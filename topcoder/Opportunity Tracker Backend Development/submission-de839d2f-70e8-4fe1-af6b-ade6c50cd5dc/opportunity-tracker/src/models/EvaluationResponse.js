const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class EvaluationResponse extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      EvaluationResponse._models = models
      EvaluationResponse.belongsTo(models.Evaluation, { foreignKey: 'evaluationId' })
      EvaluationResponse.belongsTo(models.Criteria, { foreignKey: 'criteriaId' })
    }

    /**
     * Get EvaluationResponse by id
     * @param {Number} id the EvaluationResponse id
     * @returns {EvaluationResponse} the EvaluationResponse instance
     */
    static async findById (id) {
      const evaluationResponse = await EvaluationResponse.findOne({
        where: {
          id
        }
      })
      if (!evaluationResponse) {
        throw new errors.NotFoundError(`id: ${id} "EvaluationResponse" doesn't exists.`)
      }
      return evaluationResponse
    }
  }
  EvaluationResponse.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      evaluationId: {
        field: 'evaluation_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      criteriaId: {
        field: 'criteria_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      score: {
        field: 'score',
        type: Sequelize.INTEGER,
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'evaluation_response',
      timestamps: false
    }
  )

  return EvaluationResponse
}
