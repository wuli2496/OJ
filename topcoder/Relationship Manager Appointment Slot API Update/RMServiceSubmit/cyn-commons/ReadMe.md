# Odyssey Online and Mobile Banking Common Library

## Build Locally
Run maven command in the project

```bash
mvn clean install
```

## Using secret mounts

- An applicaiton startup listener tailored for Spring Boot is part of this commons module. The startup listener will list all the files under `/etc/secrets` folder and use file name as key, file content as value for spring properties. So, you can mount a kubernetes secret to `/etc/secrets` folder with same file name as property name, all services will automatically use the property. For example, if you have a property for otp service with key `otp.encryption.key` and value `qwertyu`, mount as below into a kubernetes POD. The service will read this file and populate `otp.encryption.key` into Spring applicaiton context so that application can start 

```bash
# ls /etc/secrets
otp.encryption.key
# cat /etc/secrets/otp.encryption.key
qwertyu
```

## Using CynBodyAuthorization annotation.

CynBodyAuthorization will provide request body based authorization for controller methods. It will check if customerUniqueId in request body matches with customerUniqueid belongs to access token.

```java
  /**
   * Endpoint for evaluating an otp code.
   *
   * @param requestData structure containing otp to be validated.
   * @return success response.
   */
  @CynBodyAuthorization(customerUniqueIdPath = "customerUniqueIdentifier")
  @PayloadDecryption
  @PostMapping("/evaluate")
  public GenericResponse<OtpEvaluateSuccess> validateOtp(
      @Valid @RequestBody final OtpEvaluate requestData) {
    return new GenericResponse<>(otpService.evaluate(requestData));
  }
```

You should provide the location of the customerUniqueIdentifier within request body via `customerUniqueIdPath` property. It will skip this check for pre-configured roles.

You can control list of skipped roles via `roles.bypass.cui.check` property. The defailt value is `ROLE_Application/cyn-authentication`. You can provide comma-separated roles by following the same syntax like ROLE_<role_name_in_wso>

## Run the checkstyle

For checking

```bash
mvn com.coveo:fmt-maven-plugin:check
```

For formatting

```bash
mvn com.coveo:fmt-maven-plugin:format
```

## Run the tests and generate coverage report

```bash
mvn clean test
```

or

```bash
mvn test
```

This runs all the tests and generates the coverage in `cyn-commons-master/target/site/jacoco/index.html`
