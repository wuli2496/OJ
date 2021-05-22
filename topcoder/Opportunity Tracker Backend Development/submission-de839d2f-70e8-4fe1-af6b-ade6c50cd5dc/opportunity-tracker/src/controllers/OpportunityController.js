/**
 * The Opportunity controller
 */
const httpStatus = require('http-status')
const { setResHeaders } = require('../helpers')
const service = require('../services/OpportunityService')

/**
 * Create
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function createOpportunity (req, res) {
  res.json(await service.createOpportunity(req.authUser, req.body))
}

/**
 * Search
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function searchOpportunities (req, res) {
  const result = await service.searchOpportunities(req.authUser, req.query)
  setResHeaders(req, res, result)
  res.send(result.result)
}

/**
 * Get
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getOpportunityCompleteById (req, res) {
  res.json(await service.getOpportunityCompleteById(req.authUser, req.params.opportunityId))
}

/**
 * Delete
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function deleteOpportunity (req, res) {
  await service.deleteOpportunity(req.authUser, req.params.opportunityId)
  res.status(httpStatus.NO_CONTENT).end()
}

/**
 * Edit
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function editOpportunityById (req, res) {
  res.json(await service.editOpportunityById(req.authUser, req.params.opportunityId, req.body))
}

/**
 * Get
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getOpportunityDetailsById (req, res) {
  res.json(await service.getOpportunityDetailsById(req.authUser, req.params.opportunityId))
}

/**
 * Create
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function addTopLevelDiscussionPost (req, res) {
  await service.addTopLevelDiscussionPost(req.authUser, req.params.opportunityId, req.body)
  res.status(httpStatus.CREATED).end()
}

/**
 * Get
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getOpportunityDiscussionsById (req, res) {
  res.json(await service.getOpportunityDiscussionsById(req.authUser, req.params.opportunityId))
}

/**
 * Update
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function updateOpportunityMembersById (req, res) {
  await service.updateOpportunityMembersById(req.authUser, req.params.opportunityId, req.body)
  res.status(httpStatus.NO_CONTENT).end()
}

/**
 * Get
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getOpportunityMembersById (req, res) {
  res.json(await service.getOpportunityMembersById(req.authUser, req.params.opportunityId))
}

/**
 * Create
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function addOpportunityPhase (req, res) {
  res.json(await service.addOpportunityPhase(req.authUser, req.params.opportunityId, req.body))
}

/**
 * Get
 * @param {Object} req the request object
 * @param {Object} res the response object
 */
async function getOpportunityPhasesById (req, res) {
  res.json(await service.getOpportunityPhasesById(req.authUser, req.params.opportunityId))
}

module.exports = {
  createOpportunity,
  searchOpportunities,
  getOpportunityCompleteById,
  deleteOpportunity,
  editOpportunityById,
  getOpportunityDetailsById,
  addTopLevelDiscussionPost,
  getOpportunityDiscussionsById,
  updateOpportunityMembersById,
  getOpportunityMembersById,
  addOpportunityPhase,
  getOpportunityPhasesById
}
