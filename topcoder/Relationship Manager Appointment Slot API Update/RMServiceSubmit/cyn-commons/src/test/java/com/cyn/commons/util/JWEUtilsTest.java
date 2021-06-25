package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.jwk.RSAKey;
import java.util.Base64;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class JWEUtilsTest {

  private String invalidCertificate =
      "-----BEGIN CERTIFICATE-----\n"
          + "MIICpzCCAhACAg4AMA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG\n"
          + "A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE\n"
          + "MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl\n"
          + "YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw\n"
          + "ODIyMDcyNjQzWhcNMTcwODIxMDcyNjQzWjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE\n"
          + "CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs\n"
          + "ZS5jb20wgfAwgagGByqGSM44BAEwgZwCQQDKVt7ZYtFRCzrm2/NTjl45YtMgVctQ\n"
          + "pLadAowFRydY13uhGw+JXyM+qmngfQkXImQpoYdIe+A8DWG2vaO3wKQ3AhUAxx6d\n"
          + "eaDs+XNHcbsiVQ1osvxrG8sCQHQYZDlSy/A5AFXrWXUNlTJbNhWDnitiG/95qYCe\n"
          + "FGnwYPp/WyhX+/lbDmQujkrbd4wYStudZM0cc4iDAWeOHQ0DQwACQDtK/S6POMQE\n"
          + "8aI+skBdNQn+Ch76kNDhztC/suOr9FbCSxnZ/CfhSgE1phOJyEkdR2jgErl3uh51\n"
          + "lo+7to76LLUwDQYJKoZIhvcNAQEFBQADgYEAnrmxZ3HB0LmVoFYdBJWxNBkRaFyn\n"
          + "jBmRsSJp2xvFg2nyAF77AOqBuFOFqOxg04eDxH8TGLQOWjqdyCFCY79AQlmkdB+8\n"
          + "Z5SWqPEwLJHVLd91O9avQwwRQT5TAxGXFkHTlQxOoaGfTsVQFqSDnlYC4mFjspA7\n"
          + "W+K8+llxOFmtVzU=\n"
          + "-----END CERTIFICATE-----\n";

  private String validCertificate =
      "-----BEGIN CERTIFICATE-----\n"
          + "MIIDMjCCAhoCCQCkdVzf9StZMzANBgkqhkiG9w0BAQsFADBbMQswCQYDVQQGEwJG\n"
          + "UjEPMA0GA1UECAwGRlJBTkNFMQ4wDAYDVQQHDAVMSUxMRTEXMBUGA1UECgwOR1JB\n"
          + "VklURUVTT1VSQ0UxEjAQBgNVBAMMCUxPQ0FMSE9TVDAeFw0yMDA1MDQyMTMwNTha\n"
          + "Fw0yMTA1MDQyMTMwNThaMFsxCzAJBgNVBAYTAkZSMQ8wDQYDVQQIDAZGUkFOQ0Ux\n"
          + "DjAMBgNVBAcMBUxJTExFMRcwFQYDVQQKDA5HUkFWSVRFRVNPVVJDRTESMBAGA1UE\n"
          + "AwwJTE9DQUxIT1NUMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4+iL\n"
          + "fs52Ta+7s3W/X4+E+f8SwigNhOqigF0Mjoce8mWTQGjS9U1kOuSJKnS9c4gf5vZh\n"
          + "BB8iGkufMDvl5nwB0JgcihG6QUiN1NT8WPasJLjRTYQmJNCIYBac0oSZfHIUq8C4\n"
          + "kmbFbWqXdp3uB+0l9qalD/HlckBt6lJ4VEb+ntbDpWgQnSxtRDzPZgH/kqJ3sNGn\n"
          + "Xnd5uNcG1W9v0IC+Wz1+kZ1eFM9+HqfnhGLr4yOr1sSeRQTJUaT12ixSKwn7qBJv\n"
          + "nSSUlevIao+Zje2wnvTGVAgsEIuGulw0KWH9hLnYecyTPf3YR7v9sv8ff1qpn0LO\n"
          + "mOWOfXYvXqteEcdh9wIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAxXU2rql3jOGLx\n"
          + "2MjKsRH89lC2lvo8SiQpNyzW7/XO3SIxIAjVgXixREugJX4p+bOn//jQ6R9ZQ0Jk\n"
          + "pZ9oz3ULSyPnIzqjKp7bqxc2sFR+jYIFrePkK7gCcr8BjcJHmE7Lg8PpNgYRQ/0B\n"
          + "E9JdRpac6CsxnN6zzxUduAwyfMANBHdwWLYyC+daqdvaJwGkOPXtVpjagH5Etjp8\n"
          + "M/SVukvUElYG2tuIbmkmCUC0ABFXMe2T2e9d9STReGYZ/8NdffqFbER0dOuf1cK8\n"
          + "94uiZJKU/oLvrt28ia1IPADT+SV0EELcGjGAJvJAaBLHmhg/J/GDrA1Yo8TgdOmC\n"
          + "vIgLGU+Z\n"
          + "-----END CERTIFICATE-----";

  private String privateKey =
      "-----BEGIN PRIVATE KEY-----\n"
          + "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDj6It+znZNr7uz\n"
          + "db9fj4T5/xLCKA2E6qKAXQyOhx7yZZNAaNL1TWQ65IkqdL1ziB/m9mEEHyIaS58w\n"
          + "O+XmfAHQmByKEbpBSI3U1PxY9qwkuNFNhCYk0IhgFpzShJl8chSrwLiSZsVtapd2\n"
          + "ne4H7SX2pqUP8eVyQG3qUnhURv6e1sOlaBCdLG1EPM9mAf+Sonew0aded3m41wbV\n"
          + "b2/QgL5bPX6RnV4Uz34ep+eEYuvjI6vWxJ5FBMlRpPXaLFIrCfuoEm+dJJSV68hq\n"
          + "j5mN7bCe9MZUCCwQi4a6XDQpYf2Eudh5zJM9/dhHu/2y/x9/WqmfQs6Y5Y59di9e\n"
          + "q14Rx2H3AgMBAAECggEBAMQ7OcCZLCt/n/HI9FnnTOujwBcEPX+wNvKnK2Sc2vyC\n"
          + "kOLoXptvY1mB5Mh1c5nCHxXSFX5jAA38BriVGkH1i+p6zjfjQ6dqPRR/vjcrTNTh\n"
          + "fPVtTxprosB5N9Xa7fzEjR/E9Ca57ktjd75eBNE2U3Wnzn+IG7Lf3sPe9lQ6PImt\n"
          + "aMkvCpuJ2dNeLMoEzWY+LANyKUf1h729JefUdCsy4xPN7glPBg1G4qvr6Qw/F9fi\n"
          + "Hfm78EvDQwK24X0oQFHPYIK5VH3Elnw2vPpJg4+FI+6yfqz3oNzTDOhHciXCXWx+\n"
          + "aijCoNzMwPEGYKPLd4phB1X+rFew6m/DminB+KsayeECgYEA+zZKtw85xX7Nrqjs\n"
          + "LHsRzSGylxQzgxktOByhljdb9/1S5C+kSZXALMDpuAL1HthknlE7ij3LyJRkyGlR\n"
          + "9qbw7dkK5QIypgnOkqALe01hMPSzMChM5c3lK+Kfkj6pk4UM5yduUPWuBOjTr4fu\n"
          + "RX1bNFYGnMJN8tDTSqqyGNy4BSUCgYEA6ECM2JcOO44V70A1WT043FjeuqmwGlJ6\n"
          + "4YSMUjRzO3UV6WFQmyVWM0ipmjo8zbfS3GX4F61XrUpRKYVZ5/uUR6I7+MMCPq5Q\n"
          + "0JlA9z/fy8Y+/EAQW4bPd9g+TNHt4P5xOhhUmOs/H1zE6T4n8AeoPF0BmwH1OAtM\n"
          + "0DV4YROCNesCgYB6WocpghDxUEF9wGjHgWm6L598ViqAv9J2cfuB3pS8xHWLDmlG\n"
          + "Ldnb8lnjdg1NaLgZJBeLzW1j1GMB+coiCE1wizXzNI7WsBAc+jadZ6Le5VAhNH+/\n"
          + "rfjg1xBBeqO9ZiBStbHVXNPVMFFqPRqgXd3+L6go0bqFeZBv3ZPA6D/4wQKBgCF+\n"
          + "JZGafw/xlpIxFUBwRsHoAv6yN9Yj7NqWzdGRBMkfQnCsev5UrRCqOuMl4Nzd1Ie6\n"
          + "IMPxWIKCBCBTvbppmmGWMMXYAXvekYLzht3hFToCtdSUvIcmcsbapWlYoNBggR2a\n"
          + "oqCG1EyGiVMFhqbjw8wosXGUH7PcVVB1VY3STWXPAoGBANRe4x8Ic6KhjtpdKGus\n"
          + "xuUgqjyYYDPavSnSn+fzoB7TaYvHvx90Jis6tPcnw8PO1YtWejswIeXFDTPURS47\n"
          + "Do5ROs/pXthuL5W9qwCzthrGkFbX/OW98kIwvMqviDuBjk23IBTWLqnvMhXJywlj\n"
          + "ALnA3sOH8tnBTVGPdPiVxvO6\n"
          + "-----END PRIVATE KEY-----";

  @BeforeEach
  void setUp() {
    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.setLevel(Level.OFF);
  }

  /** Tests if creation of jwe is working fine * */
  @Test
  void createJWE() throws JOSEException {

    String encodedPrivateKey = getEncodedPrivateKey();

    JWEContext jweContext =
        new JWEContext(
            "subject",
            new HashMap<>() {
              {
                put("role", "admin");
              }
            },
            encodedPrivateKey,
            validCertificate,
            validCertificate,
            200,
            "topcoder");
    JWEUtils jweUtils = new JWEUtils();
    JWEObject jweObject = jweUtils.createJWE(jweContext);
    assertNotNull(jweObject);
  }

  private String getEncodedPrivateKey() {
    return privateKey
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "")
        .replaceAll("\n", "");
  }

  /** Tests if the public key is retrieved from a valid certificate */
  @Test
  void getPublicKey() {
    JWEUtils jweUtils = new JWEUtils();
    assertNotNull(jweUtils.getPublicKey(validCertificate));
    assertNull(jweUtils.getPublicKey(invalidCertificate));
  }

  /** Tests whether a valid JWK is retrieved by passing the private key and a valid certificate */
  @Test
  void getJSONWebKey() {
    String encodedPrivateKey = getEncodedPrivateKey();

    JWEUtils jweUtils = new JWEUtils();
    RSAKey rsaKey = jweUtils.getJSONWebKey(encodedPrivateKey, validCertificate);
    assertNotNull(rsaKey);

    rsaKey =
        jweUtils.getJSONWebKey(
            Base64.getEncoder().encodeToString("invalidPem".getBytes()), invalidCertificate);
    assertNull(rsaKey);
  }
}
