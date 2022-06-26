package com.example.safira.controllers;

import com.example.safira.models.Service;
import com.example.safira.services.ServiceService;
import com.example.safira.utils.Util;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);

    @GetMapping
    public ResponseEntity<List<Service>> getAll() {
        LOGGER.info("Received all services");

        return ResponseEntity.ok().body(serviceService.findAll());
    }

    @GetMapping("/{value}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Service> get(@PathVariable("value") String value) {
        Service service = new Service();

        if (value != null) {
            if (value.matches("[+]?\\d+")) {
                service = Util.getRecordFromTable(Long.parseLong(value), serviceService);
            } else {
                service = serviceService.findAll()
                        .stream()
                        .filter(s -> s.getName().equals(value))
                        .findFirst()
                        .orElse(null);
            }
        }
        LOGGER.info("Received service");

        return ResponseEntity.ok().body(service);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Service> add(@RequestBody Service service) {
        Service serviceForCreate = serviceService.findAll()
                .stream()
                .filter(s -> s.getName().equals(service.getName()))
                .findFirst()
                .orElse(null);

        if (serviceForCreate == null) {
            serviceForCreate = serviceService.save(service);
            LOGGER.info(String.format("%s added", serviceForCreate.getName()));
        } else {
            LOGGER.error("This service already exists");
        }

        return ResponseEntity.status(201).body(serviceForCreate);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Service> update(@PathVariable long id, @RequestBody Service serviceDetails) {
        Service updateService = Util.getRecordFromTable(id, serviceService);
        updateService.setName(serviceDetails.getName());
        updateService.setServiceType(serviceDetails.getServiceType());
        updateService.setPrice(serviceDetails.getPrice());
        serviceService.save(updateService);
        LOGGER.info(String.format("UPDATED SERVICE WITH ID  = %d", updateService.getId()));

        return ResponseEntity.ok().body(updateService);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Service> delete(@PathVariable long id) {
        Service deleteService = Util.getRecordFromTable(id, serviceService);
        serviceService.delete(deleteService);
        LOGGER.info(String.format("DELETED SERVICE WITH ID  = %d", deleteService.getId()));

        return ResponseEntity.ok().body(deleteService);
    }
}
