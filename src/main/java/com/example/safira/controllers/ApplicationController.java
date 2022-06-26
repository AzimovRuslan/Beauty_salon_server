package com.example.safira.controllers;

import com.example.safira.models.Application;
import com.example.safira.models.Record;
import com.example.safira.models.ServiceType;
import com.example.safira.models.User;
import com.example.safira.services.*;
import com.example.safira.utils.Util;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final ServiceTypeService serviceTypeService;
    private final RecordService recordService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok().body(applicationService.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Application> get(@PathVariable long id) {
        Application applicationFromTable = Util.getRecordFromTable(id, applicationService);

        return ResponseEntity.ok().body(applicationFromTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Application> add(@RequestBody Application application) {
        ServiceType serviceType = serviceTypeService.findAll()
                .stream()
                .filter(s -> s.getName().equals(application.getServiceType().getName()))
                .findFirst()
                .orElse(null);

        User user = userService.findAll()
                .stream()
                .filter(u -> u.getUsername().equals(application.getUser().getUsername()))
                .findFirst()
                .orElse(null);

        Application applicationForSave = new Application();

        if (serviceType != null && user != null) {
            applicationForSave.setUser(user);
            applicationForSave.setUserPhone(application.getUserPhone());
            applicationForSave.setServiceType(serviceType);
        } else {
            logger.error("Unable to add application to table");
        }

        return ResponseEntity.status(201).body(applicationService.save(applicationForSave));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Application> update(@PathVariable long id, @RequestBody Application applicationDetails) {
        Application updateApplication = Util.getRecordFromTable(id, applicationService);

        updateApplication.setUser(applicationDetails.getUser());
        updateApplication.setServiceType(applicationDetails.getServiceType());

        applicationService.save(updateApplication);

        return ResponseEntity.ok().body(updateApplication);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Application> delete(@PathVariable long id) {
        List<Record> records = recordService.findAll();

        for (Record record : records) {
            if (record.getApplication().getId() == id) {
                recordService.delete(record);
            }
        }

        Application deleteApplication = Util.getRecordFromTable(id, applicationService);
        applicationService.delete(deleteApplication);

        return ResponseEntity.ok().body(deleteApplication);
    }
}
