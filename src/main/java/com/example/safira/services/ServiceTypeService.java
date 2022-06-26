package com.example.safira.services;

import com.example.safira.models.ServiceType;
import com.example.safira.repositories.ServiceTypeRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceTypeService implements Service<ServiceType> {
    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public ServiceType save(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    @Override
    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public Optional<ServiceType> findById(Long id) {
        return serviceTypeRepository.findById(id);
    }

    @Override
    public void delete(ServiceType entity) {
        serviceTypeRepository.delete(entity);
    }
}
