/* eslint-disable no-unused-expressions */

const expect = require('chai').expect
const sinon = require('sinon')
const { User } = require('../../src/models')
const service = require('../../src/services/UsersService')
const testData = require('./common/UsersData')

describe('Users Service Test', () => {
  afterEach(() => {
    sinon.restore()
  })
  it('T01:Search users successfully', async () => {
    const data = testData.T01
    const stubUserFindAndCountAll = sinon.stub(User, 'findAndCountAll').callsFake(async () => data.respond)
    const users = await service.searchUsers(data.criteria)
    expect(users).to.deep.eql(data.result)
    expect(stubUserFindAndCountAll.calledOnce).to.be.true
  })
})
