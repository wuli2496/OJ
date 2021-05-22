const T01 = {
  user: {
    id: 1
  },
  techTheme: {
    dataValues: {
      icon: 'icon1',
      name: 'tech1'
    }
  },
  source: {
    dataValues: {
      id: 1,
      name: 'source1'
    }
  },
  tags: [
    {
      id: 1,
      label: 'tag1'
    }
  ],
  docs: [
    {
      id: 1,
      name: 'tag1',
      documentType: 'pdf',
      location: 'loc'
    }
  ],
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  newOpportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  result: {
    id: 1,
    name: 'New Opportunity',
    updatedOn: '2021-05-16T15:33:36.087Z',
    technologyTheme: {
      icon: 'icon1',
      name: 'tech1'
    },
    company: 'company',
    source: {
      id: 1,
      name: 'source1'
    },
    description: 'desc',
    tags: ['tag1'],

    documents: [{
      name: 'tag1',
      documentType: 'pdf',
      location: 'loc'
    }],
    links: ['link']
  }
}

const T02 = {
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: "user with username test@test.test doesn't exist"
}

const T03 = {
  user: {
    id: 1
  },
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: 'id: 1 "TechTheme" doesn\'t exists.'
}

const T04 = {
  user: {
    id: 1
  },
  techTheme: {
    dataValues: {
      icon: 'icon1',
      name: 'tech1'
    }
  },
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: 'id: 1 "Source" doesn\'t exists.'
}

const T05 = {
  user: {
    id: 1
  },
  techTheme: {
    dataValues: {
      icon: 'icon1',
      name: 'tech1'
    }
  },
  source: {
    dataValues: {
      id: 1,
      name: 'source1'
    }
  },
  tags: [],
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: 'tag ids 1 not found'
}

const T06 = {
  user: {
    id: 1
  },
  techTheme: {
    dataValues: {
      icon: 'icon1',
      name: 'tech1'
    }
  },
  source: {
    dataValues: {
      id: 1,
      name: 'source1'
    }
  },
  tags: [
    {
      id: 1,
      label: 'tag1'
    }
  ],
  docs: [],
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: 'document ids 1 not found'
}

const T07 = {
  user: {
    id: 1
  },
  techTheme: {
    dataValues: {
      icon: 'icon1',
      name: 'tech1'
    }
  },
  source: {
    dataValues: {
      id: 1,
      name: 'source1'
    }
  },
  tags: [
    {
      id: 1,
      label: 'tag1'
    }
  ],
  docs: [
    {
      id: 1,
      name: 'tag1',
      documentType: 'pdf',
      location: 'loc'
    }
  ],
  opportunity: {
    name: 'New Opportunity',
    technologyTheme: 1,
    company: 'company',
    source: 1,
    description: 'desc',
    tags: [1],
    supportingDocuments: [1],
    usefulLinks: ['link']
  },
  error: 'Error'
}

const T08 = {
  criteria: {
    page: 1,
    perPage: 10,
    sortBy: 'ProductName',
    sortOrder: 'asc',
    status: 'UnderEvaluation',
    productName: 'New Opportunity',
    companyName: 'company',
    technologyTheme: '1,2',
    lastUpdatedStart: '2021-05-15',
    lastUpdatedEnd: '2021-05-20',
    owner: 'emre',
    tags: '1,2'
  },
  response: {
    count: 1,
    rows: [
      {
        id: 7,
        description: 'desc',
        name: 'New Opportunity',
        company: 'company',
        status: 'UnderEvaluation',
        lastUpdatedOn: '2021-05-16T17:42:02.191Z',
        technologyTheme: {
          icon: 'icon1',
          name: 'name1'
        },
        owner: {
          id: 1,
          name: 'emre',
          profilePicture: 'picture'
        },
        tags: [
          {
            id: 1,
            label: 'tag1'
          },
          {
            id: 2,
            label: 'tag2'
          },
          {
            id: 3,
            label: 'tag3'
          }
        ]
      }
    ]
  },
  result: {
    total: 1,
    result: [
      {
        id: 7,
        description: 'desc',
        name: 'New Opportunity',
        company: 'company',
        status: 'UnderEvaluation',
        lastUpdatedOn: '2021-05-16T17:42:02.191Z',
        technologyTheme: {
          icon: 'icon1',
          name: 'name1'
        },
        owner: {
          id: 1,
          name: 'emre',
          profilePicture: 'picture'
        },
        tags: [
          {
            id: 1,
            label: 'tag1'
          },
          {
            id: 2,
            label: 'tag2'
          },
          {
            id: 3,
            label: 'tag3'
          }
        ]
      }
    ],
    page: 1,
    perPage: 10
  }
}

