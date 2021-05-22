/**
 * Application entry point
 */
require('./src/bootstrap')
const express = require('express')
const config = require('config')
const cors = require('cors')
const { errorHandler } = require('./src/helpers/middlewares')
const logger = require('./src/helpers/logger')

const app = express()

// Add required middlewares
app.use(cors({
  // Allow browsers access pagination data in headers
  exposedHeaders: ['X-Page', 'X-Per-Page', 'X-Total', 'X-Total-Pages', 'X-Prev-Page', 'X-Next-Page']
}))
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
// load routes
require('./routes')(app)

app.use(errorHandler)

// Start the API
app.listen(config.PORT, () => logger.info({ component: 'app', message: `API is running on port ${config.PORT}` }))
