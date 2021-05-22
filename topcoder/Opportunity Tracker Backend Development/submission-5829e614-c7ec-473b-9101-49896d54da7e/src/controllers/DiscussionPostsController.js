/**
 * Controller for opportunities endpoints
 */
const service = require('../services/DiscussionPostsService')

/**
 * Get DiscussionPosts.
 * @param req the request
 * @param res the response
 */
async function getDiscussionPosts (req, res) {
  res.send(await service.getDiscussionPosts(req.authUser, req.params.opportunityId))
}

/**
 * Create DiscussionPost.
 * @param req the request
 * @param res the response
 */
async function createDiscussionPost (req, res) {
  await service.createDiscussionPost(req.authUser, req.params.opportunityId, req.body)
  res.status(201).end()
}

/**
 * Get DiscussionPostReplies.
 * @param req the request
 * @param res the response
 */
async function getDiscussionPostReplies (req, res) {
  res.send(await service.getDiscussionPostReplies(req.authUser, req.params.discussionPostId))
}

/**
 * Create DiscussionPostReply.
 * @param req the request
 * @param res the response
 */
async function createDiscussionPostReply (req, res) {
  await service.createDiscussionPostReply(req.authUser, req.params.discussionPostId, req.body)
  res.status(201).end()
}

module.exports = {
  getDiscussionPosts,
  createDiscussionPost,
  getDiscussionPostReplies,
  createDiscussionPostReply
}
