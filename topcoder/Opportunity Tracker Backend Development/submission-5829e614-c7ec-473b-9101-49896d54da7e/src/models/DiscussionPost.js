const Sequelize = require('sequelize')
const { Model } = require('sequelize')
const config = require('config')
const errors = require('../common/errors')

module.exports = (sequelize) => {
  class DiscussionPost extends Model {
    /**
     * Create association between models
     * @param models the database models
     */
    static associate (models) {
      DiscussionPost._models = models
      DiscussionPost.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
      DiscussionPost.belongsTo(models.User, { foreignKey: 'authorId' })
    }

    /**
     * Get entity by id
     * @param id the entity id
     * @returns the DiscussionPost instance
     */
    static async findById (id) {
      const criteria = {
        where: {
          id
        }
      }
      const entity = await DiscussionPost.findOne(criteria)
      if (!entity) {
        throw new errors.NotFoundError(`id: ${id} "DiscussionPost" doesn't exists.`)
      }
      return entity
    }
  }

  DiscussionPost.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true
      },
      opportunityId: {
        field: 'opportunity_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      authorId: {
        field: 'author_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      parentId: {
        field: 'parent_id',
        type: Sequelize.INTEGER,
        allowNull: true
      },
      content: {
        type: Sequelize.STRING,
        allowNull: false
      },
      createdBy: {
        field: 'created_by',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      updatedBy: {
        field: 'updated_by',
        type: Sequelize.INTEGER
      },
      createdOn: {
        field: 'created_on',
        type: Sequelize.DATE
      },
      updatedOn: {
        field: 'updated_on',
        type: Sequelize.DATE
      }
    },
    {
      schema: config.DB_SCHEMA_NAME,
      sequelize,
      timestamps: true,
      createdAt: 'createdOn',
      updatedAt: 'updatedOn',
      deletedAt: false,
      tableName: 'discussion_post'
    }
  )
  return DiscussionPost
}
