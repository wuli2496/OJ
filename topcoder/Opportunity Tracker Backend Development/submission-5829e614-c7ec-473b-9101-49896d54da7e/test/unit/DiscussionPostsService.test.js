const opptunityService = require('../../src/services/OpportunitiesService')
const service = require('../../src/services/DiscussionPostsService')
const initDb = require('../../src/init-db')
const expect = require('chai').expect

describe('DiscussionPosts service test', () => {
  let oppId
  before(async () => {
    await initDb.initDB()
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
    const entity = await opptunityService.createOpportunity(123456, input)
    oppId = entity.id
  })

  after(async () => {
    await initDb.initDB()
  })

  describe('Create/Get discussion post successfully', () => {
    it('T01: Create discussion post and get it', async () => {
      let posts = await service.getDiscussionPosts(123456, oppId)
      expect(posts.length).to.eq(0)
      await service.createDiscussionPost(123456, oppId, { text: 'this is a post' })
      posts = await service.getDiscussionPosts(123456, oppId)
      expect(posts.length).to.eq(1)
      expect(posts[0].text).to.eq('this is a post')
      expect(posts[0].replyCount).to.eq(0)
      expect(posts[0].author.id).to.eq(123456)
      expect(posts[0].author.name).to.eq('testuser')
      expect(posts[0].author.profilePic).to.eq('p_4.png')
    })
  })

  describe('Get discussion post failed', () => {
    it('T01: Get discussion post with not exist opportunity id', async () => {
      let err
      try {
        await service.getDiscussionPosts(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Create discussion post failed', () => {
    it('T01: Create discussion post with not exist opportunity id', async () => {
      let err
      try {
        await service.createDiscussionPost(123456, 999, { text: 'this is a post' })
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "Opportunity" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Create/Get discussion post reply successfully', () => {
    it('T01: Create discussion post reply and get it', async () => {
      let posts = await service.getDiscussionPostReplies(123456, 1)
      expect(posts.length).to.eq(0)
      await service.createDiscussionPostReply(123456, 1, { text: 'this is a reply' })
      posts = await service.getDiscussionPostReplies(123456, 1)
      expect(posts.length).to.eq(1)
      expect(posts[0].text).to.eq('this is a reply')
      expect(posts[0].replyCount).to.eq(0)
      expect(posts[0].author.id).to.eq(123456)
      expect(posts[0].author.name).to.eq('testuser')
      expect(posts[0].author.profilePic).to.eq('p_4.png')
      // check the parent post, the reply count should be updated by 1
      posts = await service.getDiscussionPosts(123456, oppId)
      expect(posts[0].replyCount).to.eq(1)
    })
  })

  describe('Get discussion post reply failed', () => {
    it('T01: Get discussion post reply with not exist post id', async () => {
      let err
      try {
        await service.getDiscussionPostReplies(123456, 999)
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "DiscussionPost" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })

  describe('Create discussion post reply failed', () => {
    it('T01: Create discussion post reply with not exist post id', async () => {
      let err
      try {
        await service.createDiscussionPostReply(123456, 999, { text: 'this is a reply' })
      } catch (error) {
        err = error
      }
      expect(err.message).to.eq('id: 999 "DiscussionPost" doesn\'t exists.')
      expect(err.httpStatus).to.eq(404)
    })
  })
})
