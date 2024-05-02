package com.sadmag.progressapi.authentication;

import com.sadmag.progressapi.user.LoginRecord;
import com.sadmag.progressapi.user.RegisterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRecord loginRecord) {
        var token = authenticationService.login(loginRecord);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRecord registerRecord) {
        authenticationService.register(registerRecord);

        return ResponseEntity.created(null).build();
    }
}