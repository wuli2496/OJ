/**
 * Service for users endpoints
 */
const Joi = require('joi')
const models = require('../models')
const logger = require('../common/logger')
const { Op } = require('sequelize')
const model = models.DiscussionPost

/**
 * Find the discussion posts by the given filter.
 * @param filter the filter
 * @params the DiscussionPost list.
 */
async function _findPosts (filter) {
  const posts = await model.findAll({
    where: filter
  })
  const result = []
  for (const post of posts) {
    const author = await model._models.User.findById(post.authorId)
    const replies = await model.count({ where: { parentId: { [Op.eq]: post.id } } })
    result.push({ author: author, date: post.createdOn, text: post.content, replyCount: replies, id: post.id })
  }
  return result
}

/**
 * Get DiscussionPosts.
 * @params currentUser the user who perform this operation
 * @params opportunityId the opportunity id that the post belongs to
 * @returns the DiscussionPost list.
 */
async function getDiscussionPosts (currentUser, opportunityId) {
  // check if the opportunity exists.
  await model._models.Opportunity.findById(opportunityId, true, false)
  const filter = {}
  filter.opportunityId = {
    [Op.eq]: opportunityId
  }
  // Only the top-level posts are returned
  filter.parentId = {
    [Op.eq]: null
  }
  return await _findPosts(filter)
}

getDiscussionPosts.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  opportunityId: Joi.number().integer().required()
}).required()

/**
 * Create DiscussionPost.
 * @params currentUser the user who perform this operation
 * @params opportunityId the opportunity id that the post belongs to
 * @params entity the discussion post to be created
 * @returns the created discussion post
 */
async function createDiscussionPost (currentUser, opportunityId, entity) {
  try {
    // check if the opportunity exists.
    await model._models.Opportunity.findById(opportunityId)
    return await models.sequelize.transaction(async (t) => {
      const post = {
        opportunityId: opportunityId,
        authorId: currentUser,
        createdBy: currentUser,
        content: entity.text
      }
      const created = await model.create(post, { transaction: t })
      return created.dataValues
    })
  } catch (error) {
    logger.logFullError(error, { component: 'DiscussionPostsService', context: 'createDiscussionPost' })
    throw error
  }
}

createDiscussionPost.schema = Joi.object().keys({
  currentUser: Joi.number().integer(),
  opportunityId: Joi.number().integer().required(),
  entity: Joi.object().keys({
    text: Joi.string().trim().required()
  }).required()
}).required()

/**
 * Get DiscussionPostReplies.
 * @params currentUser the user who perform this operation
 * @params discussionPostId the discussion post id
 * @returns the DiscussionPostReplies
 */
async function getDiscussionPostReplies (currentUser, discussionPostId) {
  // check if the Discussion post exists.
  await model.findById(discussionPostId)
  const filter = {}
  filter.parentId = {
    [Op.eq]: discussionPostId
  }
  return await _findPosts(filter)
}

getDiscussionPostReplies.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  discussionPostId: Joi.number().integer().required()
}).required()

/**
 * Create discussion post reply.
 * @params currentUser the user who perform this operation
 * @params entity the reply of the DiscussionPost
 * @returns the created reply post
 */
async function createDiscussionPostReply (currentUser, discussionPostId, entity) {
  try {
    // check if the opportunity exists.
    const parent = await model._models.DiscussionPost.findById(discussionPostId)
    return await models.sequelize.transaction(async (t) => {
      const post = {
        opportunityId: parent.opportunityId,
        authorId: currentUser,
        createdBy: currentUser,
        content: entity.text,
        parentId: discussionPostId
      }
      const created = await model.create(post, { transaction: t })
      return created.dataValues
    })
  } catch (error) {
    logger.logFullError(error, { component: 'DiscussionPostsService', context: 'createDiscussionPostReply' })
    throw error
  }
}

createDiscussionPostReply.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  discussionPostId: Joi.number().integer().required(),
  entity: Joi.object().keys({
    text: Joi.string().trim().required()
  }).required()
}).required()

module.exports = {
  getDiscussionPosts,
  createDiscussionPost,
  getDiscussionPostReplies,
  createDiscussionPostReply
}
