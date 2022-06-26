package com.example.safira.utils;

import com.example.safira.services.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class Util {
    public static <T> T getRecordFromTable(Long id, Service<T> service) {
        Optional<T> recordFromTable = service.findById(id);

        return recordFromTable.orElseThrow(() -> new EntityNotFoundException("id-" + id));
    }
}
