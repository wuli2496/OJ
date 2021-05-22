require('dotenv').config()
module.exports = {
  // the log level, default is 'debug'
  LOG_LEVEL: process.env.LOG_LEVEL || 'debug',
  // the server port, default is 3000
  PORT: process.env.PORT || 3000,
  // the server api base path
  BASE_PATH: process.env.BASE_PATH || '/api/v1',

  DB_DATABASE: process.env.DB_DATABASE || 'OP_TRACKER',
  DB_USERNAME: process.env.DB_USERNAME || 'sa',
  DB_PASSWORD: process.env.DB_PASSWORD || 'yourStrong@P!ssword',
  DB_HOST: process.env.DB_HOST || 'localhost',
  DB_DIALECT: process.env.DB_DIALECT || 'mssql',
  DB_PORT: process.env.DB_PORT || '1433',
  DB_LOGGING: process.env.DB_LOGGING || false,
  DB_SCHEMA_NAME: process.env.DB_SCHEMA_NAME || 'hess_opportunity',

  INITIAL_PHASE_STATUS: process.env.INITIAL_PHASE_STATUS || 'UnderEvaluation'
}
