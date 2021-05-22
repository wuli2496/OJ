# Hess Opportunities API

## Unit test Coverage

47 passing (4s)


File                        | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s
----------------------------|---------|----------|---------|---------|-----------------------------
All files                   |   95.03 |    84.55 |   94.67 |    95.2 |
config                     |     100 |      100 |     100 |     100 |
default.js                |     100 |      100 |     100 |     100 |
test.js                   |     100 |      100 |     100 |     100 |
src                        |   80.65 |       50 |   33.33 |   80.65 |
init-db.js                |   80.65 |       50 |   33.33 |   80.65 | 10,39-45
src/common                 |     100 |       50 |     100 |     100 |
errors.js                 |     100 |       50 |     100 |     100 | 22
src/models                 |   95.81 |    89.66 |   97.37 |   95.79 |
DiscussionPost.js         |     100 |      100 |     100 |     100 |
Document.js               |     100 |      100 |     100 |     100 |
Opportunity.js            |   94.64 |      100 |     100 |   94.55 | 110-112
OpportunityDocument.js    |     100 |      100 |     100 |     100 |
OpportunityLink.js        |     100 |      100 |     100 |     100 |
OpportunityMembers.js     |     100 |      100 |     100 |     100 |
OpportunityPhase.js       |     100 |      100 |     100 |     100 |
OpportunityTag.js         |     100 |      100 |     100 |     100 |
OpportunityViews.js       |     100 |      100 |     100 |     100 |
Source.js                 |     100 |      100 |     100 |     100 |
Tag.js                    |     100 |      100 |     100 |     100 |
TechTheme.js              |     100 |      100 |     100 |     100 |
User.js                   |      76 |       50 |      80 |      76 | 49,60-69
index.js                  |     100 |      100 |     100 |     100 |
src/services               |   95.96 |    78.43 |   96.88 |   96.36 |
DiscussionPostsService.js |     100 |      100 |     100 |     100 |
LookupsService.js         |     100 |      100 |     100 |     100 |
OpportunitiesService.js   |   94.34 |    80.85 |      95 |   94.87 | 43,46,57-58,162,373-374,438
UsersService.js           |     100 |       50 |     100 |     100 | 15-16
test                       |     100 |      100 |     100 |     100 |
prepare.js                |     100 |      100 |     100 |     100 |

## OKTA authentication
From the backend(this application), we will use the token which provided in the Authorization Header by the frontend to validate and authenticate.
You can refer to [this](https://developer.okta.com/blog/2019/02/14/modern-token-authentication-in-node-with-express#request-a-jwt) about how to prepare the jwt token.
In the backend, the claims will be checked to find if it is matching the `okta_username` value from `user` table.