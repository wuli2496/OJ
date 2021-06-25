# Odyssey Relationship Manager Appointment Service

# Prerequisites

- Java 11 or above
- Maven 3.6 or above
- Docker
- ngrok [Download and connect an account (register if you don't have one)](https://ngrok.com/download), required for receiving change notifications from Microsoft.

## Configuration

| Config                    	    | Description                                  | Default          |
| ----------------------------- | -------------------------------------------- | ---------------- |
| server.port               	    | Port to listen for incoming requests         | 8103            |
| spring.application.name   	    | Application name to be used in Spring Sleuth | cyn-relationship-manager-appointment-server                       |
| server.servlet.context-path   | The root path of the rest endpoint           | /v1/appointments/service/customer                      |
| spring.aop.auto           	    | Autoconfiguration of spring aop       | false      |
| spring.datasource.url | The Mysql Database URL | jdbc:mysql://localhost:3306/RM_APPOINTMENT_SERVICE |
| spring.datasource.username | The username of Mysql Database | root |
| spring.datasource.password        | The password of Mysql Database  | password      |
| spring.jpa.hibernate.ddl-auto | Hibernate auto ddl mode | update |
| spring.jpa.database-platform      | JPA Dialect           | org.hibernate.dialect.MySQL8Dialect     |
| spring.jpa.show-sql | Whether to show sql executed by JPA | true |
| spring.cloud.gcp.project-id | Google cloud project Id | fake-project-id |
| spring.cloud.gcp.logging.enabled | Flag for enabling google cloud logging | false |
| spring.cloud.gcp.trace.enabled | Flag for enabling google cloud trace | false |
| access.token.header | Access token request header | x-access-token |
| gcp.store.projectId | GCP store projectId | odyssey-olb |
| gcp.store.projectId | GCP store bucket name | topcoder-challenge |
| video.server.name | RM video service host | |
| microsoft.graph.client.id | Microsoft graph client id | 65e05f82-b637-4ebb-ba6b-073fb30a5430 | 
| microsoft.graph.client.secret | Microsoft graph client secret | --pwxoL0XCP0~lgv~0074.Rr0q7Iiaxx_g |
| microsoft.graph.tenant | Microsoft graph tenant id | bdbde11e-8377-490d-8d17-f864b60509c9 |
| microsoft.graph.calendar.user | Microsoft graph user who owns the calendar | pvmagacho@pvmagachotc.onmicrosoft.com |
| microsoft.graph.calendar.id | Microsoft graph calendar id | AAMkADg0Mjk2OGMwLTAwYjUtNDJlZC1iNzA4LWZjZWZkN2JhNGNlMgBGAAAAAADXZ1tZ77AGTZ36u-ZxsDplBwDoMpmE5if0Qqws4YqxcBFqAAAAAAEGAADoMpmE5if0Qqws4YqxcBFqAAADA-1VAAA= |
| microsoft.graph.notification.path | Path on the appointment server to receive change notifications | /ms.graph.notification |
| microsoft.graph.notification.base.url | Base URL to receive the change notification |  |
| microsoft.graph.notification.expiration.minutes | Change notification subscription expiration in minutes | 120 |
| microsoft.graph.notification.client.state | Client state string for creating the subscription | client_state |

Environment variables:

| Environment                    | Description                                      | Required |
| ------------------------------ | ------------------------------------------------ | -------- |
| INTERNAL_SERVICE_PASSWORD      | Internal Service Password                        | Required |
| INTERNAL_SERVICE_USER          | Internal Service User                            | Required |
| AUTHENTICATION_SERVER_NAME     | Authentication server                            | Required |
| AEKM_INTERNAL_SECRET           | AEKM internal secret                             | Required |
| AEKM_SERVER_NAME               | AEKM server                                      | Required |
| AEKM_APP_ID                    | AEKM app id of the Marketing Preferences service | Required |
| MICROSOFT_GRAPH_NOTIFICATION_BASE_URL | Ms Graph Change notification base URL     | No       |


## Deploy locally

1. In a new terminal window, start ngrok to forward requests to `localhost:8103` (the appointment service's default port)

```
ngrok http 8103
```

Leave this terminal running and continue. 
We will use the displayed https forwarding url, e.g. `https://684fc172e662.ngrok.io` (this is only an example, the real URL will be different), 
as the `MICROSOFT_GRAPH_NOTIFICATION_BASE_URL` environment variable later.

2. Boot up the mysql and openvidu container by running the below command in the main directory cyn-relationship-manager-appointment-service

```bash
cd cyn-relationship-manager-appointment-service/cyn-relationship-manager-appointment-server/doc
docker-compose up -d
```

3. Connect to db using any mysql client or connect directly to docker console cli. We will need to create two databases - one for rm-video service and one for
rm-appointment service

```bash
mysql -u root -p -h 127.0.0.1
create database RM_VIDEO_SERVICE;
create database RM_APPOINTMENT_SERVICE;
```
 
4. This project depends on `cyn-commons`, they need be built and deployed at first, refer to their doc for details. Then:
Ignore: cyn-commons pre-requisites.

```bash
# Build cyn-commons
cd cyn-commons
mvn clean install

# Export below environment variables before running the service.
export INTERNAL_SERVICE_PASSWORD=cyn_service
export INTERNAL_SERVICE_USER=cyn_service
export AUTHENTICATION_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_INTERNAL_SECRET=my-secret
export AEKM_APP_ID=/app/two
export ASYNC_MESSAGE_ENCRYPTION_KEY=mykey@91mykey@91
export ID_TOKEN_ISSUER_PRIVATE_KEY="MIIEwQIBADANBgkqhkiG9w0BAQEFAASCBKswggSnAgEAAoIBAgComM/BjVG/eV7JquHl3Zp6MpnQJ9/uOpDCd6npuRLOXH/DoCMKHjBS0qQx7dkV7FkUMo/pJ97kSFQJmxmL5zeHYxvECjTc4XHH4rtAvvY0vMIqk7QFmssE+A+4U0EYJSh0nJtHFcdWXC1pY0nijhbkNghsyDrfkdUx5t5v8M58b0UgHd9DXBENSMOzvRvD8Qb8rj4ipwsa9Z0syxba+ScRUa2g8Flgb8WN813A8NK9mCVZyg/gVZUANgu/0FaludRoGdlDBC7DbYmZcbECrnS51JlrS9xJ+5WO7uHYvjabMsrtbl1PkeFrmsZD7yqzE6CitKTjTJ7uXpzIWzENIjYcXQIDAQABAoIBATMoWzIR3ZJKE7cBwRXszijyHbYJw2sYHkDD6qze2ZQAgUws1TUGwAcBn4z+7PY4BzeXuA85Z+rA/68Cfxfyiujr68rqhevYBJxwcf1NdIvAEvK0atWgiKXzV2HI8WGQNNxdgMhrFVdhFO57kDnMf6mzs46+mw6nw5cmxKwW17DuUpoEw2S9RpSxe4yfU4DLTW1NU++OU6IFK0LgBx60ELv9GY0uRba2EeFjq1Y+Tdi9EPDGG9vrSUmnRwohHWZ9tHa/M7Do0Lv0YaVihiZxFkD1fgeHWAvPRq6c9VQ8pp4h4dIWJZV6kg2A5b/CWg673lA6vJDa9Zfjqgh4QpSKaAyBAoGBDZTxh2tFvOpyoq95iFthY+Quu2rIIA+XJW4MVl2xjQj7/ULIn3ZNpmAVWI3n8s7d6cen70W6aClRZ57nbeT0O40SEdAEq5Gtf5oCWmdtPv1mHylTLsEnWM2vTlKfRjT1WhICkeb68yaPfvoNxZaGL2SC15Gqa7L4MQxstc6RfR1RAoGBDGnW3xkkAkmcOK0z8ierPBnTw4mF8qKe8mbunX14bL5KCBVYeqRjW0DZMkHeklgY4cSpIpygGl3tdp2tNWFkQxZa73vmY2MeOdAB1+TtD4BktPJ5Ng4AAB9oqzimLP9FtMcHsgbuHYOYsHSk/tTZR2ChU6UcVkMvZqkHmMP/gNtNAoGBCfyGS4g40tugRra3qednCMfzBKNTWx+bh04PjEGAF6+PeXztfzLBjaAAkghlkEdDLG49sNWYZpZa4NIaYNeELXVg3/AQPwbyh2x6Zna+AR7ZQ/hizXIAuG8kg3aE3l56OSwc76Liyn6D7F14Wtx+9cH14wOcR2xkycvU8ylcGJ7BAoGBC2dwr6U5H6DglHEKt/0Zoy1XDtzY4f/kZm2J6cXLTJvFB4gUsRc08DCzWSiKgYfN1BwIBnQ94ftInSBecBv5MGjpQfphvgzhR9uA1gbKguNBobrhAHf8KCCy2BpDEDO0pk/zEeqr9xFexT1kF3kT5C8yBtY5Ika21WmUQRRPNuJBAoGBCskITCXH3IbsZXzxd/6C74gSnvWVKfKPZKMJZ1UHiBdTA/DFuGWuTSKoGmf6NgleuT1YirKK7b64H3eUkkH2VOEbtneVOcYDnv98A4QRrfBNqBBqEappSdQruaPHa9ulduZ6S1tBNCHhBJFKgjB4r1VsPaT2jTDfW61oD3JFnIrm"

# Build cyn-relationship-manager-video-service on port 8102
cd cyn-relationship-manager-video-service
mvn clean install
cd cyn-relationship-manager-video-service/cyn-relationship-manager-video-server
mvn spring-boot:run

# Build cyn-relationship-manager-appointment-service and run on port 8103
# Open another terminal and export the same set of environment variables as above and then run:

cd cyn-relationship-manager-appointtment-service
mvn clean install
cd cyn-relationship-manager-appointtment-service/cyn-relationship-manager-appointment-server
export GOOGLE_APPLICATION_CREDENTIALS=doc/google_auth.json
# Set MICROSOFT_GRAPH_NOTIFICATION_BASE_URL to the ngrok https forwarding URL, which is random and unknown until ngrok is running.
# The service will create and renew the subscription for update and deletion of events in the configured calendar.
export MICROSOFT_GRAPH_NOTIFICATION_BASE_URL=
mvn spring-boot:run
```

Insert dummy data into appointments and users table for testing.

```
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(1,'good','Manager1',10,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(2,'very good','Manager2',9,'John_Doe','2021-02-20 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(3,'good','Manager3',7,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(4,'not so good','Manager4',5,'John_Doe','2021-02-21 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(5,'good','Manager5',7,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(6,'very good','Manager1',8,'User2','2021-02-20 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(7,'old school','Manager1',6,'User3','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(8,'not so good','Manager1',3,'User4','2021-02-21 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(9,'OK OK','Manager1',5,'User5','2021-02-23 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(10,'Average','Manager1',5,'User6','2021-02-24 15:07:16.643357');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (1, 32400, 36000, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (2, 36000, 37800, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (3, 37800, 39600, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (4, 43200, 46800, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (5, 46800, 50400, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (6, 32400, 36000, 'Manager2');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (7, 36000, 37800, 'Manager2');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (8, 37800, 43200, 'Manager2');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (1, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (2, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (3, 'some address', '2021-03-06 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (4, 'some address', '2021-03-07 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (5, 'some address', '2021-03-08 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (6, 'some address', '2021-03-09 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (7, 'some address', '2021-03-10 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (8, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'some subject', 'Other User');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (1, 1, 1);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (2, 2, 1);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (3, 1, 2);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (4, 2, 2);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (5, 1, 3);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (6, 2, 3);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (7, 1, 4);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (8, 2, 4);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (9, 1, 5);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (10, 2, 5);


INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (11, 1, 6);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (12, 2, 6);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (13, 1, 7);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (14, 2, 7);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointment_attachments` (`id`, `created_date`, `file_name`, `url`, `appointment_id`) VALUES (1, '2021-02-25 11:22:12.133237', 'dummyfile', 'dummyUrl', 8);

```

## Run checkstyle

For checking

```bash
mvn com.coveo:fmt-maven-plugin:check
```

For formatting

```bash
mvn com.coveo:fmt-maven-plugin:format
```

## About the calendar id

A new calendar named `Relationship Manager` has been created for this challenge by calling the Microsoft Graph API.

You can use the script `cyn-relationship-manager-appointment-server/doc/scripts/create-calendar.sh` to create a new calendar:

```
cyn-relationship-manager-appointment-server/doc/scripts/create-calendar.sh <name>
```

## Validation

### Webhook (subscription renew/creation)

The service will try to renew or create a change notification subscription on start up. The log will show something like:

```
2021-03-23 21:25:18.289  INFO [...] .c.e.r.a.s.s.i.MicrosoftGraphServiceImpl : No subscription found for resource /users/pvmagacho@pvmagachotc.onmicrosoft.com/calendars/AAMkADg0Mjk2OGMwLTAwYjUtNDJlZC1iNzA4LWZjZWZkN2JhNGNlMgBGAAAAAADXZ1tZ77AGTZ36u-ZxsDplBwDoMpmE5if0Qqws4YqxcBFqAAAAAAEGAADoMpmE5if0Qqws4YqxcBFqAAADA-1VAAA=/events, creating new.
2021-03-23 21:25:18.302  INFO [...] c.azure.identity.ClientSecretCredential  : Azure Identity => getToken() result for scopes [https://graph.microsoft.com/.default]: SUCCESS
...
2021-03-23 21:25:19.964  INFO [...] r.a.s.f.MicrosoftGraphNotificationFilter : Handles URL validation request: Validation: Testing client application reachability for subscription Request-Id: a569c869-d75d-4206-a9aa-cf02642cffed
2021-03-23 21:25:21.594  INFO [...] .c.e.r.a.s.s.i.MicrosoftGraphServiceImpl : Successfully created subscription 48b58769-c9b6-4aec-ab6f-339d908be00a
```

You can run `cyn-relationship-manager-appointment-server/doc/scripts/get-subscription.sh` to check the created subscriptions.

### API

Import the postman environment and collection under `doc` folder into Postman.

- Run `Authentication/RetrievePublicKey - AEKM` request to retrieve a public key.
- Run `Authentication/Login [Send OTP]` request to send otp.
- Run `Authentication/Login [ValidateOTP]` request to login user and get token.

**Note: since the response is encrypted, you can go to the Visualize/console tab of Postman to view decrypted response.**

- Run `Appointments CRUD/Create Appointment - PASS` request to create a new appointment.
- Run the script `cyn-relationship-manager-appointment-server/doc/scripts/get-events.sh` to get the event just created in Microsoft.
- Run `Appointments CRUD/Update Appointment - PASS` request to update the appointment
- Run the script `cyn-relationship-manager-appointment-server/doc/scripts/get-events.sh` again to get the updated event.
- Run `Appointments CRUD/Delete Appointment By Id - PASS` request to delete the appointment
- Run the script `cyn-relationship-manager-appointment-server/doc/scripts/get-events.sh` get the event list, which should be empty.

During the above execution, our service should receive notifications from Microsoft through ngrok, and it should ignore all of them.
You should also see a 404 not found error in the log, which happens when our service is trying to get a deleted event.

Run the rest requests in the other folders to validate the API like before.
