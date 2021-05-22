/**
 * Controller for users endpoints
 */
const service = require('../services/UsersService')
const helper = require('../common/helper')

/**
 * Get Users.
 * @param req the request
 * @param res the response
 */
async function searchUsers (req, res) {
  const result = await service.searchUsers(req.authUser, req.query)
  helper.setResHeaders(req, res, result)
  res.send(result.result)
}

module.exports = {
  searchUsers
}
