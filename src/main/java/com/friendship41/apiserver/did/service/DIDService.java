package com.friendship41.apiserver.did.service;

import com.friendship41.apiserver.common.service.RSAService;
import com.friendship41.apiserver.did.data.DIDBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DIDService {
  private final List<DIDBlock> didBlockList = new ArrayList<>();

  private final RSAService rsaService;
  private final BlockService blockService;

  @Autowired
  public DIDService(RSAService rsaService, BlockService blockService) {
    this.rsaService = rsaService;
    this.blockService = blockService;
  }


  public Map<String, Object> registerDIDBlock(final Map<String, String> data, final String publicKey) {
    String didId = this.createDIDId();
    if (this.didBlockList.isEmpty()) {
      this.didBlockList.add(this.createDIDBlock(
          "0",
          didId,
          this.createAuthentication(didId),
          data,
          publicKey));
      return Map.of("did", didId);
    }

    this.didBlockList.add(this.createDIDBlock(
        this.didBlockList.get(this.didBlockList.size() - 1).getHash(),
        didId,
        this.createAuthentication(didId),
        data,
        publicKey));
    return Map.of("did", didId);
  }

  public List<DIDBlock> getDIDBlockList() {
    return this.didBlockList;
  }

  public DIDBlock getDIDBlock(final String did) throws RuntimeException {
    return this.didBlockList.stream()
        .filter(didBlock -> didBlock.getId().equals(did))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("fail to find did: " + did));
  }

  private String createDIDId() {
    return "did:apartment:" + UUID.randomUUID().toString().replace("-", "");
  }

  private DIDBlock.Authentication createAuthentication(final String didId) {
    return DIDBlock.Authentication.builder()
        .id(didId + "#" + UUID.randomUUID().toString().replace("-", "").substring(0, 10))
        .type("RSA")
        .controller(didId)
        .publicKeyMultibase(rsaService.getPublicKeyBase64())
        .build();
  }

  private DIDBlock createDIDBlock(final String prevHash, final String didId, final DIDBlock.Authentication authentication,
                                  final Map<String, String> encryptedUserData, final String publicKey) {
    return DIDBlock.builder()
        .prevHash(prevHash)
        .hash(this.blockService.calcDIDBlockHash(prevHash, encryptedUserData))
        .timestamp(new Date().getTime())
        .id(didId)
        .authenticationList(List.of(authentication))
        .data(encryptedUserData)
        .publicKey(publicKey)
        .build();
  }

  public Object temp() {
    return Map.of(
        "pub", this.rsaService.getPublicKeyBase64(),
        "prv", this.rsaService.getPrivateKeyBase64());
  }
}
