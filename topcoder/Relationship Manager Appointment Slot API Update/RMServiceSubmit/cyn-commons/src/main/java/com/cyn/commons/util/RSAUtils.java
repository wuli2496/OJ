package com.cyn.commons.util;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.springframework.stereotype.Service;

/** Utility class for RSA Encryption */
@Service
public class RSAUtils {

  // Algorithm name
  public static final String RSA_ALGORIRHM = "RSA";
  // Async key size.
  protected static final int KEYSIZE = 2048;
  // The generator with a specified key length and algorithm
  private final KeyPairGenerator keyGen;
  // The cipher to be used for cryptographic algorithms
  private final Cipher cipher;

  /**
   * Constructor for RSA Utils
   *
   * @throws NoSuchAlgorithmException if RSA algorithm cannot be found from providers
   * @throws NoSuchPaddingException if the padding does not exist
   */
  public RSAUtils() throws NoSuchAlgorithmException, NoSuchPaddingException {
    this.keyGen = KeyPairGenerator.getInstance(RSA_ALGORIRHM);
    this.keyGen.initialize(KEYSIZE);
    this.cipher = Cipher.getInstance(RSA_ALGORIRHM);
  }

  /**
   * RSA Key Generator
   *
   * @return RSA Key Pair
   */
  public KeyPair generateKeyPair() {
    return this.keyGen.generateKeyPair();
  }

  /**
   * The RSA encryptor as a helper method
   *
   * @param input data to be encrypted
   * @param key RSA Public Key to be used
   * @return encrypted data
   * @throws GeneralSecurityException
   */
  public byte[] encrypt(byte[] input, PublicKey key) throws GeneralSecurityException {
    this.cipher.init(Cipher.ENCRYPT_MODE, key);
    return this.cipher.doFinal(input);
  }

  /**
   * The RSA decrypt helper method
   *
   * @param input data to be decrypted
   * @param key RSA Private Key to be used
   * @return plain data as byte array
   * @throws GeneralSecurityException
   */
  public byte[] decrypt(byte[] input, PrivateKey key) throws GeneralSecurityException {
    this.cipher.init(Cipher.DECRYPT_MODE, key);
    return this.cipher.doFinal(input);
  }
}
