/**
 * The DiscussionPost service
 */
const Joi = require('joi')
const errors = require('../helpers/errors')
const _ = require('lodash')
const { DiscussionPost, User } = require('../models')
const { Sequelize } = require('sequelize')

/**
  * Create a new discussionReply
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id DiscussionPost Id
  * @param {Object} discussionReply the discussionReply data
  */
async function createDiscussionReply (currentUser, id, discussionReply) {
  await DiscussionPost.findById(id)
  const author = await User.findOne({ where: { oktaUsername: currentUser.username }, raw: true })
  if (_.isNil(author)) {
    throw new errors.NotFoundError(`user with username ${currentUser.username} doesn't exist`)
  }
  await DiscussionPost.create({
    authorId: author.id,
    parentId: id,
    content: discussionReply.text,
    createdBy: author.id
  })
}

createDiscussionReply.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required(),
  discussionReply: Joi.object().keys({
    text: Joi.string().required()
  }).required()
})

/**
  * Get Replies
  * @param {Object} currentUser the user who perform this operation.
  * @param {Number} id the parent DiscussionPost ID
  */
async function getDiscussionPostReplies (currentUser, id) {
  await DiscussionPost.findById(id)
  return await DiscussionPost.findAll({
    where: {
      parentId: id
    },
    attributes: [['updated_on', 'date'], ['content', 'text'], [Sequelize.fn('COUNT', Sequelize.col('replies.id')), 'replyCount']],
    include: [{
      model: DiscussionPost,
      as: 'replies',
      required: false,
      attributes: []
    },
    {
      model: User,
      as: 'author',
      required: false,
      attributes: ['id', 'name', 'profilePicture']
    }],
    group: ['author.id', 'author.name', 'author.profile_picture', 'DiscussionPost.id', 'DiscussionPost.updated_on', 'DiscussionPost.content'],
    raw: false
  })
}

getDiscussionPostReplies.schema = Joi.object({
  currentUser: Joi.object().required(),
  id: Joi.number().integer().required()
})

module.exports = {
  createDiscussionReply,
  getDiscussionPostReplies
}
