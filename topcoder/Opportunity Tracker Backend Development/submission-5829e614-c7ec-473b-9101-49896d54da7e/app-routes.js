/**
 * Configure all routes for express app
 */

const _ = require('lodash')
const config = require('config')
const HttpStatus = require('http-status-codes')
const helper = require('./src/common/helper')
const routes = require('./src/routes')
const models = require('./src/models')
const jwtDecode = require('jwt-decode')

/**
 * Configure all routes for express app
 * @param app the express app
 */
module.exports = (app) => {
  // Load all routes
  _.each(routes, (verbs, path) => {
    _.each(verbs, (def, verb) => {
      const controllerPath = `./src/controllers/${def.controller}`
      const method = require(controllerPath)[def.method]; // eslint-disable-line
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
        actions.push(async (req, res, next) => {
          let token
          if (req.headers.authorization && req.headers.authorization.split(' ')[0] === 'Bearer') {
            token = req.headers.authorization.split(' ')[1]
          }
          if (token) {
            // decode the user id from the token
            try {
              const decoded = jwtDecode(token)
              if (!decoded.sub) {
                res.status(401).json({ message: 'The token provided is invalid.' })
                res.send()
              } else {
                const user = await models.User.findByOktaName(decoded.sub)
                req.authUser = user.id
              }
            } catch (error) {
              res.status(401)
                .json({ message: 'The token provided is invalid.' })
              res.send()
            }
          } else {
            // if there is no token
            // return an error
            res.status(401)
              .json({ message: 'No token provided.' })
            res.send()
          }
          next()
        })
      }

      actions.push(method)
      const fullPath = config.get('BASE_PATH') + path
      app[verb](fullPath, helper.autoWrapExpress(actions))
    })
  })

  // Check if the route is not found or HTTP method is not supported
  app.use('*', (req, res) => {
    let url = req.baseUrl
    if (url.indexOf(config.get('BASE_PATH')) === 0) {
      url = url.substring(config.get('BASE_PATH').length)
    }
    const route = routes[url]
    if (route) {
      res.status(HttpStatus.METHOD_NOT_ALLOWED).json({ message: 'The requested HTTP method is not supported.' })
    } else {
      res.status(HttpStatus.NOT_FOUND).json({ message: 'The requested resource cannot be found.' })
    }
  })
}
