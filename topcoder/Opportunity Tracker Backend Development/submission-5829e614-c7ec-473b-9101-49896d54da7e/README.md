# Hess Opportunities API

## Tech Stack

- [Node.js](https://nodejs.org/) v12
- [MSSQL](https://www.microsoft.com/en-us/sql-server)

## Local Setup

### Requirements

- [Node.js](https://nodejs.org/en/) v12+
- [Docker](https://www.docker.com/)
- [Docker-Compose](https://docs.docker.com/compose/install/)

### Configuration

Configuration for the application is at `config/default.js`.
The following parameters can be set in config files or in env variables:
- LOG_LEVEL: the log level, default is 'debug'
- PORT: the server port, default is 3000
- BASE_PATH: the basic path, default is '/api/v1'
- DB_DATABASE: the database name, default is 'OP_TRACKER'
- DB_USERNAME: the database username, default is 'sa'
- DB_PASSWORD: the database password, default is 'yourStrong@P!ssword'
- DB_HOST: the database host, default is 'localhost'
- DB_DIALECT: the database dialect, default is 'mssql'
- DB_PORT: the database port, default is '1433'
- DB_SCHEMA_NAME: the database schema, default is 'hess_opportunity'
- DB_LOGGING: enable db logging or not, default is false
- INITIAL_PHASE_STATUS: the initial status for the created opportunity, default is 'UnderEvaluation' 

Configuration for testing is at `config/test.js`. The database configuration is used for testing.

### Available commands
- Install dependencies `npm install`
- Run lint `npm run lint`
- Run lint fix `npm run lint:fix`
- Clear and init db `npm run init-db`
- Run test `npm run test`
- Run test coverage `npm run cov`
- Start app `npm start`
- App is running at `http://localhost:3000`

### Steps to run locally

1. Install npm dependencies

   ```bash
   npm install
   ```

2. Local config

   Config the environment as described in `Configuration` sesion.

3. Start the local MSSQL server

   ```bash
   cd local
   docker-compose up
   ```

4. Init DB

   ```bash
   npm run init-db force
   ```

   This command will do 3 things:

   - create Database tables (drop if exists)
   - inserting the lookup data

5. Start the API

   ```bash
   npm start
   ```

   The API will be served on `http://localhost:3000`.

## Testing

- Run `npm run test` to execute unit tests
- Run `npm run cov` to execute unit tests and generate coverage report.

## Postman Collection

- Import the Postman collection and environment  from `docs/postman`
- Expand and run the requests from the `hess-opportunity`

## DDL and testing data
The sql files are provided from `docs/sql`.
- hess_opportunity_ddl.sql, the ddl.
- hess_opportunity-with-data.sql, the ddl and testing data included.
