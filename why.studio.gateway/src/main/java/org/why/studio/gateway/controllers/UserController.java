package org.why.studio.gateway.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "api/user/create/init", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> initialCreateUser(@RequestBody String email) {
        userService.initialCreate(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "api/user/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

}
