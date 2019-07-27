package org.why.studio.gateway.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.why.studio.gateway.dto.TokenRequest;
import org.why.studio.gateway.services.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "api/oauth/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAccessToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.getAccessToken(tokenRequest.getClientId(), tokenRequest.getClientSecret(),
                tokenRequest.getEmail(), tokenRequest.getPassword()));
    }

}
