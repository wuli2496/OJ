package com.cyn.commons.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

/** Utility to encrypt and decrypt with a given key using AES */
@Component
public class AESUtils {

  // AES Algorithm Name.
  private static final String AES = "AES";

  // AES Algorithm Name with pkcs5 padding.
  private static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/pkcs5padding";

  // AES Algorithm provider.
  private static final String AES_PROVIDER = "SunJCE";

  // Initialization vector for protect against subsequent modification.
  private static final String IV = "AODVNUASDNVVAOVF";

  /**
   * The AES encryption with standard configurations
   *
   * @param dataToEncrypt the data as String to be encrypted
   * @param key the symmetric key to be used for encryption
   * @return encrypted data
   * @throws NoSuchPaddingException if padding does not exist
   * @throws NoSuchAlgorithmException if algorithm does not exist
   * @throws InvalidKeyException if key is invalid
   * @throws BadPaddingException if given padding is not correct
   * @throws IllegalBlockSizeException block size of cipher not correct
   */
  public static final String encryptWithKey(String dataToEncrypt, String key)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          BadPaddingException, IllegalBlockSizeException {
    // Create key and cipher
    Key aesKey = new SecretKeySpec(key.getBytes(), AES);
    Cipher cipher = Cipher.getInstance(AES);
    // encrypt the text
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(dataToEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  /**
   * AES decryption with the default algorithm setup
   *
   * @param dataToDecrypt the data as String to be decrypted
   * @param key a symmetric key to be used for decryption
   * @return plain data
   * @throws NoSuchPaddingException if padding does not exist
   * @throws NoSuchAlgorithmException if algorithm does not exist
   * @throws InvalidKeyException if key is invalid
   * @throws BadPaddingException if given padding is not correct
   * @throws IllegalBlockSizeException block size of cipher not correct
   */
  public static final String decryptWithKey(String dataToDecrypt, String key)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          BadPaddingException, IllegalBlockSizeException {
    // Create key and cipher
    Key aesKey = new SecretKeySpec(key.getBytes(), AES);
    Cipher cipher = Cipher.getInstance(AES);
    // decrypt the text
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    return new String(cipher.doFinal(Base64.getDecoder().decode(dataToDecrypt)));
  }

  /**
   * AES decryption with the PKCS5 and initialization vector enabled
   *
   * @param encrypted the data as byte array to be decrypted
   * @param encryptionKey a symmetric key to be used for decryption
   * @return
   * @throws InvalidKeyException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws UnsupportedEncodingException
   * @throws InvalidAlgorithmParameterException
   * @throws NoSuchProviderException
   */
  public byte[] decryptPKCS5(byte[] encrypted, byte[] encryptionKey)
      throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
          BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
          NoSuchProviderException {
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING, AES_PROVIDER);
    SecretKeySpec key = new SecretKeySpec(encryptionKey, AES);
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
    return cipher.doFinal(encrypted);
  }

  /**
   * AES decryption with the PKCS5 and initialization vector enabled.
   *
   * @param encrypted encrypted data as input stream to be decrypted.
   * @param encryptionKey symmetric decryption key.
   * @return decrypted data as input stream.
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchProviderException
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeyException
   */
  public InputStream decryptPKCS5(InputStream encrypted, byte[] encryptionKey)
      throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException,
          InvalidAlgorithmParameterException, InvalidKeyException {
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING, AES_PROVIDER);
    SecretKeySpec key = new SecretKeySpec(encryptionKey, AES);
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
    return new CipherInputStream(encrypted, cipher);
  }

  /**
   * AES encryption with the PKCS5 and initialization vector enabled
   *
   * @param value the data as byte array to be encryption
   * @param symmetricKey a symmetric key to be used for encryption
   * @return encrypted data as byte array.
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   * @throws UnsupportedEncodingException
   * @throws NoSuchProviderException
   * @throws InvalidAlgorithmParameterException
   */
  public byte[] encryptPKCS5(byte[] value, byte[] symmetricKey)
      throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException,
          NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException,
          InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING, AES_PROVIDER);
    SecretKeySpec key = new SecretKeySpec(symmetricKey, AES);
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
    return cipher.doFinal(value);
  }

  /**
   * AES encryption with the PKCS5 and initialization vector enabled.
   *
   * @param from the data as input stream to be encrypted
   * @param symmetricKey a symmetric key to be used for encryption
   * @return encrypted data as input stream.
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   * @throws NoSuchProviderException
   * @throws InvalidAlgorithmParameterException
   */
  public InputStream encryptPKCS5(InputStream from, byte[] symmetricKey)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          NoSuchProviderException, InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING, AES_PROVIDER);
    SecretKey key = new SecretKeySpec(symmetricKey, AES);
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
    return new CipherInputStream(from, cipher);
  }
}
