/**
 * Contains opportunity routes
 */
module.exports = {
  '/opportunities': {
    post: {
      controller: 'OpportunityController',
      method: 'createOpportunity',
      auth: 'jwt',
      scopes: ['openid']
    },
    get: {
      controller: 'OpportunityController',
      method: 'searchOpportunities',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/opportunities/:opportunityId': {
    get: {
      controller: 'OpportunityController',
      method: 'getOpportunityCompleteById',
      auth: 'jwt',
      scopes: ['openid']
    },
    delete: {
      controller: 'OpportunityController',
      method: 'deleteOpportunity',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/opportunities/:opportunityId/details': {
    post: {
      controller: 'OpportunityController',
      method: 'editOpportunityById',
      auth: 'jwt',
      scopes: ['openid']
    },
    get: {
      controller: 'OpportunityController',
      method: 'getOpportunityDetailsById',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/opportunities/:opportunityId/discussionPosts': {
    post: {
      controller: 'OpportunityController',
      method: 'addTopLevelDiscussionPost',
      auth: 'jwt',
      scopes: ['openid']
    },
    get: {
      controller: 'OpportunityController',
      method: 'getOpportunityDiscussionsById',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/opportunities/:opportunityId/members': {
    post: {
      controller: 'OpportunityController',
      method: 'updateOpportunityMembersById',
      auth: 'jwt',
      scopes: ['openid']
    },
    get: {
      controller: 'OpportunityController',
      method: 'getOpportunityMembersById',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/opportunities/:opportunityId/phases': {
    post: {
      controller: 'OpportunityController',
      method: 'addOpportunityPhase',
      auth: 'jwt',
      scopes: ['openid']
    },
    get: {
      controller: 'OpportunityController',
      method: 'getOpportunityPhasesById',
      auth: 'jwt',
      scopes: ['openid']
    }
  }
}
