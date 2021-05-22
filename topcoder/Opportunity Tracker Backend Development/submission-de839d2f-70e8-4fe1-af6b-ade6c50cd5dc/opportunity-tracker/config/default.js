/**
 * Default configurations
 */
require('dotenv').config()
module.exports = {
  LOG_LEVEL: process.env.LOG_LEVEL || 'debug',
  API_VERSION: process.env.API_VERSION || '/v1',
  PORT: process.env.PORT || 3000,
  DB_HOST: process.env.DB_HOST,
  DB_PORT: process.env.DB_PORT,
  DB_NAME: process.env.DB_NAME,
  DB_USER: process.env.DB_USER,
  DB_PWD: process.env.DB_PWD,
  DB_SCHEMA: process.env.DB_SCHEMA,
  DB_TRUST: process.env.DB_TRUST,
  API_BASE_URL: process.env.API_BASE_URL || 'http://localhost:3000',
  DEFAULT_DATA_FILE_PATH: process.env.DEFAULT_DATA_FILE_PATH || './data/demo-data.json',

  AUTH: {
    CLIENTID: process.env.CLIENTID,
    ISSUER: process.env.ISSUER,
    CLAIMS: {
      aud: process.env.AUD
    },
    TESTING: {
      disableHttpsCheck: process.env.DISABLEHTTPSCHECK === 'true'
    }
  }
}
