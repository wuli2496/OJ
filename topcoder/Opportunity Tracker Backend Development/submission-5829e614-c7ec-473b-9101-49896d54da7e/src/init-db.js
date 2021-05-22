/**
 * Sync the database models to db tables.
 */
const config = require('config')
const models = require('./models')
const logger = require('./common/logger')

const initDB = async () => {
  if (process.argv[2] === 'force') {
    await models.sequelize.dropSchema(config.DB_SCHEMA_NAME)
  }
  await models.sequelize.createSchema(config.DB_SCHEMA_NAME)
  // re-init all tables
  await models.sequelize.sync({ force: true })
  await models.TechTheme.create({ id: 1, icon: '1.png', name: 'theme_1' })
  await models.TechTheme.create({ id: 2, icon: '2.png', name: 'theme_2' })
  await models.TechTheme.create({ id: 3, icon: '3.png', name: 'theme_3' })
  await models.Source.create({ id: 1, name: 'source_1' })
  await models.Source.create({ id: 2, name: 'source_2' })
  await models.Source.create({ id: 3, name: 'source_3' })
  await models.Tag.create({ id: 1, label: 'tag_1' })
  await models.Tag.create({ id: 2, label: 'tag_2' })
  await models.Tag.create({ id: 3, label: 'tag_3' })
  await models.User.create({ id: 1, name: 'user_1', profilePicture: 'p_1.png', oktaUsername: 'okname_1' })
  await models.User.create({ id: 2, name: 'user_2', profilePicture: 'p_2.png', oktaUsername: 'okname_2' })
  await models.User.create({ id: 3, name: 'user_3', profilePicture: 'p_3.png', oktaUsername: 'okname_3' })
  await models.User.create({
    id: 123456,
    name: 'testuser',
    profilePicture: 'p_4.png',
    oktaUsername: '0oar95zt9zIpYuz6A0h7'
  })
  await models.Document.create({ id: 1, name: 'document_1', documentType: 'pdf', location: 'location 1' })
  await models.Document.create({ id: 2, name: 'document_2', documentType: 'doc', location: 'location 2' })
  await models.Document.create({ id: 3, name: 'document_3', documentType: 'image', location: 'location 3' })
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
