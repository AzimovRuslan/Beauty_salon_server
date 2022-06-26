package com.example.safira.controllers;

import com.example.safira.models.*;
import com.example.safira.services.EmployeeService;
import com.example.safira.services.ReviewService;
import com.example.safira.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        LOGGER.info("Received all reviews");

        return ResponseEntity.ok().body(reviewService.findAll());
    }

    @GetMapping("/{value}")
    public ResponseEntity<List<Review>> get(@PathVariable("value") String value) {
        List<Review> reviews = new ArrayList<>();

        if (value != null) {
            if (value.matches("[+]?\\d+")) {
                for (Review review : reviewService.findAll()) {
                    if (review.getEmployee().getId() == Integer.parseInt(value)) {
                        reviews.add(review);
                    }
                }
            } else {
                reviews = reviewService.findAll();
            }
        }

        LOGGER.info("Received all reviews");

        return ResponseEntity.ok().body(reviews);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Review> add(@RequestBody Review review) {
        Review reviewForCreate = null;

        Employee employee = employeeService.findAll()
                .stream()
                .filter(e -> e.getName().equals(review.getEmployee().getName()) && e.getSurname().equals(review.getEmployee().getSurname()) && e.getPatronymic().equals(review.getEmployee().getPatronymic()))
                .findFirst()
                .orElse(null);

        User user = userService.findAll()
                .stream()
                .filter(u -> u.getUsername().equals(review.getUser().getUsername()))
                .findFirst()
                .orElse(null);

        if (employee != null && user != null) {
            review.setUser(user);
            review.setDescription(review.getDescription());
            review.setDate(review.getDate());
            review.setEmployee(employee);
        }

        reviewForCreate = reviewService.save(review);

        return ResponseEntity.status(201).body(reviewForCreate);
    }

}
