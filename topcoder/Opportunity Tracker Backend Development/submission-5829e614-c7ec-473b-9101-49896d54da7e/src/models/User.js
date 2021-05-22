const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class User extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      User._models = models
      User.hasMany(models.Opportunity, { foreignKey: 'ownerId', as: 'owners' })
      User.hasMany(models.DiscussionPost, { foreignKey: 'authorId', as: 'posts' })
    }

    /**
     * Get entity by id
     * @param id the entity id
     * @returns the User instance
     */
    static async findById (id) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await User.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "User" doesn't exists.`)
      }
      return { id: entity.id, name: entity.name, profilePic: entity.profilePicture }
    }

    /**
     * Get entity by name
     * @param name the entity name
     * @returns the User instance
     */
    static async findByName (name) {
      const criteria = {
        where: {
          name
        }
      }
      const entity = await User.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`name: ${name} "User" doesn't exists.`)
      }
      return { id: entity.id, name: entity.name, profilePic: entity.profilePicture }
    }

    /**
     * Get entity by name
     * @param oktaUsername the entity oktaUsername
     * @returns the User instance
     */
    static async findByOktaName (oktaUsername) {
      const criteria = {
        where: {
          oktaUsername
        }
      }
      const entity = await User.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`oktaUsername: ${oktaUsername} "User" doesn't exists.`)
      }
      return { id: entity.id, name: entity.name, profilePic: entity.profilePicture }
    }
  }

  User.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false
      },
      name: {
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
        allowNull: false
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: false,
      tableName: 'user'
    }
  )
  return User
}
