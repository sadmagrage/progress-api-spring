package com.sadmag.progressapi.expiration_date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class ExpirationDateServiceTest {

    @Autowired
    @InjectMocks
    private ExpirationDateService expirationDateService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void genExpirationDate() {
        var expirationDate = expirationDateService.genExpirationDate((short) 15);
        var expectedMinute = LocalDateTime.now().plusMinutes(15).getMinute();

        Assertions.assertNotNull(expirationDate);
        Assertions.assertEquals(expectedMinute, expirationDate.atZone(ZoneOffset.of("-03:00")).getMinute());
    }
}