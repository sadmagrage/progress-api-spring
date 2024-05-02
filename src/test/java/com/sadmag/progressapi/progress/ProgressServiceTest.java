package com.sadmag.progressapi.progress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProgressServiceTest {

    @Autowired
    @InjectMocks
    private ProgressService progressService;

    @Mock
    private ProgressRepository progressRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all progress")
    void findAll() {
        var progress = new Progress(UUID.randomUUID(), (short) 1, 100000);
        Mockito.when(progressRepository.findAll()).thenReturn(List.of(progress));

        var result = progressService.findAll();

        Mockito.verify(progressRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(progress, result.get(0));
    }

    @Test
    @DisplayName("Should return the progress with the highest attempt")
    void findLast() {
        var progress = new Progress(UUID.randomUUID(), (short) 1, 100000);
        Mockito.when(progressRepository.findFirstByOrderByAttemptDesc()).thenReturn(progress);

        var result = progressService.findLast();

        Mockito.verify(progressRepository, Mockito.times(1)).findFirstByOrderByAttemptDesc();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(progress, result);
    }

    @Test
    @DisplayName("Should save a progress")
    void save() {
        var progress = new Progress();
        Mockito.when(progressRepository.save(Mockito.any(Progress.class))).thenReturn(progress);

        var progressRecord = new ProgressRecord((short) 1, 10000);

        progress.setAttempt(progressRecord.attempt());
        progress.setTimestamp(progressRecord.timestamp());

        var result = progressService.save(progressRecord);

        Mockito.verify(progressRepository, Mockito.times(1)).save(progress);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(progress.getAttempt(), result.getAttempt());
        Assertions.assertEquals(progress.getTimestamp(), result.getTimestamp());
    }

    @Test
    @DisplayName("Should update a progress")
    void update() {
        var progress = new Progress();
        var uuid = UUID.randomUUID();

        Mockito.when(progressRepository.findById(uuid)).thenReturn(Optional.of(progress));

        var progressRecord = new ProgressRecord((short) 1, 10000);

        progress.setAttempt(progressRecord.attempt());
        progress.setTimestamp(progressRecord.timestamp());

        var result = progressService.update(progressRecord, uuid);

        Mockito.verify(progressRepository, Mockito.times(1)).findById(uuid);
        Mockito.verify(progressRepository, Mockito.times(1)).save(progress);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(progress.getAttempt(), result.getAttempt());
        Assertions.assertEquals(progress.getTimestamp(), result.getTimestamp());
    }

    @Test
    @DisplayName("Should delete a progress")
    void delete() {
        var uuid = UUID.randomUUID();
        var progress = new Progress();

        Mockito.when(progressRepository.findById(uuid)).thenReturn(Optional.of(progress));

        progressService.delete(uuid);

        Mockito.verify(progressRepository, Mockito.times(1)).delete(progress);
    }
}