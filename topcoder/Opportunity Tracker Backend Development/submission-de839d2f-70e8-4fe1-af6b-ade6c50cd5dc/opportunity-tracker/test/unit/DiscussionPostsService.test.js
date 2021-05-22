/* eslint-disable no-unused-expressions */

const expect = require('chai').expect
const sinon = require('sinon')
const { DiscussionPost, User } = require('../../src/models')
const service = require('../../src/services/DiscussionPostsService')
const commonData = require('./common/CommonData')
const testData = require('./common/DiscussionPostsData')

describe('DiscussionPosts Service Test', () => {
  afterEach(() => {
    sinon.restore()
  })
  it('T01:Create discussion reply successfully', async () => {
    const data = testData.T01
    const stubDiscussionPostFindOne = sinon.stub(DiscussionPost, 'findOne').callsFake(async () => 1)
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubDiscussionPostCreate = sinon.stub(DiscussionPost, 'create').callsFake(async () => {})
    await service.createDiscussionReply(commonData.user1, data.id, data.discussionReply)
    expect(stubDiscussionPostFindOne.calledOnce).to.be.true
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubDiscussionPostCreate.calledOnce).to.be.true
  })
  it('T02:Fail to Create discussion reply with nonexistent author', async () => {
    const data = testData.T02
    const stubDiscussionPostFindOne = sinon.stub(DiscussionPost, 'findOne').callsFake(async () => 1)
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => undefined)
    const stubDiscussionPostCreate = sinon.stub(DiscussionPost, 'create').callsFake(async () => {})
    let error
    try {
      await service.createDiscussionReply(commonData.user1, data.id, data.discussionReply)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubDiscussionPostFindOne.calledOnce).to.be.true
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubDiscussionPostCreate.called).to.be.false
  })
  it('T03:Fail to Create discussion reply with nonexistent discussion', async () => {
    const data = testData.T03
    const stubDiscussionPostFindOne = sinon.stub(DiscussionPost, 'findOne').callsFake(async () => undefined)
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => {})
    const stubDiscussionPostCreate = sinon.stub(DiscussionPost, 'create').callsFake(async () => {})
    let error
    try {
      await service.createDiscussionReply(commonData.user1, data.id, data.discussionReply)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubDiscussionPostFindOne.calledOnce).to.be.true
    expect(stubUserFindOne.calledOnce).to.be.false
    expect(stubDiscussionPostCreate.called).to.be.false
  })
  it('T04:Get discussion reply successfully', async () => {
    const data = testData.T04
    const stubDiscussionPostFindOne = sinon.stub(DiscussionPost, 'findOne').callsFake(async () => 1)
    const stubDiscussionPostFindAll = sinon.stub(DiscussionPost, 'findAll').callsFake(async () => data.replies)
    const replies = await service.getDiscussionPostReplies(commonData.user1, data.id)
    expect(replies).to.deep.eql(data.replies)
    expect(stubDiscussionPostFindOne.calledOnce).to.be.true
    expect(stubDiscussionPostFindAll.calledOnce).to.be.true
  })
})
