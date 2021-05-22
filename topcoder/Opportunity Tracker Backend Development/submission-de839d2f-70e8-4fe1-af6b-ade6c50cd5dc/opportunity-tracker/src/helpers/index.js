/**
 * Helper methods
 */
const _ = require('lodash')
const fs = require('fs')
const models = require('../models')
const querystring = require('querystring')
const Confirm = require('prompt-confirm')
const logger = require('./logger')

/**
 * Set HTTP response headers from result.
 * @param {Object} req the HTTP request
 * @param {Object} res the HTTP response
 * @param {Object} result the operation result
 */
function setResHeaders (req, res, result) {
  const totalPages = Math.ceil(result.total / result.perPage)
  if (parseInt(result.page, 10) > 1) {
    res.set('X-Prev-Page', parseInt(result.page, 10) - 1)
  }
  if (parseInt(result.page, 10) < totalPages) {
    res.set('X-Next-Page', parseInt(result.page, 10) + 1)
  }
  res.set('X-Page', parseInt(result.page, 10))
  res.set('X-Per-Page', result.perPage)
  res.set('X-Total', result.total)
  res.set('X-Total-Pages', totalPages)
  // set Link header
  if (totalPages > 0) {
    let link = `<${getPageLink(req, 1)}>; rel="first", <${getPageLink(req, totalPages)}>; rel="last"`
    if (parseInt(result.page, 10) > 1) {
      link += `, <${getPageLink(req, parseInt(result.page, 10) - 1)}>; rel="prev"`
    }
    if (parseInt(result.page, 10) < totalPages) {
      link += `, <${getPageLink(req, parseInt(result.page, 10) + 1)}>; rel="next"`
    }
    res.set('Link', link)
  }
}

/**
 * Get link for a given page.
 * @param {Object} req the HTTP request
 * @param {Number} page the page number
 * @returns {String} link for the page
 */
function getPageLink (req, page) {
  const q = _.assignIn({}, req.query, { page })
  return `${req.protocol}://${req.get('Host')}${req.baseUrl}${req.path}?${querystring.stringify(q)}`
}

/**
 * Wrap async function to standard express function
 * @param {Function} fn the async function
 * @returns {Function} the wrapped function
 */
function wrapExpress (fn) {
  return function (req, res, next) {
    fn(req, res, next).catch(next)
  }
}

/**
 * Wrap all functions from object
 * @param obj the object (controller exports)
 * @returns {Object|Array} the wrapped object
 */
function autoWrapExpress (obj) {
  if (_.isArray(obj)) {
    return obj.map(autoWrapExpress)
  }
  if (_.isFunction(obj)) {
    if (obj.constructor.name === 'AsyncFunction') {
      return wrapExpress(obj)
    }
    return obj
  }
  _.each(obj, (value, key) => {
    obj[key] = autoWrapExpress(value)
  })
  return obj
}

/**
 * Get the first parameter from cli arguments
 */
function getParamFromCliArgs () {
  const filteredArgs = process.argv.filter(arg => !arg.includes('--'))

  if (filteredArgs.length > 2) {
    return filteredArgs[2]
  }

  return null
}

/**
 * Prompt the user with a y/n query and call a callback function based on the answer
 * @param {string} promptQuery the query to ask the user
 * @param {function} cb the callback function
 */
async function promptUser (promptQuery, cb) {
  if (process.argv.includes('--force')) {
    await cb()
    return
  }

  const prompt = new Confirm(promptQuery)
  prompt.ask(async (answer) => {
    if (answer) {
      await cb()
    }
  })
}

/**
 * Import data from a json file into the database
 * @param {string} pathToFile the path to the json file
 * @param {Array} dataModels the data models to import
 */
async function importData (pathToFile, dataModels) {
  // check if file exists
  if (!fs.existsSync(pathToFile)) {
    throw new Error(`File with path ${pathToFile} does not exist`)
  }

  // clear database
  logger.info({ component: 'importData', message: 'Clearing database...' })
  await models.sequelize.sync({ force: true })

  let transaction = null
  let currentModelName = null
  try {
    // Start a transaction
    transaction = await models.sequelize.transaction()
    const jsonData = JSON.parse(fs.readFileSync(pathToFile).toString())

    for (let index = 0; index < dataModels.length; index += 1) {
      const modelName = dataModels[index]
      currentModelName = modelName
      const model = models[modelName]
      const modelRecords = jsonData[modelName]

      if (modelRecords && modelRecords.length > 0) {
        logger.info({ component: 'importData', message: `Importing data for model: ${modelName}` })

        await model.bulkCreate(modelRecords, { transaction })
        logger.info({ component: 'importData', message: `Records imported for model: ${modelName} = ${modelRecords.length}` })
      } else {
        logger.info({ component: 'importData', message: `No records to import for model: ${modelName}` })
      }
    }
    // commit transaction only if all things went ok
    logger.info({ component: 'importData', message: 'committing transaction to database...' })
    await transaction.commit()
  } catch (error) {
    logger.error({ component: 'importData', message: `Error while writing data of model: ${currentModelName}` })
    // rollback all insert operations
    if (transaction) {
      logger.info({ component: 'importData', message: 'rollback database transaction...' })
      transaction.rollback()
    }
    if (error.name && error.errors && error.fields) {
      // For sequelize validation errors, we throw only fields with data that helps in debugging error,
      // because the error object has many fields that contains very big sql query for the insert bulk operation
      throw new Error(
        JSON.stringify({
          modelName: currentModelName,
          name: error.name,
          errors: error.errors,
          fields: error.fields
        })
      )
    } else {
      throw error
    }
  }
}

/**
 * Export data from the database into a json file
 * @param {string} pathToFile the path to the json file
 * @param {Array} dataModels the data models to export
 */
async function exportData (pathToFile, dataModels) {
  logger.info({ component: 'exportData', message: `Start Saving data to file with path ${pathToFile}....` })

  const allModelsRecords = {}
  for (let index = 0; index < dataModels.length; index += 1) {
    const modelName = dataModels[index]
    const modelRecords = await models[modelName].findAll()
    const rawRecords = _.map(modelRecords, r => r.toJSON())
    allModelsRecords[modelName] = rawRecords
    logger.info({ component: 'exportData', message: `Records loaded for model: ${modelName} = ${rawRecords.length}` })
  }

  fs.writeFileSync(pathToFile, JSON.stringify(allModelsRecords))
  logger.info({ component: 'exportData', message: 'End Saving data to file....' })
}

module.exports = {
  setResHeaders,
  autoWrapExpress,
  getParamFromCliArgs,
  promptUser,
  importData,
  exportData
}
