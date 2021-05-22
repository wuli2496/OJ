/* eslint-disable no-unused-expressions */

const expect = require('chai').expect
const sinon = require('sinon')
const {
  Opportunity, User, TechTheme, Source, Tag, Document, OpportunityTag, OpportunityDocument, OpportunityLink,
  OpportunityPhase, DiscussionPost, OpportunityMembers, sequelize
} = require('../../src/models')
const service = require('../../src/services/OpportunityService')
const commonData = require('./common/CommonData')
const testData = require('./common/OpportunityData')

describe('Opportunity Service Test', () => {
  afterEach(() => {
    sinon.restore()
  })
  it('T01:Create opportunity successfully', async () => {
    const data = testData.T01
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => data.techTheme)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => data.source)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const stubDocumentFindAll = sinon.stub(Document, 'findAll').callsFake(async () => data.docs)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityCreate = sinon.stub(Opportunity, 'create').callsFake(async () => data.newOpportunity)
    const stubOpportunityTagBulkCreate = sinon.stub(OpportunityTag, 'bulkCreate').callsFake(async () => {})
    const stubOpportunityDocumentBulkCreate = sinon.stub(OpportunityDocument, 'bulkCreate').callsFake(async () => {})
    const stubOpportunityLinkBulkCreate = sinon.stub(OpportunityLink, 'bulkCreate').callsFake(async () => {})
    const stubOpportunityPhaseCreate = sinon.stub(OpportunityPhase, 'create').callsFake(async () => {})
    const result = await service.createOpportunity(commonData.user1, data.opportunity)
    expect(result).to.deep.eql(data.result)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
    expect(stubDocumentFindAll.calledOnce).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityTagBulkCreate.calledOnce).to.be.true
    expect(stubOpportunityDocumentBulkCreate.calledOnce).to.be.true
    expect(stubOpportunityLinkBulkCreate.calledOnce).to.be.true
    expect(stubOpportunityPhaseCreate.calledOnce).to.be.true
    expect(stubOpportunityCreate.calledOnce).to.be.true
  })
  it('T02:Fail to create opportunity with nonexistent user', async () => {
    const data = testData.T02
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
  })
  it('T03:Fail to create opportunity with nonexistent techTheme id', async () => {
    const data = testData.T03
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
  })
  it('T04:Fail to create opportunity with nonexistent source id', async () => {
    const data = testData.T04
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => data.techTheme)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
  })
  it('T05:Fail to create opportunity with nonexistent tag id', async () => {
    const data = testData.T05
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => data.techTheme)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => data.source)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
  })
  it('T06:Fail to create opportunity with nonexistent document id', async () => {
    const data = testData.T06
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => data.techTheme)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => data.source)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const stubDocumentFindAll = sinon.stub(Document, 'findAll').callsFake(async () => data.docs)
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
    expect(stubDocumentFindAll.calledOnce).to.be.true
  })
  it('T07:Fail to create opportunity - database error', async () => {
    const data = testData.T07
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => data.techTheme)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => data.source)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const stubDocumentFindAll = sinon.stub(Document, 'findAll').callsFake(async () => data.docs)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityCreate = sinon.stub(Opportunity, 'create').callsFake(async () => { throw new Error('Error') })
    let error
    try {
      await service.createOpportunity(commonData.user1, data.opportunity)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
    expect(stubDocumentFindAll.calledOnce).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityCreate.calledOnce).to.be.true
  })
  it('T08:Search opportunities successfully', async () => {
    const data = testData.T08
    const stubOpportunityFindAndCountAll = sinon.stub(Opportunity, 'findAndCountAll').callsFake(async () => data.response)
    const result = await service.searchOpportunities(commonData.user1, data.criteria)
    expect(stubOpportunityFindAndCountAll.calledOnce).to.be.true
    expect(result).to.deep.eql(data.result)
  })
  it('T09:Fail to Search opportunities by wrong techTheme id', async () => {
    const data = testData.T09
    let error
    try {
      await service.searchOpportunities(commonData.user1, data.criteria)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
  })
  it('T10:Fail to Search opportunities by wrong tag id', async () => {
    const data = testData.T10
    let error
    try {
      await service.searchOpportunities(commonData.user1, data.criteria)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
  })
  it('T11:Get complete opportunity by id successfully', async () => {
    const data = testData.T11
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const result = await service.getOpportunityCompleteById(commonData.user1, data.id)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(result).to.deep.eql(data.result)
  })
  it('T12:Fail to get complete opportunity by nonexistent id', async () => {
    const data = testData.T12
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.getOpportunityCompleteById(commonData.user1, data.id)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T13:Delete opportunity successfully', async () => {
    const data = testData.T13
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    await service.deleteOpportunity(commonData.user1, data.id)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T14:Fail to delete opportunity by nonexistent id', async () => {
    const data = testData.T14
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.deleteOpportunity(commonData.user1, data.id)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T15:Edit opportunity successfully', async () => {
    const data = testData.T15
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => 1)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => 1)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const stubDocumentFindAll = sinon.stub(Document, 'findAll').callsFake(async () => data.docs)
    const stubOpportunityTagDestroy = sinon.stub(OpportunityTag, 'destroy').callsFake(async () => {})
    const stubOpportunityTagCreate = sinon.stub(OpportunityTag, 'create').callsFake(async () => {})
    const stubOpportunityDocumentDestroy = sinon.stub(OpportunityDocument, 'destroy').callsFake(async () => {})
    const stubOpportunityDocumentCreate = sinon.stub(OpportunityDocument, 'create').callsFake(async () => {})
    const stubOpportunityLinkDestroy = sinon.stub(OpportunityLink, 'destroy').callsFake(async () => {})
    const stubOpportunityLinkCreate = sinon.stub(OpportunityLink, 'create').callsFake(async () => {})
    const result = await service.editOpportunityById(commonData.user1, data.id, data.data)
    expect(result).to.deep.eql(data.result)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledTwice).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
    expect(stubDocumentFindAll.calledOnce).to.be.true
    expect(stubOpportunityTagDestroy.calledOnce).to.be.true
    expect(stubOpportunityTagCreate.called).to.be.true
    expect(stubOpportunityDocumentDestroy.calledOnce).to.be.true
    expect(stubOpportunityDocumentCreate.called).to.be.true
    expect(stubOpportunityLinkDestroy.calledOnce).to.be.true
    expect(stubOpportunityLinkCreate.called).to.be.true
  })
  it('T16:Fail to edit opportunity by not owner', async () => {
    const data = testData.T16
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    let error
    try {
      await service.editOpportunityById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T17:Fail to edit opportunity by nonexistent tag id', async () => {
    const data = testData.T17
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => 1)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => 1)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    let error
    try {
      await service.editOpportunityById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
  })
  it('T18:Fail to edit opportunity by nonexistent document id', async () => {
    const data = testData.T18
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubTechThemeFindOne = sinon.stub(TechTheme, 'findOne').callsFake(async () => 1)
    const stubSourceFindOne = sinon.stub(Source, 'findOne').callsFake(async () => 1)
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const stubDocumentFindAll = sinon.stub(Document, 'findAll').callsFake(async () => data.docs)
    let error
    try {
      await service.editOpportunityById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubTechThemeFindOne.calledOnce).to.be.true
    expect(stubSourceFindOne.calledOnce).to.be.true
    expect(stubTagFindAll.calledOnce).to.be.true
    expect(stubDocumentFindAll.calledOnce).to.be.true
  })
  it('T19:Fail to get opportunity details by nonexistent id', async () => {
    const data = testData.T19
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => undefined)
    let error
    try {
      await service.getOpportunityDetailsById(commonData.user1, data.id)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T20:Add top level discussion post successfully', async () => {
    const data = testData.T20
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => 1)
    const stubDiscussionPostCreate = sinon.stub(DiscussionPost, 'create').callsFake(async () => {})
    await service.addTopLevelDiscussionPost(commonData.user1, data.id, data.post)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubDiscussionPostCreate.calledOnce).to.be.true
  })
  it('T21:Get opportunity discussions successfully', async () => {
    const data = testData.T21
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => 1)
    const stubDiscussionPostFindAll = sinon.stub(DiscussionPost, 'findAll').callsFake(async () => data.discussions)
    const discussions = await service.getOpportunityDiscussionsById(commonData.user1, data.id)
    expect(discussions).to.deep.eql(data.discussions)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubDiscussionPostFindAll.calledOnce).to.be.true
  })
  it('T22:Update opportunity members successfully', async () => {
    const data = testData.T22
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubOpportunityMembersFindAll = sinon.stub(OpportunityMembers, 'findAll').callsFake(async () => data.currentMembers)
    const stubUserFindAll = sinon.stub(User, 'findAll').callsFake(async () => data.newMembersResult)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityMembersBulkCreate = sinon.stub(OpportunityMembers, 'bulkCreate').callsFake(async () => {})
    const stubOpportunityMembersDestroy = sinon.stub(OpportunityMembers, 'destroy').callsFake(async () => {})
    await service.updateOpportunityMembersById(commonData.user1, data.id, data.data)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubOpportunityMembersFindAll.calledOnce).to.be.true
    expect(stubUserFindAll.calledOnce).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityMembersBulkCreate.calledOnce).to.be.true
    expect(stubOpportunityMembersDestroy.calledOnce).to.be.true
  })
  it('T23:Fail to update opportunity members with not owner', async () => {
    const data = testData.T23
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    let error
    try {
      await service.updateOpportunityMembersById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T24:Fail to update opportunity members with nonexistend member id', async () => {
    const data = testData.T24
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubOpportunityMembersFindAll = sinon.stub(OpportunityMembers, 'findAll').callsFake(async () => data.currentMembers)
    const stubUserFindAll = sinon.stub(User, 'findAll').callsFake(async () => data.newMembersResult)
    let error
    try {
      await service.updateOpportunityMembersById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubOpportunityMembersFindAll.calledOnce).to.be.true
    expect(stubUserFindAll.calledOnce).to.be.true
  })
  it('T25:Fail to update opportunity members - database error', async () => {
    const data = testData.T25
    const stubUserFindOne = sinon.stub(User, 'findOne').callsFake(async () => data.user)
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.opportunity)
    const stubOpportunityMembersFindAll = sinon.stub(OpportunityMembers, 'findAll').callsFake(async () => data.currentMembers)
    const stubUserFindAll = sinon.stub(User, 'findAll').callsFake(async () => data.newMembersResult)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityMembersBulkCreate = sinon.stub(OpportunityMembers, 'bulkCreate').callsFake(async () => {})
    const stubOpportunityMembersDestroy = sinon.stub(OpportunityMembers, 'destroy').callsFake(async () => { throw new Error('Error') })
    let error
    try {
      await service.updateOpportunityMembersById(commonData.user1, data.id, data.data)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubUserFindOne.calledOnce).to.be.true
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubOpportunityMembersFindAll.calledOnce).to.be.true
    expect(stubUserFindAll.calledOnce).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityMembersBulkCreate.calledOnce).to.be.true
    expect(stubOpportunityMembersDestroy.calledOnce).to.be.true
  })
  it('T26:Get opportunity members successfully', async () => {
    const data = testData.T26
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => data.result)
    const result = await service.getOpportunityMembersById(commonData.user1, data.id)
    expect(result).to.deep.eql(data.result)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T27:Faild to get opportunity members by nonexistent id', async () => {
    const data = testData.T27
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => null)
    let error
    try {
      await service.getOpportunityMembersById(commonData.user1, data.id)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
  })
  it('T28:Add and get opportunity phase successfully', async () => {
    const data = testData.T28
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => 1)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityPhaseUpdate = sinon.stub(OpportunityPhase, 'update').callsFake(async () => 1)
    const stubOpportunityPhaseCreate = sinon.stub(OpportunityPhase, 'create').callsFake(async () => 1)
    const stubOpportunityPhaseFindAll = sinon.stub(OpportunityPhase, 'findAll').callsFake(async () => data.phases)
    const result = await service.addOpportunityPhase(commonData.user1, data.id, data.phase)
    expect(result).to.deep.eql(data.result)
    expect(stubOpportunityFindOne.calledTwice).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityPhaseUpdate.calledOnce).to.be.true
    expect(stubOpportunityPhaseCreate.calledOnce).to.be.true
    expect(stubOpportunityPhaseFindAll.calledOnce).to.be.true
  })
  it('T29:Fail to add opportunity phase - database error', async () => {
    const data = testData.T29
    const stubOpportunityFindOne = sinon.stub(Opportunity, 'findOne').callsFake(async () => 1)
    const stubTransaction = sinon.stub(sequelize, 'transaction').callsFake(async () => commonData.transaction)
    const stubOpportunityPhaseUpdate = sinon.stub(OpportunityPhase, 'update').callsFake(async () => 1)
    const stubOpportunityPhaseCreate = sinon.stub(OpportunityPhase, 'create').callsFake(async () => { throw new Error('Error') })
    let error
    try {
      await service.addOpportunityPhase(commonData.user1, data.id, data.phase)
    } catch (err) {
      error = err
    }
    expect(error.message).to.eq(data.error)
    expect(stubOpportunityFindOne.calledOnce).to.be.true
    expect(stubTransaction.calledOnce).to.be.true
    expect(stubOpportunityPhaseUpdate.calledOnce).to.be.true
    expect(stubOpportunityPhaseCreate.calledOnce).to.be.true
  })
})
