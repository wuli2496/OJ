/**
 * Contains user routes
 */
module.exports = {
  '/users': {
    get: {
      controller: 'UsersController',
      method: 'searchUsers'
    }
  }
}
