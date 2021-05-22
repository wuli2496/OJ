const { Sequelize, Model, Op } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class User extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      User._models = models
      User.hasMany(models.Opportunity, { foreignKey: 'owner_id' })
      User.hasMany(models.DiscussionPost, { foreignKey: 'authorId' })
      User.hasMany(models.Evaluation, { foreignKey: 'userId' })
      User.belongsToMany(models.Opportunity, { foreignKey: 'user_id', through: models.OpportunityViews })
      User.belongsToMany(models.Opportunity, { foreignKey: 'user_id', through: models.OpportunityMembers })
    }

    /**
     * Get User by id
     * @param {Number} id the User id
     * @returns {User} the User instance
     */
    static async findById (id) {
      const user = await User.findOne({
        where: {
          id
        }
      })
      if (!user) {
        throw new errors.NotFoundError(`id: ${id} "User" doesn't exists.`)
      }
      return user
    }
  }
  User.init(
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
      },
      profilePicture: {
        field: 'profile_picture',
        type: Sequelize.STRING(128),
        allowNull: false
      },
      oktaUsername: {
        field: 'okta_username',
        type: Sequelize.STRING(45),
        allowNull: true
      }
    },
    {
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'user',
      timestamps: false,
      indexes: [{
        unique: true,
        fields: ['okta_username'],
        where: {
          okta_username: {
            [Op.not]: null
          }
        }
      }]
    }
  )

  return User
}
