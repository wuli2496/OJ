/**
 * Contains lookup routes
 */
module.exports = {
  '/lookups/sources': {
    get: {
      controller: 'LookupsController',
      method: 'getSourcesLookup',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/lookups/tags': {
    get: {
      controller: 'LookupsController',
      method: 'getTagsLookup',
      auth: 'jwt',
      scopes: ['openid']
    }
  },
  '/lookups/technologyThemes': {
    get: {
      controller: 'LookupsController',
      method: 'getTechnologyThemesLookup',
      auth: 'jwt',
      scopes: ['openid']
    }
  }
}
