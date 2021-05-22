/**
 * Service for users endpoints
 */
const Joi = require('joi')
const models = require('../models')
const { Op } = require('sequelize')
const _ = require('lodash')

/**
 * Get Users.
 * @params criteria the searching criteria
 * @returns the User list.
 */
async function searchUsers (currentUser, criteria) {
  const page = criteria.page > 0 ? criteria.page : 1
  const perPage = criteria.perPage > 0 ? criteria.perPage : 20
  const filter = {}
  filter.name = {
    [Op.like]: `%${criteria.name}%`
  }
  const users = await models.User.findAll({
    where: filter,
    offset: ((page - 1) * perPage),
    limit: perPage
  })

  return {
    total: users.length,
    page,
    perPage,
    result: _.map(users, u => ({ id: u.id, name: u.name, profilePic: u.profilePicture }))
  }
}

searchUsers.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required(),
  criteria: Joi.object().keys({
    page: Joi.number().integer(),
    perPage: Joi.number().integer(),
    name: Joi.string().trim().required()
  }).required()
}).required()

module.exports = {
  searchUsers
}
