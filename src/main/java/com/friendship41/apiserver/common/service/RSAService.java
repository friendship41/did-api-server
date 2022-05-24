package com.friendship41.apiserver.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

@Service
@Slf4j
public class RSAService {
  private final KeyPair keypair;
  private final Cipher cipher;

  public RSAService() throws NoSuchAlgorithmException, NoSuchPaddingException {
    this.keypair = this.createKeypair();
    this.cipher = Cipher.getInstance("RSA");
  }

  public KeyPair getKeypair() {
    return this.keypair;
  }

  // Key로 RSA 암호화를 수행
  public byte[] encryptText(Key key, String encryptText) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(encryptText.getBytes());
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      log.error("fail to encryptText", e);
      return null;
    }
  }

  // Key로 RSA 복호화를 수행
  public String decryptText(Key key, byte[] ecryptByteArr) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(ecryptByteArr));
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      log.error("fail to decryptText", e);
      return null;
    }
  }

  public String getPublicKeyBase64() {
    return Base64.getEncoder().encodeToString(this.keypair.getPublic().getEncoded());
  }

  public String getPrivateKeyBase64() {
    return Base64.getEncoder().encodeToString(this.keypair.getPrivate().getEncoded());
  }


  private KeyPair createKeypair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024);
    return keyPairGenerator.generateKeyPair();
  }
}
