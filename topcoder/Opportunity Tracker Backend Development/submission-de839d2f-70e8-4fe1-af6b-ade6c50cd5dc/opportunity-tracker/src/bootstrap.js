const fs = require('fs')
const Joi = require('joi')
const path = require('path')
const logger = require('./helpers/logger')

Joi.page = () => Joi.number().integer().min(1).default(1)
Joi.perPage = () => Joi.number().integer().min(1).default(10).max(100)
Joi.sortBy = () => Joi.string().valid('ProductName', 'Company', 'TechTheme', 'Status', 'LastUpdated', 'Owner', 'Tags', 'Views')
Joi.sortOrder = () => Joi.string().valid('asc', 'desc')
Joi.opportunityStatus = () => Joi.string().valid('UnderEvaluation', 'EvaluationPass', 'EvaluationFail', 'InPilotTest', 'PilotTestPass',
  'PilotTestFail', 'Implemented')

function buildServices (dir) {
  const files = fs.readdirSync(dir)

  files.forEach((file) => {
    const curPath = path.join(dir, file)
    fs.stat(curPath, (err, stats) => {
      if (err) return
      if (stats.isDirectory()) {
        buildServices(curPath)
      } else if (path.extname(file) === '.js') {
        const serviceName = path.basename(file, '.js')
        logger.buildService(require(curPath), serviceName)
      }
    })
  })
}

buildServices(path.join(__dirname, 'services'))
