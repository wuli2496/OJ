const service = require('../../src/services/LookupsService')
const initDb = require('../../src/init-db')
const expect = require('chai').expect

describe('Lookups service test', () => {
  before(async () => {
    await initDb.initDB()
  })

  after(async () => {
    await initDb.initDB()
  })

  describe('Get all lookup data successfully', () => {
    it('T01: Get all technology themes', async () => {
      const techThemes = await service.getTechnologyThemes(123456)
      expect(techThemes.length).to.eq(3)
      expect(techThemes[0].id).to.eq(1)
      expect(techThemes[0].icon).to.eq('1.png')
      expect(techThemes[0].name).to.eq('theme_1')
    })

    it('T02: Get all sources', async () => {
      const sources = await service.getSources(123456)
      expect(sources.length).to.eq(3)
      expect(sources[0].id).to.eq(1)
      expect(sources[0].name).to.eq('source_1')
    })

    it('T03: Get all tags', async () => {
      const tags = await service.getTags(123456)
      expect(tags.length).to.eq(3)
      expect(tags[0].id).to.eq(1)
      expect(tags[0].label).to.eq('tag_1')
    })
  })
})
