const T01 = {
  user: {
    id: 1
  },
  id: 1,
  discussionReply: {
    text: 'reply'
  }
}

const T02 = {
  id: 1,
  discussionReply: {
    text: 'reply'
  },
  error: "user with username test@test.test doesn't exist"
}

const T03 = {
  id: 1,
  discussionReply: {
    text: 'reply'
  },
  error: 'id: 1 "DiscussionPost" doesn\'t exists.'
}

const T04 = {
  id: 1,
  replies: [{
    date: '2021-05-16T15:33:36.087Z',
    text: 'test',
    replyCount: 3,
    author: {
      id: 1,
      name: 'emre',
      profilePicture: 'picture'
    }
  }]
}

module.exports = {
  T01,
  T02,
  T03,
  T04
}
