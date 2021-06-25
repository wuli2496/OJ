package com.cyn.commons.util;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Utility class to create encrypted tokens */
@Slf4j
@Component
public class JWEUtils {

  /** Type of token content * */
  private static final String CONTENT_TYPE = "JWT";

  /**
   * Generate a JWE token based on configuration
   *
   * @param jweContext the context containing all the information
   * @return JWE token
   * @throws JOSEException
   */
  public JWEObject createJWE(JWEContext jweContext) throws JOSEException {
    RSAKey serverJWK =
        getJSONWebKey(jweContext.getEncodedPrivateKey(), jweContext.getPublicKeyCertificate());

    JWSHeader jwtHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(serverJWK).build();
    Calendar now = Calendar.getInstance();
    Date issueTime = now.getTime();
    now.add(Calendar.MINUTE, jweContext.getTokenLifeInMinutes());
    Date expiryTime = now.getTime();
    String jti = String.valueOf(issueTime.getTime());

    JWTClaimsSet.Builder builder =
        new JWTClaimsSet.Builder()
            .issuer(jweContext.getIssuer())
            .subject(jweContext.getSubject())
            .issueTime(issueTime)
            .expirationTime(expiryTime);
    jweContext.getClaims().entrySet().stream()
        .forEach(claim -> builder.claim(claim.getKey(), claim.getValue()));
    JWTClaimsSet jwtClaims = builder.jwtID(jti).build();

    SignedJWT jws = new SignedJWT(jwtHeader, jwtClaims);
    RSASSASigner signer = new RSASSASigner(serverJWK);
    jws.sign(signer);

    JWEHeader jweHeader =
        new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
            .contentType(CONTENT_TYPE)
            .build();

    JWEObject jwe = new JWEObject(jweHeader, new Payload(jws));

    RSAKey clientPublicKey = getPublicKey(jweContext.getProviderCertificate());
    jwe.encrypt(new RSAEncrypter(clientPublicKey));
    return jwe;
  }

  /**
   * Create a Java public ket from x509 formatted String
   *
   * @param content x509 public key
   * @return RSA Key for JWT library
   */
  public RSAKey getPublicKey(String content) {
    RSAKey publicKey = null;
    try {
      X509Certificate certificate = X509CertUtils.parse(content);
      publicKey = RSAKey.parse(certificate);
    } catch (JOSEException e) {
      log.error("Something went wrong", e);
    }
    return publicKey;
  }

  /**
   * Generate a Json web key from a key pair
   *
   * @param encodedPrivateKey private key
   * @param publicKeyCertificate public key as x509
   * @return RSA key structure
   */
  public RSAKey getJSONWebKey(String encodedPrivateKey, String publicKeyCertificate) {
    RSAKey jwk = null;
    try {
      byte[] decoded = Base64.getDecoder().decode(encodedPrivateKey);

      // PKCS8 decode the decoded RSA private key
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = kf.generatePrivate(keySpec);

      jwk = new RSAKey.Builder(getPublicKey(publicKeyCertificate)).privateKey(privateKey).build();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      log.error("Something went wrong", ex);
    }
    return jwk;
  }
}
