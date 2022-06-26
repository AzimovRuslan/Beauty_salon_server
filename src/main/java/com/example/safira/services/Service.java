package com.example.safira.services;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    T save(T t);

    List<T> findAll();

    Optional<T> findById(Long id);

    void delete(T entity);
}
