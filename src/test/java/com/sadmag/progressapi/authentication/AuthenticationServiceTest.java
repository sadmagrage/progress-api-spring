package com.sadmag.progressapi.authentication;

import com.sadmag.progressapi.token.TokenRecord;
import com.sadmag.progressapi.token.TokenService;
import com.sadmag.progressapi.user.LoginRecord;
import com.sadmag.progressapi.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Autowired
    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should authenticate and return a TokenRecord object with the token")
    void login() {
        var loginRecord = new LoginRecord("user", "pass");
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRecord.username(), loginRecord.password());
        var auth = Mockito.mock(Authentication.class);
        var tokenRecord = new TokenRecord("token");

        Mockito.when(authenticationManager.authenticate(usernamePassword)).thenReturn(auth);
        Mockito.when(tokenService.generateToken((User) auth.getPrincipal())).thenReturn(tokenRecord);

        var result = authenticationService.login(loginRecord);

        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(usernamePassword);
        Mockito.verify(tokenService, Mockito.times(1)).generateToken((User) auth.getPrincipal());
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(TokenRecord.class, result);
        Assertions.assertEquals(tokenRecord, result);
    }
}