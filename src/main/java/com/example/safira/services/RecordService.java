package com.example.safira.services;

import com.example.safira.models.Record;
import com.example.safira.repositories.RecordRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class RecordService implements Service<Record> {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record save(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public Optional<Record> findById(Long id) {
        return recordRepository.findById(id);
    }

    @Override
    public void delete(Record entity) {
        recordRepository.delete(entity);
    }
}
