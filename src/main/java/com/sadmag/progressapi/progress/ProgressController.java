package com.sadmag.progressapi.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(progressService.findAll());
    }

    @GetMapping(path = "/last")
    public ResponseEntity<Object> findLast() {
        return ResponseEntity.ok().body(progressService.findLast());
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ProgressRecord progressRecord) {
        return ResponseEntity.created(null).body(progressService.save(progressRecord));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody ProgressRecord progressRecord, @PathVariable UUID id) {
        return ResponseEntity.ok().body(progressService.update(progressRecord, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        progressService.delete(id);

        return ResponseEntity.ok().build();
    }
}