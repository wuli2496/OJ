/* eslint-disable no-unused-expressions */

const expect = require('chai').expect
const sinon = require('sinon')
const models = require('../../src/models')

describe('Models Test', () => {
  afterEach(() => {
    sinon.restore()
  })
  for (const [model, name, failCase, successCase] of [
    [models.Criteria, 'Criteria', 'T01', 'T02'],
    [models.DiscussionPost, 'DiscussionPost', 'T03', 'T04'],
    [models.Document, 'Document', 'T05', 'T06'],
    [models.Evaluation, 'Evaluation', 'T07', 'T08'],
    [models.EvaluationResponse, 'EvaluationResponse', 'T09', 'T10'],
    [models.Opportunity, 'Opportunity', 'T11', 'T12'],
    [models.OpportunityLink, 'OpportunityLink', 'T13', 'T14'],
    [models.OpportunityPhase, 'OpportunityPhase', 'T15', 'T16'],
    [models.Source, 'Source', 'T17', 'T18'],
    [models.Tag, 'Tag', 'T19', 'T20'],
    [models.TechTheme, 'TechTheme', 'T21', 'T22'],
    [models.User, 'User', 'T23', 'T24']
  ]) {
    it(`${failCase}:Fail to find ${name} by id`, async () => {
      const stubFindOne = sinon.stub(model, 'findOne').callsFake(async () => undefined)
      let error
      try {
        await model.findById(1)
      } catch (err) {
        error = err
      }
      expect(error.message).to.eq(`id: 1 "${name}" doesn't exists.`)
      expect(stubFindOne.calledOnce).to.be.true
    })
    it(`${successCase}:Find ${name} by id successfully`, async () => {
      const stubFindOne = sinon.stub(model, 'findOne').callsFake(async () => 1)
      expect(await model.findById(1)).to.eq(1)
      expect(stubFindOne.calledOnce).to.be.true
    })
  }
})
