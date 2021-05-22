const sinon = require('sinon')
const expect = require('chai').expect
const helper = require('../../src/helpers')
const testData = require('./common/HelpersData')

describe('helper test', () => {
  before(() => {

  })
  beforeEach(() => {
    sinon.restore()
  })
  describe('Response Headers test', () => {
    it('Set http response', () => {
      const data = testData.T01
      helper.setResHeaders(data.req, data.res, data.result)
      expect(data.headers).to.deep.eql(data.expected)
    })
  })

  describe('autoWrapExpress test', () => {
    it('autoWrapExpress with sync function', () => {
      const fn = () => { return null }
      const res = helper.autoWrapExpress(fn)
      expect(fn).to.eql(res)
    })

    it('autoWrapExpress with async function', () => {
      const fn = async () => { return null }
      const res = helper.autoWrapExpress(fn)
      res()
      expect(res).to.be.a('function')
    })

    it('autoWrapExpress with function array', () => {
      const fn = [() => { return null }]
      const res = helper.autoWrapExpress(fn)
      expect(res).to.be.a('array')
    })

    it('autoWrapExpress with object', () => {
      const obj = {
        fn: () => { return null }
      }
      const res = helper.autoWrapExpress(obj)
      expect(res).to.be.a('object')
      expect(res.fn).to.be.a('function')
    })
  })
})
