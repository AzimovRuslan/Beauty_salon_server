package com.example.safira.controllers;

import com.example.safira.models.Service;
import com.example.safira.models.ServiceType;
import com.example.safira.services.ServiceTypeService;
import com.example.safira.utils.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/service_type")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTypeController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ServiceType>> getAll() {
        LOGGER.info("Received all types of services");

        return ResponseEntity.ok().body(serviceTypeService.findAll());
    }

//    @GetMapping("{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ServiceType> get(@PathVariable long id) {
//        ServiceType serviceTypeFromDB = Util.getRecordFromTable(id, serviceTypeService);
//
//        return ResponseEntity.ok().body(serviceTypeFromDB);
//    }

    @GetMapping("/{value}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ServiceType> get(@PathVariable("value") String value) {
        ServiceType serviceType = new ServiceType();

        if (value != null) {
            if (value.matches("[+]?\\d+")) {
                serviceType = Util.getRecordFromTable(Long.parseLong(value), serviceTypeService);
            } else {
                serviceType = serviceTypeService.findAll()
                        .stream()
                        .filter(s -> s.getName().equals(value))
                        .findFirst()
                        .orElse(null);
            }
        }
        LOGGER.info("Received service type");

        return ResponseEntity.ok().body(serviceType);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ServiceType> add(@RequestBody ServiceType serviceType) {
        ServiceType serviceTypeForCreate = serviceTypeService.findAll()
                .stream()
                .filter(s -> s.getName().equals(serviceType.getName()))
                .findFirst()
                .orElse(null);

        if (serviceTypeForCreate == null) {
            serviceTypeForCreate = serviceTypeService.save(serviceType);
            LOGGER.info(String.format("%s added", serviceTypeForCreate.getName()));
        } else {
            LOGGER.error("This type of service already exists");
        }

        return ResponseEntity.status(201).body(serviceTypeService.save(serviceTypeForCreate));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ServiceType> update(@PathVariable long id, @RequestBody ServiceType serviceTypeDetails) {
        ServiceType updateServiceType = Util.getRecordFromTable(id, serviceTypeService);
        updateServiceType.setName(serviceTypeDetails.getName());
        serviceTypeService.save(updateServiceType);
        LOGGER.info(String.format("UPDATED TYPE OF SERVICE WITH ID  = %d", updateServiceType.getId()));

        return ResponseEntity.ok().body(updateServiceType);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ServiceType> delete(@PathVariable long id) {
        ServiceType deleteServiceType = Util.getRecordFromTable(id, serviceTypeService);
        serviceTypeService.delete(deleteServiceType);
        LOGGER.info(String.format("DELETED TYPE OF SERVICE WITH ID  = %d", deleteServiceType.getId()));

        return ResponseEntity.ok().body(deleteServiceType);
    }
}