const T09 = {
  criteria: {
    page: 1,
    perPage: 10,
    sortBy: 'ProductName',
    sortOrder: 'asc',
    status: 'UnderEvaluation',
    productName: 'New Opportunity',
    companyName: 'company',
    technologyTheme: '1,2,a',
    lastUpdatedStart: '2021-05-15',
    lastUpdatedEnd: '2021-05-20',
    owner: 'emre',
    tags: '1,2'
  },
  error: 'technologyTheme id a is not a valid number'
}

const T10 = {
  criteria: {
    page: 1,
    perPage: 10,
    sortBy: 'ProductName',
    sortOrder: 'asc',
    status: 'UnderEvaluation',
    productName: 'New Opportunity',
    companyName: 'company',
    technologyTheme: '1,2',
    lastUpdatedStart: '2021-05-15',
    lastUpdatedEnd: '2021-05-20',
    owner: 'emre',
    tags: '1,2,a'
  },
  error: 'tag id a is not a valid number'
}

const T11 = {
  id: 3,
  opportunity: {
    dataValues: {
      id: 3,
      ownerId: 1,
      name: 'name',
      updatedOn: '2021-05-16T13:25:07.260Z',
      company: 'new company',
      description: 'desc',
      technologyTheme: {
        icon: 'icon1',
        name: 'name1'
      },
      source: {
        id: 2,
        name: 'source1'
      },
      owner: {
        id: 1,
        name: 'emre',
        profilePicture: 'picture'
      },
      tags: [{
        id: 1,
        label: 'tag1'
      }],
      documents: [],
      links: [],
      phases: [{
        id: 2,
        status: 'UnderEvaluation',
        startDate: '2021-05-16T03:29:46.589Z',
        endDate: '2021-05-16T13:17:45.443Z'
      }],
      discussions: []
    }
  },
  result: {
    id: 3,
    ownerId: 1,
    name: 'name',
    updatedOn: '2021-05-16T13:25:07.260Z',
    company: 'new company',
    description: 'desc',
    technologyTheme: {
      icon: 'icon1',
      name: 'name1'
    },
    source: {
      id: 2,
      name: 'source1'
    },
    owner: {
      id: 1,
      name: 'emre',
      profilePicture: 'picture'
    },
    tags: ['tag1'],
    documents: [],
    links: [],
    phases: [{
      id: 2,
      status: 'UnderEvaluation',
      startDate: '2021-05-16T03:29:46.589Z',
      endDate: '2021-05-16T13:17:45.443Z',
      isActive: false
    }],
    discussions: []
  }
}
T11.opportunity.toJSON = () => T11.opportunity.dataValues

const T12 = {
  id: 1,
  error: 'id: 1 "Opportunity" doesn\'t exists.'
}

const T13 = {
  id: 1,
  opportunity: {}
}

const T14 = {
  id: 1,
  error: 'id: 1 "Opportunity" doesn\'t exists.'
}
T13.opportunity.destroy = async () => {}

const T15 = {
  id: 1,
  user: {
    id: 1
  },
  data: {
    name: 'name',
    technologyTheme: 2,
    company: 'company',
    source: 2,
    description: 'desc',
    tags: [3, 4],
    supportingDocuments: [3, 4],
    usefulLinks: ['link']
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z',
      links: [{ link: 'link' }],
      tags: [{ label: 'tag3' }, { label: 'tag4' }]
    }
  },
  tags: [{ id: 3, label: 'tag3' }, { id: 4, label: 'tag4' }],
  docs: [{ id: 3, name: 'doc3' }, { id: 4, name: 'doc4' }],
  result: {
    id: 1,
    name: 'New Opportunity',
    tech_theme_id: 1,
    sourceId: 1,
    description: 'desc',
    company: 'company',
    owner_id: 1,
    updatedOn: '2021-05-16T15:33:36.087Z',
    links: ['link'],
    tags: ['tag3', 'tag4']
  }
}
T15.opportunity.update = async () => {}
T15.opportunity.toJSON = () => T15.opportunity.dataValues

