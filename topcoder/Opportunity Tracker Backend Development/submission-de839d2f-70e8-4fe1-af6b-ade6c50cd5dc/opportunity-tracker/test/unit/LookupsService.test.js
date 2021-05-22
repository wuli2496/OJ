/* eslint-disable no-unused-expressions */

const expect = require('chai').expect
const sinon = require('sinon')
const { Source, Tag, TechTheme } = require('../../src/models')
const service = require('../../src/services/LookupsService')
const commonData = require('./common/CommonData')
const testData = require('./common/LookupsData')

describe('Lookups Service Test', () => {
  afterEach(() => {
    sinon.restore()
  })
  it('T01:Get sources successfully', async () => {
    const data = testData.T01
    const stubSourceFindAll = sinon.stub(Source, 'findAll').callsFake(async () => data.sources)
    const sources = await service.getSourcesLookup(commonData.user1)
    expect(sources).to.deep.eql(data.sources)
    expect(stubSourceFindAll.calledOnce).to.be.true
  })
  it('T02:Get tags successfully', async () => {
    const data = testData.T02
    const stubTagFindAll = sinon.stub(Tag, 'findAll').callsFake(async () => data.tags)
    const tags = await service.getTagsLookup(commonData.user1)
    expect(tags).to.deep.eql(data.tags)
    expect(stubTagFindAll.calledOnce).to.be.true
  })
  it('T03:Get technology themes successfully', async () => {
    const data = testData.T03
    const stubTechThemeFindAll = sinon.stub(TechTheme, 'findAll').callsFake(async () => data.techThemes)
    const techThemes = await service.getTechnologyThemesLookup(commonData.user1)
    expect(techThemes).to.deep.eql(data.techThemes)
    expect(stubTechThemeFindAll.calledOnce).to.be.true
  })
})
