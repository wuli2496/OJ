/**
 * The Lookups controller
 */
const service = require('../services/LookupsService')

/**
 * Get Sources
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getSourcesLookup (req, res) {
  res.json(await service.getSourcesLookup(req.authUser))
}

/**
 * Get Tags
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getTagsLookup (req, res) {
  res.json(await service.getTagsLookup(req.authUser))
}

/**
 * Get Technology Themes
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getTechnologyThemesLookup (req, res) {
  res.json(await service.getTechnologyThemesLookup(req.authUser))
}

module.exports = {
  getSourcesLookup,
  getTagsLookup,
  getTechnologyThemesLookup
}
