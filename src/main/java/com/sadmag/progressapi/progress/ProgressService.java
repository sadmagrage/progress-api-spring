package com.sadmag.progressapi.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public List<Progress> findAll() {
        return progressRepository.findAll();
    }

    public Progress findLast() {
        return progressRepository.findFirstByOrderByAttemptDesc();
    }

    public Progress save(ProgressRecord progressRecord) {
        var progress = new Progress();

        progress.setTimestamp(progressRecord.timestamp());
        progress.setAttempt(progressRecord.attempt());

        progressRepository.save(progress);

        return progress;
    }

    public Progress update(ProgressRecord progressRecord, UUID id) {
        var progress = progressRepository.findById(id).orElseThrow();

        progress.setTimestamp(progressRecord.timestamp());
        progress.setAttempt(progressRecord.attempt());

        progressRepository.save(progress);

        return progress;
    }

    public void delete(UUID id) {
        var progress = progressRepository.findById(id).orElseThrow();

        progressRepository.delete(progress);
    }
}