const T16 = {
  id: 1,
  user: {
    id: 2
  },
  data: {
    name: 'name',
    technologyTheme: 2,
    company: 'company',
    source: 2,
    description: 'desc',
    tags: [3, 4],
    supportingDocuments: [3, 4],
    usefulLinks: ['link']
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  error: 'Only the owner is allowed to perform this operation.'
}

const T17 = {
  id: 1,
  user: {
    id: 1
  },
  data: {
    name: 'name',
    technologyTheme: 2,
    company: 'company',
    source: 2,
    description: 'desc',
    tags: [3, 4],
    supportingDocuments: [3, 4],
    usefulLinks: ['link']
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  tags: [{ id: 4, label: 'tag4' }],
  error: 'tag ids 3 not found'
}

const T18 = {
  id: 1,
  user: {
    id: 1
  },
  data: {
    name: 'name',
    technologyTheme: 2,
    company: 'company',
    source: 2,
    description: 'desc',
    tags: [3, 4],
    supportingDocuments: [3, 4],
    usefulLinks: ['link']
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  tags: [{ id: 3, label: 'tag3' }, { id: 4, label: 'tag4' }],
  docs: [{ id: 4, name: 'doc4' }],
  error: 'document ids 3 not found'
}

const T19 = {
  id: 1,
  error: 'id: 1 "Opportunity" doesn\'t exists.'
}

const T20 = {
  id: 1,
  user: {
    id: 1
  },
  post: {
    text: 'post'
  }
}

const T21 = {
  id: 1,
  discussions: [{
    date: '2021-05-16T10:57:01.839Z',
    text: 'top level',
    replyCount: 3,
    author: {
      id: 1,
      name: 'emre',
      profilePicture: 'picture'
    }
  }]
}

const T22 = {
  id: 1,
  user: {
    id: 1
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  currentMembers: [{ user_id: 1 }, { user_id: 2 }],
  newMembersResult: [{ id: 3 }, { id: 4 }],
  data: { members: [3, 4] }
}

const T23 = {
  id: 1,
  user: {
    id: 2
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  data: { members: [3, 4] },
  error: 'Only the owner is allowed to perform this operation.'
}

const T24 = {
  id: 1,
  user: {
    id: 1
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  currentMembers: [{ user_id: 1 }, { user_id: 2 }],
  newMembersResult: [{ id: 4 }],
  data: { members: [3, 4] },
  error: 'User ids 3 not found'
}

const T25 = {
  id: 1,
  user: {
    id: 1
  },
  opportunity: {
    dataValues: {
      id: 1,
      name: 'New Opportunity',
      tech_theme_id: 1,
      sourceId: 1,
      description: 'desc',
      company: 'company',
      owner_id: 1,
      updatedOn: '2021-05-16T15:33:36.087Z'
    }
  },
  currentMembers: [{ user_id: 1 }, { user_id: 2 }],
  newMembersResult: [{ id: 3 }, { id: 4 }],
  data: { members: [3, 4] },
  error: 'Error'
}

const T26 = {
  id: 1,
  result: {
    owner: {
      id: 1,
      name: 'emre',
      profilePicture: 'picture'
    },
    members: [
      {
        id: 3,
        name: 'emre2',
        profilePicture: 'picture'
      }
    ]
  }
}

const T27 = {
  id: 1,
  error: 'id: 1 "Opportunity" doesn\'t exists.'
}

const T28 = {
  id: 1,
  phase: {
    status: 'EvaluationPass'
  },
  phases: [
    {
      id: 2,
      status: 'UnderEvaluation',
      startDate: '2021-05-16T03:29:46.589Z',
      endDate: '2021-05-16T13:17:45.443Z'
    },
    {
      id: 3,
      status: 'EvaluationPass',
      startDate: '2021-05-16T13:13:24.128Z',
      endDate: null
    }],
  result: [
    {
      id: 2,
      status: 'UnderEvaluation',
      startDate: '2021-05-16T03:29:46.589Z',
      endDate: '2021-05-16T13:17:45.443Z',
      isActive: false
    },
    {
      id: 3,
      status: 'EvaluationPass',
      startDate: '2021-05-16T13:13:24.128Z',
      endDate: null,
      isActive: true
    }]
}

const T29 = {
  id: 1,
  phase: {
    status: 'EvaluationPass'
  },
  error: 'Error'
}
module.exports = {
  T01,
  T02,
  T03,
  T04,
  T05,
  T06,
  T07,
  T08,
  T09,
  T10,
  T11,
  T12,
  T13,
  T14,
  T15,
  T16,
  T17,
  T18,
  T19,
  T20,
  T21,
  T22,
  T23,
  T24,
  T25,
  T26,
  T27,
  T28,
  T29
}
