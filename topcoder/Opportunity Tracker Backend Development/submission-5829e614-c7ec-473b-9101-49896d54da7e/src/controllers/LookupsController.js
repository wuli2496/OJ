/**
 * Controller for lookups endpoints
 */
const service = require('../services/LookupsService')

/**
 * Get Technology Themes.
 * @param req the request
 * @param res the response
 */
async function getTechnologyThemes (req, res) {
  res.send(await service.getTechnologyThemes(req.authUser))
}

/**
 * Get Sources.
 * @param req the request
 * @param res the response
 */
async function getSources (req, res) {
  res.send(await service.getSources(req.authUser))
}

/**
 * Get Tags.
 * @param req the request
 * @param res the response
 */
async function getTags (req, res) {
  res.send(await service.getTags(req.authUser))
}

module.exports = {
  getTechnologyThemes,
  getSources,
  getTags
}
