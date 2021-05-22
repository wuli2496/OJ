/**
 * The DiscussionPosts controller
 */
const httpStatus = require('http-status')
const service = require('../services/DiscussionPostsService')

/**
 * Create
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function createDiscussionReply (req, res) {
  await service.createDiscussionReply(req.authUser, req.params.discussionPostId, req.body)
  res.status(httpStatus.NO_CONTENT).end()
}

/**
 * Get Posts
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getDiscussionPostReplies (req, res) {
  res.json(await service.getDiscussionPostReplies(req.authUser, req.params.discussionPostId))
}

module.exports = {
  createDiscussionReply,
  getDiscussionPostReplies
}
