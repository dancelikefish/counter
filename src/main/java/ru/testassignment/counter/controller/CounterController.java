package ru.testassignment.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.testassignment.counter.repository.CounterRepository;

import java.util.Set;

@RestController
public class CounterController {

    private final CounterRepository counterRepository;

    @Autowired
    public CounterController(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @GetMapping("/counters")
    public ResponseEntity<?> getCountersNames() {
        Set<String> all = counterRepository.getAllNames();
        return ResponseEntity.ok(all.isEmpty() ? "No data" : all);
    }

    @PostMapping("/counter")
    public ResponseEntity<?> saveNewCounter(String name) {
        String savedCounterName = counterRepository.save(name);
        if (savedCounterName != null) {
            return ResponseEntity.ok("Counter with name: " + savedCounterName + " successfully saved");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to save new counter: " + name);
    }

    @PutMapping("/counter")
    public ResponseEntity<?> incrementCounter(String name) {
        boolean incremented = counterRepository.increment(name);
        if (incremented) {
            return ResponseEntity.ok("Counter with name: " + name + " successfully incremented");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to increment counter: " + name);
    }

    @DeleteMapping("/counter")
    public ResponseEntity<?> deleteCounter(String name) {
        boolean removed = counterRepository.remove(name);
        if (removed) {
            return ResponseEntity.ok("Counter with name: " + name + "successfully removed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to delete counter: " + name);
    }

    @GetMapping("/counters/sum")
    public ResponseEntity<?> getCountersSum() {
        return ResponseEntity.ok("Sum of all counters is: " + counterRepository.getAllCountersSum());
    }
}
