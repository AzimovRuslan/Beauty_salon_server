package com.example.safira.services;

import com.example.safira.models.User;
import com.example.safira.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class UserService implements Service<User>{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }
}
