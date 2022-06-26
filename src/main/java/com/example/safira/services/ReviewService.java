package com.example.safira.services;

import com.example.safira.models.Review;
import com.example.safira.repositories.ReviewRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ReviewService implements Service<Review> {
    public ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public void delete(Review entity) {
        reviewRepository.delete(entity);
    }
}
