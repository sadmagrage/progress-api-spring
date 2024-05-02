package com.sadmag.progressapi.authorization;

import com.sadmag.progressapi.user.User;
import com.sadmag.progressapi.user.UserRepository;
import com.sadmag.progressapi.user.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private AuthorizationService authorizationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return the user by username")
    void loadUserByUsername() {
        var username = "username";
        var user = new User(UUID.randomUUID(), username, "password", UserRole.USER);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        var result = authorizationService.loadUserByUsername(username);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(username, result.getUsername());
    }
}