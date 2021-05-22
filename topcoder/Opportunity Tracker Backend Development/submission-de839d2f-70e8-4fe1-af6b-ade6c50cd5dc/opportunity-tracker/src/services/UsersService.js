/**
 * The Users service
 */
const Joi = require('joi')
const { User } = require('../models')
const { Sequelize } = require('sequelize')

/**
  * Search Users
  * @param {Object} criteria the search criteria
  */
async function searchUsers (criteria) {
  const whereClause = {}
  const { page, perPage } = criteria
  whereClause.name = Sequelize.where(Sequelize.fn('LOWER', Sequelize.col('name')), 'LIKE', '%' + criteria.name.toLowerCase() + '%')

  const res = await User.findAndCountAll({
    where: whereClause,
    attributes: ['id', 'name', 'profilePicture'],
    offset: (page - 1) * perPage, // pagination starts from 0
    limit: perPage
  })
  return { total: res.count, result: res.rows, page, perPage }
}

searchUsers.schema = Joi.object({
  criteria: Joi.object().keys({
    page: Joi.page(),
    perPage: Joi.perPage(),
    name: Joi.string().required()
  }).required()
})

module.exports = {
  searchUsers
}
