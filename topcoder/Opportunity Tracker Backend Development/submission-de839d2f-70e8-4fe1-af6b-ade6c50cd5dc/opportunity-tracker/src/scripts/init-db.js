/**
 * Sync the database models to db tables.
 */
const config = require('config')
const models = require('../models')
const logger = require('../helpers/logger')
const sql = require('mssql')

const initDB = async () => {
  const connection = await sql.connect(`Server=${config.DB_HOST},${config.DB_PORT};User Id=${config.DB_USER};Password=${config.DB_PWD};trustServerCertificate=${config.DB_TRUST}`)
  await sql.query(`IF NOT EXISTS(SELECT 1 FROM sys.databases WHERE name='${config.DB_NAME}') CREATE DATABASE ${config.DB_NAME}`)
  await connection.close()
  await sql.di
  if (process.argv[2] === 'force') {
    await models.sequelize.dropSchema(config.DB_SCHEMA)
  }
  await models.sequelize.createSchema(config.DB_SCHEMA)
  await models.sequelize.sync({ force: true })
}

if (!module.parent) {
  initDB().then(() => {
    logger.info({ component: 'init-db', message: 'Database synced successfully' })
    process.exit()
  }).catch((e) => {
    logger.logFullError(e, { component: 'init-db' })
    process.exit(1)
  })
}

module.exports = {
  initDB
}
