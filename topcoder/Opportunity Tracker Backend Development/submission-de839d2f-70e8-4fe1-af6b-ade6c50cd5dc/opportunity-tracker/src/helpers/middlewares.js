/**
 * This module contains custom middlewares
 */
const _ = require('lodash')
const httpStatus = require('http-status')
const logger = require('./logger')
const errors = require('./errors')
const config = require('config')
const OktaJwtVerifier = require('@okta/jwt-verifier')

/**
 * Gracefully handle errors thrown from the app
 * @param {Object} err the error object
 * @param {Object} req the request object
 * @param {Object} res the response object
 * @param {Function} next the next middleware
 */
function errorHandler (err, req, res, next) {
  logger.logFullError(err, { component: 'app', signature: req.signature || `${req.method}_${req.url}` })
  const errorResponse = {}
  const status = err.isJoi ? httpStatus.BAD_REQUEST : (err.status || err.httpStatus || httpStatus.INTERNAL_SERVER_ERROR)

  if (_.isArray(err.details)) {
    if (err.isJoi) {
      _.map(err.details, (e) => {
        if (e.message) {
          if (_.isUndefined(errorResponse.message)) {
            errorResponse.message = e.message
          } else {
            errorResponse.message += `, ${e.message}`
          }
        }
      })
    }
  }

  if (_.isUndefined(errorResponse.message)) {
    if (err.message && (err.httpStatus || status !== httpStatus.INTERNAL_SERVER_ERROR)) {
      errorResponse.message = err.message
    } else {
      errorResponse.message = 'Internal server error'
    }
  }
  res.status(status).json(errorResponse)
}

const oktaJwtVerifier = new OktaJwtVerifier({
  clientId: config.AUTH.CLIENTID,
  issuer: config.AUTH.ISSUER,
  assertClaims: config.AUTH.CLAIMS,
  testing: config.AUTH.TESTING
})

/**
 * This middleware asserts valid access tokens and sends error responses
 * if the token is not present or fails validation. If the token is valid its
 * contents are attached to req
 */
function authenticator (req, res, next) {
  const authHeader = req.headers.authorization || ''
  const match = authHeader.match(/Bearer (.+)/)

  if (!match) {
    return next(new errors.BadRequestError('No token provided.'))
  }

  const accessToken = match[1]
  const audience = config.AUTH.CLAIMS.aud
  return oktaJwtVerifier.verifyAccessToken(accessToken, audience)
    .then((jwt) => {
      req.authUser = { uid: jwt.claims.uid, username: jwt.claims.sub, scopes: jwt.claims.scp, jwtToken: jwt.toString() }
      next()
    })
    .catch((err) => {
      res.status(httpStatus.UNAUTHORIZED).send(err.message)
    })
}

module.exports = {
  errorHandler,
  authenticator
}
