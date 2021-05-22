/**
 * Controller for opportunities endpoints
 */
const service = require('../services/OpportunitiesService')
const helper = require('../common/helper')

/**
 * Get Opportunities.
 * @param req the request
 * @param res the response
 */
async function searchOpportunities (req, res) {
  const result = await service.searchOpportunities(req.authUser, req.query)
  helper.setResHeaders(req, res, result)
  res.send(result.result)
}

/**
 * Create Opportunity.
 * @param req the request
 * @param res the response
 */
async function createOpportunity (req, res) {
  res.send(await service.createOpportunity(req.authUser, req.body))
}

/**
 * Get Opportunity by id.
 * @param req the request
 * @param res the response
 */
async function getOpportunity (req, res) {
  res.send(await service.getOpportunity(req.authUser, req.params.opportunityId))
}

/**
 * Delete Opportunity by id.
 * @param req the request
 * @param res the response
 */
async function deleteOpportunity (req, res) {
  await service.deleteOpportunity(req.authUser, req.params.opportunityId)
  res.status(204).end()
}

/**
 * Get Opportunity details by id.
 * @param req the request
 * @param res the response
 */
async function getOpportunityDetails (req, res) {
  res.send(await service.getOpportunityDetails(req.authUser, req.params.opportunityId))
}

/**
 * Update Opportunity details by id.
 * @param req the request
 * @param res the response
 */
async function updateOpportunityDetails (req, res) {
  res.send(await service.updateOpportunityDetails(req.authUser, req.params.opportunityId, req.body))
}

/**
 * Get Opportunity Phases by id.
 * @param req the request
 * @param res the response
 */
async function getOpportunityPhases (req, res) {
  res.send(await service.getOpportunityPhases(req.authUser, req.params.opportunityId))
}

/**
 * Update Opportunity Phases by id.
 * @param req the request
 * @param res the response
 */
async function updateOpportunityPhases (req, res) {
  res.send(await service.updateOpportunityPhases(req.authUser, req.params.opportunityId, req.body))
}

/**
 * Get Opportunity Phases by id.
 * @param req the request
 * @param res the response
 */
async function getOpportunityMembers (req, res) {
  res.send(await service.getOpportunityMembers(req.authUser, req.params.opportunityId))
}

/**
 * Update Opportunity Phases by id.
 * @param req the request
 * @param res the response
 */
async function updateOpportunityMembers (req, res) {
  await service.updateOpportunityMembers(req.authUser, req.params.opportunityId, req.body)
  res.status(204).end()
}

module.exports = {
  searchOpportunities,
  createOpportunity,
  getOpportunity,
  deleteOpportunity,
  getOpportunityDetails,
  updateOpportunityDetails,
  getOpportunityPhases,
  updateOpportunityPhases,
  getOpportunityMembers,
  updateOpportunityMembers
}
