/**
 * Contains discussion posts related routes
 */

module.exports = {
  '/opportunities/:opportunityId/discussionPosts': {
    get: {
      controller: 'DiscussionPostsController',
      method: 'getDiscussionPosts',
      auth: 'jwt'
    },
    post: {
      controller: 'DiscussionPostsController',
      method: 'createDiscussionPost',
      auth: 'jwt'
    }
  },
  '/discussionPosts/:discussionPostId/replies': {
    get: {
      controller: 'DiscussionPostsController',
      method: 'getDiscussionPostReplies',
      auth: 'jwt'
    },
    post: {
      controller: 'DiscussionPostsController',
      method: 'createDiscussionPostReply',
      auth: 'jwt'
    }
  }
}
