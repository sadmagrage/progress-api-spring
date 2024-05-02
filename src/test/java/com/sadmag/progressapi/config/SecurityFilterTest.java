package com.sadmag.progressapi.config;

import com.sadmag.progressapi.token.TokenService;
import com.sadmag.progressapi.user.User;
import com.sadmag.progressapi.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private SecurityFilter securityFilter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should verify JWT authentication")
    void doFilterInternal() throws ServletException, IOException {
        var token = "token";
        var username = "username";
        UserDetails user = new User();
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        Mockito.when(tokenService.recoverToken(request)).thenReturn(token);
        Mockito.when(tokenService.validateToken(token)).thenReturn(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        securityFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(tokenService, Mockito.times(1)).recoverToken(request);
        Mockito.verify(tokenService, Mockito.times(1)).validateToken(token);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Assertions.assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}