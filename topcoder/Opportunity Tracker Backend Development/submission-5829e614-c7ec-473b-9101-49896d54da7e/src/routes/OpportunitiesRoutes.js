/**
 * Contains opportunities routes
 */

module.exports = {
  '/opportunities': {
    get: {
      controller: 'OpportunitiesController',
      method: 'searchOpportunities',
      auth: 'jwt'
    },
    post: {
      controller: 'OpportunitiesController',
      method: 'createOpportunity',
      auth: 'jwt'
    }
  },
  '/opportunities/:opportunityId': {
    get: {
      controller: 'OpportunitiesController',
      method: 'getOpportunity',
      auth: 'jwt'
    },
    delete: {
      controller: 'OpportunitiesController',
      method: 'deleteOpportunity',
      auth: 'jwt'
    }
  },
  '/opportunities/:opportunityId/details': {
    get: {
      controller: 'OpportunitiesController',
      method: 'getOpportunityDetails',
      auth: 'jwt'
    },
    post: {
      controller: 'OpportunitiesController',
      method: 'updateOpportunityDetails',
      auth: 'jwt'
    }
  },
  '/opportunities/:opportunityId/phases': {
    get: {
      controller: 'OpportunitiesController',
      method: 'getOpportunityPhases',
      auth: 'jwt'
    },
    post: {
      controller: 'OpportunitiesController',
      method: 'updateOpportunityPhases',
      auth: 'jwt'
    }
  },
  '/opportunities/:opportunityId/members': {
    get: {
      controller: 'OpportunitiesController',
      method: 'getOpportunityMembers',
      auth: 'jwt'
    },
    post: {
      controller: 'OpportunitiesController',
      method: 'updateOpportunityMembers',
      auth: 'jwt'
    }
  }
}
