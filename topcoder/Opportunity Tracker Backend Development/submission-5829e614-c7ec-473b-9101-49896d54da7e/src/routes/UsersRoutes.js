/**
 * Contains users routes
 */

module.exports = {
  '/users': {
    get: {
      controller: 'UsersController',
      method: 'searchUsers',
      auth: 'jwt'
    }
  }
}
