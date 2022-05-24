package com.friendship41.apiserver.auth.controller;

import com.friendship41.apiserver.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping
  public Object authenticateUser(@RequestBody Map<String, Object> userInfo) {
    // 인증부분 미구현으로 항상 true
    if (!this.authService.isAuthenticationSuccess()) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return this.authService.encryptUserInfoWithPrivateKey(userInfo);
  }
}
