const service = require('../../src/services/OpportunitiesService')
const initDb = require('../../src/init-db')
const expect = require('chai').expect

describe('Opportunities service test', () => {
  before(async () => {
    await initDb.initDB()
  })

  after(async () => {
    await initDb.initDB()
  })

  let validOpportunity
  let entityId

  beforeEach(() => {
    validOpportunity = {
      name: 'opp name',
      techThemeId: 1,
      company: 'company 1',
      sourceId: 1,
      description: 'opp description',
      tags: [
        1, 2, 3
      ],
      supportingDocuments: [
        1, 3
      ],
      usefulLinks: [
        'http://www.abc',
        'http://www.bba.com'
      ]
    }
  })

  describe('Create opportunity successfully', () => {
    /**
     * Verify the simple required fields.
     * @param input the input data
     * @returns entity from the persistence
     */
    async function verifyCommonFields (input) {
      const entity = await service.createOpportunity(123456, input)
      entityId = entity.id
      expect(entity.id).to.gt(0)
      expect(entity.name).to.eq(input.name)
      expect(entity.description).to.eq(input.description)
      expect(entity.technologyTheme.dataValues.id).to.eq(input.techThemeId)
      expect(entity.company).to.eq(input.company)
      expect(entity.source.dataValues.id).to.eq(input.sourceId)
      return entity
    }

    it('T01: Create Opportunity with full fields', async () => {
      const input = {
        name: 'test name',
        techThemeId: 1,
        company: 'company 1',
        sourceId: 1,
        description: 'opp description',
        tags: [
          1, 2, 3
        ],
        supportingDocuments: [
          1, 3
        ],
        usefulLinks: [
          'http://www.abc',
          'http://www.bba.com'
        ]
      }
      const entity = await verifyCommonFields(input)
      expect(entity.tags.length).to.eq(3)
      expect(entity.documents.length).to.eq(2)
      expect(entity.links.length).to.eq(2)
    })

    it('T02: Create Opportunity without tags', async () => {
      const input = {
        name: 'opp name',
        techThemeId: 1,
        company: 'company 1',
        sourceId: 1,
        description: 'opp description',
        supportingDocuments: [
          1, 3
        ],
        usefulLinks: [
          'http://www.abc',
          'http://www.bba.com'
        ]
      }
      const entity = await verifyCommonFields(input)
      expect(entity.tags.length).to.eq(0)
      expect(entity.documents.length).to.eq(2)
      expect(entity.links.length).to.eq(2)
    })

    it('T03: Create Opportunity without documents', async () => {
      const input = {
        name: 'opp name',
        techThemeId: 1,
        company: 'company 1',
        sourceId: 1,
        description: 'opp description',
        tags: [
          1, 2, 3
        ],
        usefulLinks: [
          'http://www.abc',
          'http://www.bba.com'
        ]
      }
      const entity = await verifyCommonFields(input)
      expect(entity.tags.length).to.eq(3)
      expect(entity.documents.length).to.eq(0)
      expect(entity.links.length).to.eq(2)
    })

    it('T04: Create Opportunity without links', async () => {
      const input = {
        name: 'opp name',
        techThemeId: 1,
        company: 'company 1',
        sourceId: 1,
        description: 'opp description',
        tags: [
          1, 2, 3
        ],
        supportingDocuments: [
          1, 3
        ]
      }
      const entity = await verifyCommonFields(input)
      expect(entity.tags.length).to.eq(3)
      expect(entity.documents.length).to.eq(2)
      expect(entity.links.length).to.eq(0)
    })
  })

  describe('Create opportunity failed', () => {
    it('T01: Create Opportunity with invalid technology theme', async () => {
      let err
      try {
        validOpportunity.techThemeId = 999
        await service.createOpportunity(123456, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "TechTheme" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T02: Create Opportunity with invalid source ', async () => {
      let err
      try {
        validOpportunity.sourceId = 999
        await service.createOpportunity(123456, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Source" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T03: Create Opportunity with invalid tags ', async () => {
      let err
      try {
        validOpportunity.tags = [999]
        await service.createOpportunity(123456, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Tag" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T04: Create Opportunity with invalid documents ', async () => {
      let err
      try {
        validOpportunity.supportingDocuments = [999]
        await service.createOpportunity(123456, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Document" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })
  })

  describe('Search opportunities successfully', () => {
    it('T01 Search opportunities without filter', async () => {
      const result = await service.searchOpportunities(123456, {})
      expect(result.total).to.eq(4)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(4)
    })

    it('T02 Search opportunities with pagination', async () => {
      const criteria = {
        page: 2,
        perPage: 3,
        sortBy: 'Company',
        sortOrder: 'desc'
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.page).to.eq(2)
      expect(result.perPage).to.eq(3)
      expect(result.result.length).to.eq(1)
    })

    it('T03 Search opportunities by company name', async () => {
      const criteria = {
        companyName: 'company 1'
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.total).to.eq(4)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(4)
    })

    it('T04 Search opportunities by product name', async () => {
      const criteria = {
        productName: 'opp name'
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.total).to.eq(3)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(3)
    })

    it('T05 Search opportunities by technology theme', async () => {
      const criteria = {
        technologyTheme: [1, 2]
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.total).to.eq(4)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(4)
    })

    it('T06 Search opportunities by owner', async () => {
      const criteria = {
        owner: 'testuser'
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.total).to.eq(4)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(4)
    })

    it('T07 Search opportunities by tags', async () => {
      const criteria = {
        tags: [1, 2, 3, 4, 9]
      }
      const result = await service.searchOpportunities(123456, criteria)
      expect(result.total).to.eq(3)
      expect(result.page).to.eq(1)
      expect(result.perPage).to.eq(20)
      expect(result.result.length).to.eq(3)
    })
  })

  describe('Get opportunity by id', () => {
    it('T01 Get Opportunity', async () => {
      const entity = await service.getOpportunity(123456, entityId)
      expect(entity.id).to.eq(entityId)
      expect(entity.ownerId).to.eq(123456)
      expect(entity.opportunityDetails.name).to.eq(validOpportunity.name)
      expect(entity.opportunityDetails.technologyTheme).to.be.not.eq(null)
      expect(entity.opportunityDetails.source).to.be.not.eq(null)
      expect(entity.opportunityDetails.tags).to.be.not.eq(null)
      expect(entity.opportunityDetails.documents).to.be.not.eq(null)
      expect(entity.opportunityDetails.links).to.be.not.eq(null)
      expect(entity.opportunityDetails.views).to.be.not.eq(null)
      expect(entity.opportunityPhases).to.be.not.eq(null)
      expect(entity.discussions).to.be.not.eq(null)
    })

    it('T02 Get Opportunity not exist', async () => {
      let err
      try {
        await service.getOpportunity(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Get opportunity details by id', () => {
    it('T01 Get Opportunity details', async () => {
      const entity = await service.getOpportunityDetails(123456, entityId)
      // No 'id' is returned
      expect(entity.id).to.eq(undefined)
      expect(entity.name).to.eq(validOpportunity.name)
      expect(entity.company).to.eq(validOpportunity.company)
      expect(entity.technologyTheme).to.be.not.eq(null)
      expect(entity.source).to.be.not.eq(null)
      expect(entity.tags).to.be.not.eq(null)
      expect(entity.documents).to.be.not.eq(null)
      expect(entity.links).to.be.not.eq(null)
      expect(entity.views).to.be.not.eq(null)
    })

    it('T02 Get Opportunity details not exist', async () => {
      let err
      try {
        await service.getOpportunityDetails(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Update opportunity details successfully', () => {
    /**
     * Verify the simple required fields.
     * @param input the input data
     * @returns entity from the persistence
     */
    async function verifyCommonFields (input) {
      const entity = await service.updateOpportunityDetails(123456, entityId, input)
      expect(entity.name).to.eq(input.name)
      expect(entity.description).to.eq(input.description)
      expect(entity.technologyTheme.dataValues.id).to.eq(input.techThemeId)
      expect(entity.company).to.eq(input.company)
      expect(entity.source.dataValues.id).to.eq(input.sourceId)
      return entity
    }

    it('T01: Update Opportunity defails', async () => {
      validOpportunity.tags = [1]
      validOpportunity.supportingDocuments = [2, 3]
      validOpportunity.usefulLinks = [
        'http://www.abc',
        'http://www.bba.com',
        'http://www.updated.com'
      ]
      const entity = await verifyCommonFields(validOpportunity)
      expect(entity.tags.length).to.eq(1)
      expect(entity.documents.length).to.eq(2)
      expect(entity.links.length).to.eq(3)
    })

    it('T02 Update Opportunity defails not exist', async () => {
      let err
      try {
        await service.updateOpportunityDetails(123456, 999, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })

    it('T03: Update Opportunity details with invalid technology theme', async () => {
      let err
      try {
        validOpportunity.techThemeId = 999
        await service.updateOpportunityDetails(123456, entityId, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "TechTheme" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T04: Update Opportunity with invalid source ', async () => {
      let err
      try {
        validOpportunity.sourceId = 999
        await service.updateOpportunityDetails(123456, entityId, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Source" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T05: Update Opportunity with invalid tags ', async () => {
      let err
      try {
        validOpportunity.tags = [999]
        await service.updateOpportunityDetails(123456, entityId, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Tag" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })

    it('T06: Update Opportunity with invalid documents ', async () => {
      let err
      try {
        validOpportunity.supportingDocuments = [999]
        await service.updateOpportunityDetails(123456, entityId, validOpportunity)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Document" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })
  })

  describe('Get opportunity phases by id', () => {
    it('T01 Get Opportunity phases', async () => {
      const entity = await service.getOpportunityPhases(123456, entityId)
      expect(entity.length).to.gte(1)
      expect(entity[0].id).to.gte(1)
      expect(entity[0].opportunityId).to.gte(1)
      expect(entity[0].status).to.eq('UnderEvaluation')
      expect(entity[0].startDate).to.be.not.eq(null)
      expect(entity[0].endDate).to.be.eq(null)
      expect(entity[0].isActive).to.eq(true)
    })

    it('T02 Get Opportunity phases not exist', async () => {
      let err
      try {
        await service.getOpportunityPhases(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Update opportunity phases by id', () => {
    it('T01 Update Opportunity phases', async () => {
      const entity =
        await service.updateOpportunityPhases(123456, entityId, { status: 'EvaluationFail' })
      expect(entity.length).to.gte(1)
      expect(entity[0].id).to.gte(1)
      expect(entity[0].opportunityId).to.gte(1)
      expect(entity[0].status).to.eq('UnderEvaluation')
      expect(entity[0].isActive).to.eq(false)
    })

    it('T02 Update Opportunity phases not exist', async () => {
      let err
      try {
        await service.updateOpportunityPhases(123456, 999, { status: 'EvaluationFail' })
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Get opportunity members by id', () => {
    it('T01 Get Opportunity members', async () => {
      const entity = await service.getOpportunityMembers(123456, entityId)
      expect(entity.owner.id).to.eq(123456)
      expect(entity.owner.name).to.eq('testuser')
      expect(entity.owner.profilePic).to.eq('p_4.png')
      expect(entity.members.length).to.eq(0)
    })

    it('T02 Get Opportunity members not exist', async () => {
      let err
      try {
        await service.getOpportunityMembers(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Update opportunity members by id', () => {
    it('T01 Update Opportunity members', async () => {
      await service.updateOpportunityMembers(123456, entityId, { members: [1, 2, 3] })
      const entity = await service.getOpportunityMembers(123456, entityId)
      expect(entity.owner.id).to.eq(123456)
      expect(entity.owner.name).to.eq('testuser')
      expect(entity.owner.profilePic).to.eq('p_4.png')
      expect(entity.members.length).to.eq(3)
    })

    it('T02 Update Opportunity members opportunity not exist', async () => {
      let err
      try {
        await service.updateOpportunityMembers(123456, 999, { members: [1, 2, 3] })
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })

    it('T03 Update Opportunity members member not exist', async () => {
      let err
      try {
        await service.updateOpportunityMembers(123456, entityId, { members: [999] })
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "User" doesn\'t exists.')
      expect(err.httpStatus).to.eq(400)
    })
  })

  describe('delete opportunity by id', () => {
    it('T01 Delete Opportunity successfully, first delete it as a none owner', async () => {
      // try delete as none owner
      let err
      try {
        await service.deleteOpportunity(1, entityId)
      } catch (error) {
        err = error
      }
      // the deletion should be rejected
      expect(err.message).to.eq('You are not the owner of id: ' + entityId + ' "Opportunity".')
      expect(err.httpStatus).to.eq(401)

      await service.deleteOpportunity(123456, entityId)

      try {
        await service.getOpportunityDetails(123456, entityId)
      } catch (error) {
        err = error
      }
      // the entity should be deleted
      expect(err.message).to.eq('id: ' + entityId + ' "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })

    it('T02 Delete Opportunity not exist', async () => {
      let err
      try {
        await service.deleteOpportunity(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })
})
