package com.sadmag.progressapi.token;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

class TokenServiceTest {

    @Autowired
    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should receive a request and return the token")
    void recoverToken() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        var token = "token";

        Mockito.when(request.getHeader("Authorization")).thenReturn(token);

        var result = tokenService.recoverToken(request);

        Mockito.verify(request, Mockito.times(1)).getHeader("Authorization");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(token, result);
    }
}