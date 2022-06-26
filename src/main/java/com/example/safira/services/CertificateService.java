package com.example.safira.services;

import com.example.safira.models.Certificate;
import com.example.safira.repositories.CertificateRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CertificateService implements Service<Certificate> {
    private CertificateRepository certificateRepository;

    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return certificateRepository.findById(id);
    }

    @Override
    public void delete(Certificate entity) {
        certificateRepository.delete(entity);
    }
}
