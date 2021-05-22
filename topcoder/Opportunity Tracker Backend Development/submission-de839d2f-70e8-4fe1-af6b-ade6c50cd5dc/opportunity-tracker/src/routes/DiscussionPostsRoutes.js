/**
 * Contains discussionPosts routes
 */
module.exports = {
  '/discussionPosts/:discussionPostId/replies': {
    get: {
      controller: 'DiscussionPostsController',
      method: 'getDiscussionPostReplies',
      auth: 'jwt',
      scopes: ['openid']
    },
    post: {
      controller: 'DiscussionPostsController',
      method: 'createDiscussionReply',
      auth: 'jwt',
      scopes: ['openid']
    }
  }
}
