/**
 * The application entry point
 */

require('./src/bootstrap')
const _ = require('lodash')
const config = require('config')
const express = require('express')
const cors = require('cors')
const HttpStatus = require('http-status-codes')
const logger = require('./src/common/logger')

// setup express app
const app = express()

app.use(cors({
  // Allow browsers access pagination data in headers
  exposedHeaders: ['X-Page', 'X-Per-Page', 'X-Total', 'X-Total-Pages', 'X-Prev-Page', 'X-Next-Page']
}))
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.set('port', config.PORT)

// Register routes
require('./app-routes')(app)

// The error handler
// eslint-disable-next-line no-unused-vars
app.use((err, req, res, next) => {
  logger.logFullError(err, { component: 'app', signature: req.signature || `${req.method}_${req.url}` })
  const errorResponse = {}
  const status = err.isJoi ? HttpStatus.BAD_REQUEST : (err.status || err.httpStatus || HttpStatus.INTERNAL_SERVER_ERROR)

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

  if (err.response) {
    // extract error message from V3/V5 API
    errorResponse.message = _.get(err, 'response.body.result.content.message') || _.get(err, 'response.body.message')
  }

  if (_.isUndefined(errorResponse.message)) {
    if (err.message && (err.httpStatus || status !== HttpStatus.INTERNAL_SERVER_ERROR)) {
      errorResponse.message = err.message
    } else {
      errorResponse.message = 'Internal server error'
    }
  }

  res.status(status).json(errorResponse)
})

const server = app.listen(app.get('port'), () => {
  logger.info({ component: 'app', message: `Express server listening on port ${app.get('port')}` })
})

if (process.env.NODE_ENV === 'test') {
  module.exports = server
}
