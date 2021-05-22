const { Sequelize, Model } = require('sequelize')
const config = require('config')
const errors = require('../helpers/errors')

module.exports = (sequelize) => {
  class DiscussionPost extends Model {
    /**
     * Create association between models
     * @param {Object} models the database models
     */
    static associate (models) {
      DiscussionPost._models = models
      DiscussionPost.belongsTo(models.Opportunity, { foreignKey: 'opportunityId' })
      DiscussionPost.hasMany(DiscussionPost, { as: 'replies', foreignKey: 'parentId' })
      DiscussionPost.belongsTo(models.User, { as: 'author', foreignKey: 'authorId' })
    }

    /**
     * Get DiscussionPost by id
     * @param {Number} id the DiscussionPost id
     * @returns {DiscussionPost} the DiscussionPost instance
     */
    static async findById (id) {
      const discussionPost = await DiscussionPost.findOne({
        where: {
          id
        }
      })
      if (!discussionPost) {
        throw new errors.NotFoundError(`id: ${id} "DiscussionPost" doesn't exists.`)
      }
      return discussionPost
    }
  }
  DiscussionPost.init(
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
      },
      opportunityId: {
        field: 'opportunity_id',
        type: Sequelize.INTEGER,
        allowNull: true
      },
      authorId: {
        field: 'author_id',
        type: Sequelize.INTEGER,
        allowNull: false
      },
      parentId: {
        field: 'parent_id',
        type: Sequelize.INTEGER,
        allowNull: true,
        onDelete: 'NO ACTION'
      },
      content: {
        field: 'content',
        type: Sequelize.TEXT,
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
      schema: config.DB_SCHEMA,
      sequelize,
      tableName: 'discussion_post',
      createdAt: 'createdOn',
      updatedAt: 'updatedOn',
      timestamps: true
    }
  )

  return DiscussionPost
}
