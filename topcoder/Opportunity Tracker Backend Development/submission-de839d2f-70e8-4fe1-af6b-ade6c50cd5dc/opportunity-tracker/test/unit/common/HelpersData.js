const T01 = {
  req: {
    protocol: 'https',
    baseUrl: '',
    query: {},
    path: '/v1/users'
  },
  res: {},
  result: {
    total: 30,
    perPage: 10,
    page: 2
  },
  headers: {},
  expected: {
    'X-Prev-Page': 1,
    'X-Next-Page': 3,
    'X-Page': 2,
    'X-Per-Page': 10,
    'X-Total': 30,
    'X-Total-Pages': 3,
    Link: '<https://localhost:3000/v1/users?page=1>; rel="first", <https://localhost:3000/v1/users?page=3>; rel="last", <https://localhost:3000/v1/users?page=1>; rel="prev", <https://localhost:3000/v1/users?page=3>; rel="next"'
  }
}
T01.req.get = () => 'localhost:3000'
T01.res.set = (key, value) => { T01.headers[key] = value }
module.exports = {
  T01
}
