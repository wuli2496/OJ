const service = require('../../src/services/UsersService')
const initDb = require('../../src/init-db')
const expect = require('chai').expect

describe('Users service test', () => {
  before(async () => {
    await initDb.initDB()
  })

  after(async () => {
    await initDb.initDB()
  })

  describe('Search users successfully', () => {
    it('T01: search user with one matching', async () => {
      const result = await service.searchUsers(123456, { name: 'testuser' })
      expect(result.result.length).to.eq(1)
      expect(result.result[0].id).to.eq(123456)
      expect(result.result[0].name).to.eq('testuser')
      expect(result.result[0].profilePic).to.eq('p_4.png')
    })

    it('T02: search user with multiple matching', async () => {
      const result = await service.searchUsers(123456, { name: 'u' })
      expect(result.result.length).to.eq(4)
    })
  })
})
