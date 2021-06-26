
## configure
create database db_test

In the file application.properties,set name and password.
spring.datasource.username=<name>
spring.datasource.password=<password>

## run the microservice
mvn spring-boot:run

## verify
use postman import doc/todos.postman_collection.json