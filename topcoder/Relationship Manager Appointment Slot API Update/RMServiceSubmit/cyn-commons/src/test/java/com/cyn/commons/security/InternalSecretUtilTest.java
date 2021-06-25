package com.cyn.commons.security;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.cyn.commons.exception.BaseServiceException;
import java.lang.reflect.Field;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class InternalSecretUtilTest {

  @Before
  public void setup() throws NoSuchFieldException, IllegalAccessException {
    setStaticFieldValue("encryptor", null);
    setStaticFieldValue("internalSecretKey", null);
    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.setLevel(Level.OFF);
  }

  @Test
  public void testEncryptDecrypt() throws Exception {
    setStaticFieldValue("internalSecretKey", "secret");

    String encrypted = InternalSecretUtil.encrypt("helloWorld");
    String decrypted = InternalSecretUtil.decrypt(encrypted);
    assertEquals("helloWorld", decrypted);
  }

  @Test
  public void testEncryptDecryptAppGuid() throws Exception {
    setStaticFieldValue("internalSecretKey", "secret");

    AppGuid appGuid = new AppGuid("appId", new UUID(2, 1));
    String encrypted = InternalSecretUtil.encryptAppGuid(appGuid);
    AppGuid actualAppGuid = InternalSecretUtil.decryptAppGuid(encrypted);
    assertEquals(appGuid, actualAppGuid);
  }

  @Test
  public void testEncryptDecryptAppGuidWithGuidNull() throws Exception {
    setStaticFieldValue("internalSecretKey", "secret");

    AppGuid appGuid = new AppGuid("appId", null);
    String encrypted = InternalSecretUtil.encryptAppGuid(appGuid);

    try {
      InternalSecretUtil.decryptAppGuid(encrypted);
    } catch (BaseServiceException e) {
      assertEquals("CPT_ERR000003", e.getMessage());
    }
  }

  public void setStaticFieldValue(String internalSecretKey, String secret)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = InternalSecretUtil.class.getDeclaredField(internalSecretKey);
    field.setAccessible(true);
    field.set(null, secret);
  }

  @Test
  public void testEncryptWithoutSecretKey() {
    try {
      new InternalSecretUtil();
      InternalSecretUtil.encrypt("helloWorld");
    } catch (BaseServiceException e) {
      assertEquals("CPT_ERR000001", e.getMessage());
    }
  }

  @Test
  public void testDecryptWithoutSecretKey() {
    try {
      InternalSecretUtil.decrypt("helloWorld");
    } catch (BaseServiceException e) {
      assertEquals("CPT_ERR000002", e.getMessage());
    }
  }

  @Test
  public void testEncryptAppGuidWithoutSecretKey() {
    try {
      AppGuid appGuid = new AppGuid("appId", new UUID(2, 1));
      InternalSecretUtil.encryptAppGuid(appGuid);
    } catch (BaseServiceException e) {
      assertEquals("CPT_ERR000001", e.getMessage());
    }
  }

  @Test
  public void testDecryptAppGuidWithoutSecretKey() {
    try {
      InternalSecretUtil.decryptAppGuid("helloWorld");
    } catch (BaseServiceException e) {
      assertEquals("CPT_ERR000002", e.getMessage());
    }
  }

  @Test
  public void testMain() {
    String uuid = "5fc03087-d265-11e7-b8c6-83e29cd24f4c";
    AppGuid appGuid = new AppGuid("appId", UUID.fromString(uuid));
    String[] args = new String[] {"secret", appGuid.getAppId(), uuid};
    InternalSecretUtil.main(args);
    assertNotNull(
        InternalSecretUtil.encryptAppGuid(new AppGuid(args[1], UUID.fromString(args[2]))));
  }
}
