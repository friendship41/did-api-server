package com.friendship41.apiserver.did.controller;

import com.friendship41.apiserver.did.data.DIDBlock;
import com.friendship41.apiserver.did.service.DIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/did")
public class DIDController {
  private final DIDService didService;

  @Autowired
  public DIDController(DIDService didService) {
    this.didService = didService;
  }

  @PostMapping("/block")
  public Object postDIDBlock(@RequestBody Map<String, Object> body) {
    return new ResponseEntity<>(
        this.didService.registerDIDBlock(
            (Map<String, String>) body.get("data"),
            body.get("publicKey") == null ? null : String.valueOf(body.get("publicKey"))),
        HttpStatus.OK);
  }

  @GetMapping("/blockList")
  public Object getDIDBlockList() {
    return new ResponseEntity<>(this.didService.getDIDBlockList(), HttpStatus.OK);
  }

  @GetMapping("/{did}")
  public Object getDIDBlock(@PathVariable("did") final String did) {
    try {
      return new ResponseEntity<>(this.didService.getDIDBlock(did), HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/temp")
  public Object getss() {
    return this.didService.temp();
  }
}
