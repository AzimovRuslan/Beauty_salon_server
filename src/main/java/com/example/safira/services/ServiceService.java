package com.example.safira.services;

import com.example.safira.repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService implements Service<com.example.safira.models.Service> {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public com.example.safira.models.Service save(com.example.safira.models.Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<com.example.safira.models.Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Optional<com.example.safira.models.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public void delete(com.example.safira.models.Service entity) {
        serviceRepository.delete(entity);
    }
}
