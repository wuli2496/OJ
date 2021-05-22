/**
 * Configure all routes for express app
 */

const _ = require('lodash')
const config = require('config')
const httpStatus = require('http-status')
const helper = require('./src/helpers')
const errors = require('./src/helpers/errors')
const routes = require('./src/routes')
const { authenticator } = require('./src/helpers/middlewares')

/**
  * Configure all routes for express app
  * @param app the express app
  */
module.exports = (app) => {
  // Load all routes
  _.each(routes, (verbs, path) => {
    _.each(verbs, (def, verb) => {
      const controllerPath = `./src/controllers/${def.controller}`
      const method = require(controllerPath)[def.method]
      if (!method) {
        throw new Error(`${def.method} is undefined`)
      }

      const actions = []
      actions.push((req, res, next) => {
        req.signature = `${def.controller}#${def.method}`
        next()
      })

      // add Authenticator check if route has auth
      if (def.auth) {
        actions.push((req, res, next) => {
          authenticator(req, res, next)
          next()
        })
        if (def.scopes) {
          actions.push((req, res, next) => {
            if (_.intersection(def.scopes, req.authUser.scopes).length === 0) {
              next(new errors.ForbiddenError('You are not allowed to perform this action!'))
            } else {
              next()
            }
          })
        }
      }

      actions.push(method)
      const fullPath = config.get('API_VERSION') + path
      app[verb](fullPath, helper.autoWrapExpress(actions))
    })
  })

  // Check if the route is not found or HTTP method is not supported
  app.use('*', (req, res) => {
    let url = req.baseUrl
    if (url.indexOf(config.get('API_VERSION')) === 0) {
      url = url.substring(config.get('API_VERSION').length)
    }
    const route = routes[url]
    if (route) {
      res.status(httpStatus.METHOD_NOT_ALLOWED).json({ message: 'The requested HTTP method is not supported.' })
    } else {
      res.status(httpStatus.NOT_FOUND).json({ message: 'The requested resource cannot be found.' })
    }
  })
}
