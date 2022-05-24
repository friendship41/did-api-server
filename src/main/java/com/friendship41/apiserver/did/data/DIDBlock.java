package com.friendship41.apiserver.did.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DIDBlock {
  private String hash;
  private String prevHash;
  private long timestamp;

  // DID id
  private String id;
  private List<Authentication> authenticationList;

  // 사용자 정보 -> (정보명):(Hash된 값)
  private Map<String, String> data;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String publicKey;

  @Data
  @Builder
  static public class Authentication {
    private String id;
    private String type;
    private String controller;
    private String publicKeyMultibase;
  }
}
