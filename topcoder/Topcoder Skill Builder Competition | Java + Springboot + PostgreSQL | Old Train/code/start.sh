#!/bin/bash

pg_ctlcluster $PG_MAJOR main start

gosu postgres psql -c "ALTER USER ${POSTGRES_USER} PASSWORD '${POSTGRES_PASSWORD}';"

gosu postgres psql -f "/docker-entrypoint-initdb.d/1-schema.sql"

gosu postgres psql -f "/docker-entrypoint-initdb.d/2-data.sql"

java -jar /app/app.jar
