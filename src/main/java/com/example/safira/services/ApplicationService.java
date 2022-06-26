package com.example.safira.services;

import com.example.safira.models.Application;
import com.example.safira.repositories.ApplicationRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ApplicationService implements Service<Application>{
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application save(Application entity) {
        return applicationRepository.save(entity);
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public void delete(Application entity) {
        applicationRepository.delete(entity);
    }
}
