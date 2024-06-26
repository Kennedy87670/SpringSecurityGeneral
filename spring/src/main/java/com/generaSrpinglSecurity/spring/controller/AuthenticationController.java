package com.generaSrpinglSecurity.spring.controller;

import com.generaSrpinglSecurity.spring.ExceptionHandler.UserNameExistException;
import com.generaSrpinglSecurity.spring.config.AuthenticationResponse;
import com.generaSrpinglSecurity.spring.config.AuthenticationService;
import com.generaSrpinglSecurity.spring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

private final AuthenticationService authenticationService;


@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
) throws UserNameExistException {
    return ResponseEntity.ok(authenticationService.register(request));
}


@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
){
    return ResponseEntity.ok(authenticationService.authenticate(request));
}
}
