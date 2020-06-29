package com.swapp.application.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users")
@CrossOrigin
public interface UserApi {

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticateUser(@RequestBody User userToAuthenticate);

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user);

    @PostMapping("/refresh")
    ResponseEntity<?> refreshToken(@RequestBody String refreshToken);
}
