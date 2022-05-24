package com.friendship41.apiserver.auth.service;

import com.friendship41.apiserver.common.service.RSAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
  private final RSAService rsaService;

  @Autowired
  public AuthService(RSAService rsaService) {
    this.rsaService = rsaService;
  }

  public boolean isAuthenticationSuccess() {
    return true;
  }

  public Map<String, Object> encryptUserInfoWithPrivateKey(final Map<String, Object> userInfo) {
    return this.encryptUserInfo(rsaService.getKeypair().getPrivate(), userInfo);
  }

  public Map<String, Object> encryptUserInfo(final Key key, final Map<String, Object> userInfo) {
    return userInfo.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> rsaService.encryptText(key, String.valueOf(entry.getValue())),
            (x, y) -> y,
            LinkedHashMap::new));
  }
}
