package com.friendship41.apiserver.did.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlockService {
  public String calcDIDBlockHash(final String prevHash, final Map<String, String> data) {
    return this.applySha256(
        prevHash +
        data.entrySet().stream()
            .map(stringStringEntry -> stringStringEntry.getKey() + stringStringEntry.getValue())
            .collect(Collectors.joining()));
  }

  private String applySha256(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1)
          hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
