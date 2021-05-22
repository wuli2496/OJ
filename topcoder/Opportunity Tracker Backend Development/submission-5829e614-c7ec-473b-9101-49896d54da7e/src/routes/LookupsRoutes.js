/**
 * Contains lookups routes
 */

module.exports = {
  '/lookups/technologyThemes': {
    get: {
      controller: 'LookupsController',
      method: 'getTechnologyThemes',
      auth: 'jwt'
    }
  },
  '/lookups/sources': {
    get: {
      controller: 'LookupsController',
      method: 'getSources',
      auth: 'jwt'
    }
  },
  '/lookups/tags': {
    get: {
      controller: 'LookupsController',
      method: 'getTags',
      auth: 'jwt'
    }
  }
}
