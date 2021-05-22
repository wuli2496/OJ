/**
 * The Users controller
 */
const { setResHeaders } = require('../helpers')
const service = require('../services/UsersService')

/**
 * Search Users
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function searchUsers (req, res) {
  const result = await service.searchUsers(req.query)
  setResHeaders(req, res, result)
  res.send(result.result)
}

module.exports = {
  searchUsers
}
