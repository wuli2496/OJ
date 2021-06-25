# Odyssey Relationship Manager Video Service

# Prerequisites

- Java 11 or above
- Maven 3.6 or above
- Docker
- NodeJs and NPM (for running OpenVidu browser app to validate the webhook events)
- Ngrok (https://ngrok.com/)

## Configuration

| Config                    	    | Description                                  | Default          |
| ----------------------------- | -------------------------------------------- | ---------------- |
| server.port               	    | Port to listen for incoming requests         | 8102           |
| spring.application.name   	    | Application name to be used in Spring Sleuth | cyn-relationship-manager-video-server                       |
| server.servlet.context-path   | The root path of the rest endpoint           | /v1/sales/service/customer/rm                      |
| spring.aop.auto           	    | Autoconfiguration of spring aop       | false      |
| spring.datasource.url | The Mysql Database URL | jdbc:mysql://localhost:3306/RM_VIDEO_SERVICE |
| spring.datasource.username | The username of Mysql Database | user |
| spring.datasource.password        | The password of Mysql Database  | password      |
| spring.jpa.hibernate.ddl-auto | Hibernate auto ddl mode | update |
| spring.jpa.database-platform      | JPA Dialect           | org.hibernate.dialect.MySQL8Dialect     |
| spring.jpa.show-sql | Whether to show sql executed by JPA | true |
| spring.cloud.gcp.project-id | Google cloud project Id | fake-project-id |
| spring.cloud.gcp.logging.enabled | Flag for enabling google cloud logging | false |
| spring.cloud.gcp.trace.enabled | Flag for enabling google cloud trace | false |
| access.token.header | Access token request header | x-access-token |
| appointment.server.name | RM appointment service host+port |  |
| openvidu.webhook.token | API token for OpenVidu webhook request | MY_API_TOKEN |
| openvidu.webhook.path | OpenVidu webhook path | /openvidu/webhook |
| openvidu.url | OpenVidu server URL | https://localhost:4443 |
| openvidu.secret | OpenVidu server secret | MY_SECRET |

Environment variables:

| Environment                    | Description                                      | Required |
| ------------------------------ | ------------------------------------------------ | -------- |
| INTERNAL_SERVICE_PASSWORD      | Internal Service Password                        | Required |
| INTERNAL_SERVICE_USER          | Internal Service User                            | Required |
| AUTHENTICATION_SERVER_NAME     | Authentication server                            | Required |
| AEKM_INTERNAL_SECRET           | AEKM internal secret                             | Required |
| AEKM_SERVER_NAME               | AEKM server                                      | Required |
| AEKM_APP_ID                    | AEKM app id of the Marketing Preferences service | Required |


## Deploy locally

Boot up the mysql and OpenVidu container by running the below command in the main directory `cyn-relationship-manager-video-service`

```bash
cd cyn-relationship-manager-video-service/cyn-relationship-manager-video-server/doc
docker-compose up -d
```

For appointment service: create database `RM_APPOINTMENT_SERVICE`: (Enter 'password' as password when prompted)

```bash
mysql -u root -p -h 127.0.0.1
mysql> CREATE DATABASE `RM_APPOINTMENT_SERVICE`;
Query OK, 1 row affected
mysql> exit
```
 
This project depends on `cyn-commons` and `cyn-relationship-manager-appointment-service`, they need be built and deployed at first,
refer to their doc for details. Then:
Ignore: cyn-commons pre-requisites.

# Build cyn-commons
```bash
cd cyn-commons
mvn clean install -Dmaven.test.skip=true
```

# Build and run cyn-relationship-manager-appointment-service
```bash
cd cyn-relationship-manager-appointment-service
mvn clean install
```

# Export below environment variables before running the service.
```bash
export INTERNAL_SERVICE_PASSWORD=cyn_service
export INTERNAL_SERVICE_USER=cyn_service
export AUTHENTICATION_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_INTERNAL_SECRET=my-secret
export AEKM_APP_ID=/app/two
export ASYNC_MESSAGE_ENCRYPTION_KEY=mykey@91mykey@91
export ID_TOKEN_ISSUER_PRIVATE_KEY="MIIEwQIBADANBgkqhkiG9w0BAQEFAASCBKswggSnAgEAAoIBAgComM/BjVG/eV7JquHl3Zp6MpnQJ9/uOpDCd6npuRLOXH/DoCMKHjBS0qQx7dkV7FkUMo/pJ97kSFQJmxmL5zeHYxvECjTc4XHH4rtAvvY0vMIqk7QFmssE+A+4U0EYJSh0nJtHFcdWXC1pY0nijhbkNghsyDrfkdUx5t5v8M58b0UgHd9DXBENSMOzvRvD8Qb8rj4ipwsa9Z0syxba+ScRUa2g8Flgb8WN813A8NK9mCVZyg/gVZUANgu/0FaludRoGdlDBC7DbYmZcbECrnS51JlrS9xJ+5WO7uHYvjabMsrtbl1PkeFrmsZD7yqzE6CitKTjTJ7uXpzIWzENIjYcXQIDAQABAoIBATMoWzIR3ZJKE7cBwRXszijyHbYJw2sYHkDD6qze2ZQAgUws1TUGwAcBn4z+7PY4BzeXuA85Z+rA/68Cfxfyiujr68rqhevYBJxwcf1NdIvAEvK0atWgiKXzV2HI8WGQNNxdgMhrFVdhFO57kDnMf6mzs46+mw6nw5cmxKwW17DuUpoEw2S9RpSxe4yfU4DLTW1NU++OU6IFK0LgBx60ELv9GY0uRba2EeFjq1Y+Tdi9EPDGG9vrSUmnRwohHWZ9tHa/M7Do0Lv0YaVihiZxFkD1fgeHWAvPRq6c9VQ8pp4h4dIWJZV6kg2A5b/CWg673lA6vJDa9Zfjqgh4QpSKaAyBAoGBDZTxh2tFvOpyoq95iFthY+Quu2rIIA+XJW4MVl2xjQj7/ULIn3ZNpmAVWI3n8s7d6cen70W6aClRZ57nbeT0O40SEdAEq5Gtf5oCWmdtPv1mHylTLsEnWM2vTlKfRjT1WhICkeb68yaPfvoNxZaGL2SC15Gqa7L4MQxstc6RfR1RAoGBDGnW3xkkAkmcOK0z8ierPBnTw4mF8qKe8mbunX14bL5KCBVYeqRjW0DZMkHeklgY4cSpIpygGl3tdp2tNWFkQxZa73vmY2MeOdAB1+TtD4BktPJ5Ng4AAB9oqzimLP9FtMcHsgbuHYOYsHSk/tTZR2ChU6UcVkMvZqkHmMP/gNtNAoGBCfyGS4g40tugRra3qednCMfzBKNTWx+bh04PjEGAF6+PeXztfzLBjaAAkghlkEdDLG49sNWYZpZa4NIaYNeELXVg3/AQPwbyh2x6Zna+AR7ZQ/hizXIAuG8kg3aE3l56OSwc76Liyn6D7F14Wtx+9cH14wOcR2xkycvU8ylcGJ7BAoGBC2dwr6U5H6DglHEKt/0Zoy1XDtzY4f/kZm2J6cXLTJvFB4gUsRc08DCzWSiKgYfN1BwIBnQ94ftInSBecBv5MGjpQfphvgzhR9uA1gbKguNBobrhAHf8KCCy2BpDEDO0pk/zEeqr9xFexT1kF3kT5C8yBtY5Ika21WmUQRRPNuJBAoGBCskITCXH3IbsZXzxd/6C74gSnvWVKfKPZKMJZ1UHiBdTA/DFuGWuTSKoGmf6NgleuT1YirKK7b64H3eUkkH2VOEbtneVOcYDnv98A4QRrfBNqBBqEappSdQruaPHa9ulduZ6S1tBNCHhBJFKgjB4r1VsPaT2jTDfW61oD3JFnIrm"
```

# Start ngrok
Download ngrok if needed from https://ngrok.com/ and install.
In a new terminal window, start ngrok to forward requests to `localhost:8103` (the appointment service default port)

```bash
ngrok http 8103
```

Leave this terminal running and continue. 
We will use the displayed https forwarding url, e.g. `https://684fc172e662.ngrok.io` (this is only an example, the real URL will be different).

Set MICROSOFT_GRAPH_NOTIFICATION_BASE_URL to the ngrok https forwarding URL, which is random and unknown until ngrok is running.
The service will create and renew the subscription for update and deletion of events in the configured calendar.

```bash
export MICROSOFT_GRAPH_NOTIFICATION_BASE_URL=
as the `MICROSOFT_GRAPH_NOTIFICATION_BASE_URL` environment variable later.

export GOOGLE_APPLICATION_CREDENTIALS=doc/google_auth.json
```

# Run cyn-relationship-manager-appointment-server

```bash
cd cyn-relationship-manager-appointment-service/cyn-relationship-manager-appointment-server
mvn spring-boot:run
```

Relationship server will be started at port 8103 by default

# In another terminal, build cyn-relationship-manager-video-service

```bash
cd cyn-relationship-manager-video-service
mvn clean install
```

# Export below environment variables before running the service.

```bash
export INTERNAL_SERVICE_PASSWORD=cyn_service
export INTERNAL_SERVICE_USER=cyn_service
export AUTHENTICATION_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_SERVER_NAME=services.odyssey-api-dev.com
export AEKM_INTERNAL_SECRET=my-secret
export AEKM_APP_ID=/app/two
export ASYNC_MESSAGE_ENCRYPTION_KEY=mykey@91mykey@91
export ID_TOKEN_ISSUER_PRIVATE_KEY="MIIEwQIBADANBgkqhkiG9w0BAQEFAASCBKswggSnAgEAAoIBAgComM/BjVG/eV7JquHl3Zp6MpnQJ9/uOpDCd6npuRLOXH/DoCMKHjBS0qQx7dkV7FkUMo/pJ97kSFQJmxmL5zeHYxvECjTc4XHH4rtAvvY0vMIqk7QFmssE+A+4U0EYJSh0nJtHFcdWXC1pY0nijhbkNghsyDrfkdUx5t5v8M58b0UgHd9DXBENSMOzvRvD8Qb8rj4ipwsa9Z0syxba+ScRUa2g8Flgb8WN813A8NK9mCVZyg/gVZUANgu/0FaludRoGdlDBC7DbYmZcbECrnS51JlrS9xJ+5WO7uHYvjabMsrtbl1PkeFrmsZD7yqzE6CitKTjTJ7uXpzIWzENIjYcXQIDAQABAoIBATMoWzIR3ZJKE7cBwRXszijyHbYJw2sYHkDD6qze2ZQAgUws1TUGwAcBn4z+7PY4BzeXuA85Z+rA/68Cfxfyiujr68rqhevYBJxwcf1NdIvAEvK0atWgiKXzV2HI8WGQNNxdgMhrFVdhFO57kDnMf6mzs46+mw6nw5cmxKwW17DuUpoEw2S9RpSxe4yfU4DLTW1NU++OU6IFK0LgBx60ELv9GY0uRba2EeFjq1Y+Tdi9EPDGG9vrSUmnRwohHWZ9tHa/M7Do0Lv0YaVihiZxFkD1fgeHWAvPRq6c9VQ8pp4h4dIWJZV6kg2A5b/CWg673lA6vJDa9Zfjqgh4QpSKaAyBAoGBDZTxh2tFvOpyoq95iFthY+Quu2rIIA+XJW4MVl2xjQj7/ULIn3ZNpmAVWI3n8s7d6cen70W6aClRZ57nbeT0O40SEdAEq5Gtf5oCWmdtPv1mHylTLsEnWM2vTlKfRjT1WhICkeb68yaPfvoNxZaGL2SC15Gqa7L4MQxstc6RfR1RAoGBDGnW3xkkAkmcOK0z8ierPBnTw4mF8qKe8mbunX14bL5KCBVYeqRjW0DZMkHeklgY4cSpIpygGl3tdp2tNWFkQxZa73vmY2MeOdAB1+TtD4BktPJ5Ng4AAB9oqzimLP9FtMcHsgbuHYOYsHSk/tTZR2ChU6UcVkMvZqkHmMP/gNtNAoGBCfyGS4g40tugRra3qednCMfzBKNTWx+bh04PjEGAF6+PeXztfzLBjaAAkghlkEdDLG49sNWYZpZa4NIaYNeELXVg3/AQPwbyh2x6Zna+AR7ZQ/hizXIAuG8kg3aE3l56OSwc76Liyn6D7F14Wtx+9cH14wOcR2xkycvU8ylcGJ7BAoGBC2dwr6U5H6DglHEKt/0Zoy1XDtzY4f/kZm2J6cXLTJvFB4gUsRc08DCzWSiKgYfN1BwIBnQ94ftInSBecBv5MGjpQfphvgzhR9uA1gbKguNBobrhAHf8KCCy2BpDEDO0pk/zEeqr9xFexT1kF3kT5C8yBtY5Ika21WmUQRRPNuJBAoGBCskITCXH3IbsZXzxd/6C74gSnvWVKfKPZKMJZ1UHiBdTA/DFuGWuTSKoGmf6NgleuT1YirKK7b64H3eUkkH2VOEbtneVOcYDnv98A4QRrfBNqBBqEappSdQruaPHa9ulduZ6S1tBNCHhBJFKgjB4r1VsPaT2jTDfW61oD3JFnIrm"
```

# Run cyn-relationship-manager-video-server

```bash
cd cyn-relationship-manager-video-service/cyn-relationship-manager-video-server
mvn spring-boot:run
```

The server will be started at port 8102 by default


Insert dummy data into database for testing.

```
INSERT INTO RM_APPOINTMENT_SERVICE.appointments (id, manager_name, subject, description, appointment_date, user_name, mode, status,address, cancelation_reason)
VALUES (1, 'Manager1', 'Appointment subject', 'Appt description', now(), 'Jane_Dee', 'audio', 'pending', 'add', 'reason');

INSERT INTO RM_APPOINTMENT_SERVICE.appointments (id, manager_name, subject, description, appointment_date, user_name, mode, status,address, cancelation_reason)
VALUES (2, 'Manager1', 'Appointment subject', 'Appt description', now(), 'John_Doe', 'video', 'pending', 'add', 'reason');

INSERT INTO RM_APPOINTMENT_SERVICE.appointments (id, manager_name, subject, description, appointment_date, user_name, mode, status,address, cancelation_reason)
VALUES (3, 'Manager1', 'Old appointment', 'Past appt should fail session creation', '2020-01-23 12:45:56', 'John_Doe', 'video', 'confirmed', 'add','reason');

INSERT INTO RM_APPOINTMENT_SERVICE.appointments (id, manager_name, subject, description, appointment_date, user_name, mode, status,address, cancelation_reason)
VALUES (4, 'Manager1', 'Audio Appointment subject', 'Audio Appt description', now(), 'John_Doe', 'audio', 'confirmed', 'add', 'reason');

INSERT INTO RM_APPOINTMENT_SERVICE.appointments (id, manager_name, subject, description, appointment_date, user_name, mode, status,address, cancelation_reason)
VALUES (5, 'Manager1', 'Valid Appointment subject', 'Valid Appt description', now(), 'John_Doe', 'video', 'confirmed', 'add', 'reason');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (6, 'some address', now(), NULL, 'desc', 'John_Doe', 'Video', NULL, 'Pending', 'KT', 'test01');

INSERT INTO RM_VIDEO_SERVICE.sessions (id, appointments_id, manager_name, max_guests, session_id, user_name)
VALUES (1, 6, 'John_Doe', 6, '634608f3-8a79-422c-ab5a-780ec3befbf8', 'test01');

INSERT INTO RM_VIDEO_SERVICE.sessions (id, appointments_id, manager_name, max_guests, session_id, user_name)
VALUES (2, 6, 'John_Doe', 6, '634618f3-8a79-422c-ab5a-780ec3befbf8', 'test01');

INSERT INTO RM_VIDEO_SERVICE.guests (id, expiration_time, guest_id, status, token, session_id)
VALUES (1, NULL, 'ec9e9936-ea47-4297-81b8-363262f18d1b', 'Pending', NULL, 1);

INSERT INTO RM_VIDEO_SERVICE.guests (id, expiration_time, guest_id, status, token, session_id)
VALUES (2, NULL, 'ec9e9936-eb47-4297-81b8-363262f18d1b', 'Pending', NULL, 1);

INSERT INTO RM_VIDEO_SERVICE.guests (id, expiration_time, guest_id, status, token, session_id)
VALUES (3, '2021-06-07 11:28:05.247586', 'ec9e9936-ec47-4297-81b8-363262f18d1b', 'Pending', 'MSlpxFzgwEbYp1v7rf6COy6Yw_Q', 1);

INSERT INTO RM_VIDEO_SERVICE.guests (id, expiration_time, guest_id, status, token, session_id)
VALUES (4, '2021-06-07 10:28:05.247586', 'ea8e9936-ec47-4297-81b8-363262f18d1b', 'Pending', 'MSlpyFzgwEbYp1v7rf6COy6Yw_Q', 1);
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

## Validation

# Guest service

Insert above data into database before start testing.

Use 'RM Video Guest Server' collection and 'GCP DEV HTTP' environment from 'cyn-relationship-manager-video-server/doc' folder.

Use methods from auth folder for different login cases.

To start session by guest execute methods from 'start session chain' folder in the following order:
1. Create guest
2. Generate token
3. Accept guest
4. Start video session

To reach max number of guests repeat 'start session chain' cycle 7 times.
To see different (un)successful cases use other tests.

Expand postman comments for hints about test cases (https://postimg.cc/jwKZ8mhZ).
Use postman visualize tab to see encrypted messages (https://postimg.cc/fVwNSzpR).


# Video service

Use the postman collection from `service-postman` repository.

**Note: since the response is encrypted, you can go to the Visualize/console tab of Postman to view decrypted response.**

- Run `Create Video session - Old Appointment date` request to create a db session for past appointments
- Run `Create Video session - Another User` request to create a db session for the appointment owned by another user (should fail).
- Run `Create Video session - Invalid Mode` request to create a db session with an invalid appointment mode 
- Run `Create Video session - UnConfirmed Status` request to create a db session with status not confirmed
- Run `Create Video session - Invalid appointment Id` request to create a db session for invalid appointment
- Run `Create Video session` request to create a db session.
- Run `Stream Video session - Old Appointment date` request to start a video session for a post appointment (should fail).
- Run `Stream Video session - Invalid Mode` request to start a video session for an invalid appointment mode (should fail).
- Run `Stream Video session - Unconfirmed Status` request to start a video session with status not confirmed (should fail).
- Run `Stream Video session - Invalid appointment id` request to start a video session for invalid appointment id (should fail).
- Run `Stream Video Session` request to get start video session token.
- Run `Get Video Session` request to get session id for appointment id.
- Run `Get Video Session Not Found` request to get session id for appointment id that does not exist.
- Run `Leave Session` request to destroy the session in the background.

Validating negative cases

- Run `Leave Session` request to destroy the session in the background. Run the same endpoint repeatedly to show session
doesn't exist error.

Openvidu Webhook

- Run `Openvidu Webhook/Session Destroyed` request to simulate a session destroyed event to the video service.
- Run `Openvidu Webhook/Unauthorized` request to see the webhook respond 401 for requests without authorization header.
- Run `Openvidu Webhook/Method not allwed` request to see the webhook respond 405 for requests with method other than `POST`
- Run `Openvidu Webhook/Session Created` request to simulate a session created event to the video service.

To validate the actual webhook events from OpenVidu, run the `openvidu-hello-world` web app from `openvidu-tutorials` (Requires NodeJs and NPM)

```
git clone https://github.com/OpenVidu/openvidu-tutorials.git
cd openvidu-tutorials
npm install -g http-server
http-server openvidu-hello-world/web
```

When the http server starts, goto `http://localhost:8080`, click the `JOIN` button, and after a while click `LEAVE`.

Note : you may need to "accept the certificate" by following the browser prompt.

From the log output of the RM video service, you should see two `OpenVidu event...` messages:

```
2021-03-21 11:51:37.732 DEBUG [cyn-relationship-manager-video-server,,,] 3410 --- [nio-8102-exec-9] c.c.c.e.r.v.s.f.OpenviduWebhookFilter    : OpenVidu event {"sessionId":"SessionA","timestamp":1616287897437,"event":"sessionCreated"}
2021-03-21 11:51:46.240 DEBUG [cyn-relationship-manager-video-server,,,] 3410 --- [io-8102-exec-10] c.c.c.e.r.v.s.f.OpenviduWebhookFilter    : OpenVidu event {"sessionId":"SessionA","timestamp":1616287906235,"startTime":1616287897437,"duration":8,"reason":"lastParticipantLeft","event":"sessionDestroyed"}
2021-03-21 11:51:46.240  INFO [cyn-relationship-manager-video-server,,,] 3410 --- [io-8102-exec-10] c.c.c.e.r.v.s.f.OpenviduWebhookFilter    : Session destroyed event received: SessionA
```