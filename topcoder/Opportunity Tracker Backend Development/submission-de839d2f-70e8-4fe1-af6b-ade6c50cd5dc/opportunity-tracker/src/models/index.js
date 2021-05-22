/**
 * Sequelize generated file
 */

const fs = require('fs')
const path = require('path')

const Sequelize = require('sequelize')
const config = require('config')

const basename = path.basename(module.filename)
const db = {}
const dbUrl = `mssql://${config.DB_USER}:${config.DB_PWD}@${config.DB_HOST}:${config.DB_PORT}/${config.DB_NAME}`
const sequelize = new Sequelize(dbUrl, {
  logging: false
})

fs
  .readdirSync(__dirname)
  .filter(file => (file.indexOf('.') !== 0) && (file !== basename) && (file.slice(-3) === '.js'))
  .forEach((file) => {
    const model = require(path.join(__dirname, file))(sequelize)
    db[model.name] = model
  })

Object.keys(db).forEach((modelName) => {
  if (db[modelName].associate) {
    db[modelName].associate(db)
  }
})

db.sequelize = sequelize
db.Sequelize = Sequelize

module.exports = db
