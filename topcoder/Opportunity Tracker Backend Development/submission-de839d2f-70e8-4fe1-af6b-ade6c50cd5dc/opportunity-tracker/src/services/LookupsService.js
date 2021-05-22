/**
 * The Lookups service
 */
const Joi = require('joi')
const { Source, Tag, TechTheme } = require('../models')

/**
  * Get Sources
  * @param {Object} currentUser the user who perform this operation.
  */
async function getSourcesLookup (currentUser) {
  return await Source.findAll()
}

getSourcesLookup.schema = Joi.object({
  currentUser: Joi.object().required()
})

/**
  * Get Tags
  * @param {Object} currentUser the user who perform this operation.
  */
async function getTagsLookup (currentUser) {
  return await Tag.findAll()
}

getTagsLookup.schema = Joi.object({
  currentUser: Joi.object().required()
})

/**
  * Get TechThemes
  * @param {Object} currentUser the user who perform this operation.
  */
async function getTechnologyThemesLookup (currentUser) {
  return await TechTheme.findAll({
    attributes: ['icon', 'name']
  })
}

getTechnologyThemesLookup.schema = Joi.object({
  currentUser: Joi.object().required()
})

module.exports = {
  getSourcesLookup,
  getTagsLookup,
  getTechnologyThemesLookup
}
