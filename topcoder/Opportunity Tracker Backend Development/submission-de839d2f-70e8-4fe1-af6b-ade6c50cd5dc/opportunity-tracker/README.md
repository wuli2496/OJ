# Opportunity Tracker Api

## System requirements

- [NodeJS](https://nodejs.org/en/) - Version v14.15.4 was used during development
- [Microsoft SQL Server](https://www.microsoft.com/en-gb/sql-server/sql-server-2019)
- [Swagger Editor](https://editor.swagger.io/)

## Configuration

- All configurations can be found and set in `./config/default.js`

- The following parameters can be set in config files or in env variables by providing `.env` file:

- LOG_LEVEL: the log level, default is 'debug'
- API_VERSION: the API version, default is v1
- PORT: the server port, default is 3000
- DB_HOST: the PostgreSQL database URL
- DB_PORT: the database port
- DB_NAME: the database name
- DB_USER: the database username
- DB_PWD: the database password
- DB_SCHEMA: the database schema
- DB_TRUST: set it to true for local dev / self-signed certs
- DB_ENCRYPT: set it to true for azure
- API_BASE_URL: the API base URL
- DEFAULT_DATA_FILE_PATH: the default path for importing and exporting data
- CLIENTID: the client id of okta application
- ISSUER: the okta url for authentication
- AUD: audience claim of okta application for authentication
- DISABLEHTTPSCHECK: disable https check flag for okta authentication

## Setup SQL Server with Docker

- Run `cd docker-mssql`
- Run `docker-compose up -d`

## Local deployment

- Run `npm i` to install the dependencies
- Follow the steps for "Setup SQL Server with Docker"
- Run `npm run init-db` to init the SQL Server DB (pass the `-- force` flag to drop existing data)
- Optionally, run `npm run data:import` to init demo lookups data
- Optionally, if you have not yet used the `npm run init-db` command to initialize the database you can init the database with demo data using the sql script located in `./docs/script.sql`
- Optionally, run `npm run lint` to perform a lint test using `StandardJS`
- Run `npm start` to start the API

## Docker deployment

- Run `cd docker`
- Run `docker-compose up -d`
- Wait until images are built and database and api are started successfully
- Run `docker exec -it opportunity-tracker-api npm run init-db` to init the SQL Server DB (pass the `-- force` flag to drop existing data)
- Optionally, run `docker exec -it opportunity-tracker-api npm run data:import` to init demo lookups data
- Optionally, if you have not yet used the docker command to initialize the database you can init the database with demo data using the sql script located in `./docs/script.sql`

## NPM Commands

| Command                          | Description                                                                                                                   |
| -------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| `npm start`                      | Start the application.                                                                                                        |
| `npm run dev`                    | Start app in the development mode. using  `nodemon`                                                                           |
| `npm run lint`                   | Check for lint errors.                                                                                                        |
| `npm run lint:fix`               | Check for for lint errors and fix error automatically when possible.                                                          |
| `npm run init-db`                | Initializes Database.                                                                                                         |
| `npm run data:export <filePath>` | Exports data from database into filePath (./data/demo-data.json is used as default). Use -- --force flag to skip confirmation |
| `npm run data:import <filePath>` | Imports data into database from filePath (./data/demo-data.json is used as default). Use -- --force flag to skip confirmation |
| `npm run test`                   | Run unit tests.                                                                                                               |
| `npm run cov`                    | Code Coverage Report.                                                                                                         |

## Swagger

- The Swagger definition is located in `./docs/swagger.yml`

## Testing

- Run `npm run test` to execute unit tests
- Run `npm run cov` to execute unit tests and generate coverage report.