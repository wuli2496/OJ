/**
 * Service for lookups endpoints
 */
const models = require('../models')
const Joi = require('joi')

/**
 * Get Technology Themes.
 * @returns the Technology Theme list.
 */
async function getTechnologyThemes (currentUser) {
  return await models.TechTheme.findAll()
}

getTechnologyThemes.schema = Joi.object().keys({
  currentUser: Joi.number().integer().required()
}).required()

/**
 * Get Sources.
 * @returns the Source list.
 */
async function getSources (currentUser) {
  return await models.Source.findAll()
}

getSources.schema = getTechnologyThemes.schema

/**
 * Get Tags.
 * @returns the Tag list.
 */
async function getTags (currentUser) {
  return await models.Tag.findAll()
}

getTags.schema = getTechnologyThemes.schema

module.exports = {
  getTechnologyThemes,
  getSources,
  getTags
}
