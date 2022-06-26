package com.example.safira.controllers;

import com.example.safira.models.*;
import com.example.safira.models.Record;
import com.example.safira.models.Service;
import com.example.safira.services.*;
import com.example.safira.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/record")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecordController {
    private final ApplicationService applicationService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final RecordService recordService;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

    public RecordController(ApplicationService applicationService, ServiceService serviceService, UserService userService, RecordService recordService, EmployeeService employeeService) {
        this.applicationService = applicationService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.recordService = recordService;
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Record>> getAll() {
        return ResponseEntity.ok().body(recordService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Record> add(@RequestBody Record record) throws ParseException {
        Application application = applicationService.findAll()
                .stream()
                .filter(a -> a.getId().equals(record.getApplication().getId()))
                .findFirst()
                .orElse(null);

        Employee employee = employeeService.findAll()
                .stream()
                .filter(e -> e.getId().equals(record.getEmployee().getId()))
                .findFirst()
                .orElse(null);

        Service service = serviceService.findAll()
                .stream()
                .filter(s -> s.getId().equals(record.getService().getId()))
                .findFirst()
                .orElse(null);

        Record recordForSave = new Record();

        if (application != null && employee != null && service != null) {
            Util.getRecordFromTable(record.getApplication().getId(), applicationService).setStatus("confirmed");

            recordForSave.setApplication(application);
            recordForSave.setService(service);
            recordForSave.setPrice(service.getPrice());
            recordForSave.setDate(record.getDate());
            recordForSave.setStartTime(record.getStartTime());
            recordForSave.setFinishTime(record.getFinishTime());
            recordForSave.setEmployee(employee);
        } else {
            logger.error("Unable to add record to table");
        }

        return ResponseEntity.status(201).body(recordService.save(recordForSave));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Record> update(@PathVariable long id, @RequestBody Record recordDetails) {
        Record updateRecord = Util.getRecordFromTable(id, recordService);

        updateRecord.setApplication(recordDetails.getApplication());
        updateRecord.setDate(recordDetails.getDate());
        updateRecord.setEmployee(recordDetails.getEmployee());
        updateRecord.setFinishTime(recordDetails.getFinishTime());
        updateRecord.setStartTime(recordDetails.getStartTime());
        updateRecord.setService(recordDetails.getService());
        updateRecord.setPrice(recordDetails.getService().getPrice());

        recordService.save(updateRecord);

        return ResponseEntity.ok().body(updateRecord);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Record> delete(@PathVariable long id) {
        Record deleteRecord = Util.getRecordFromTable(id, recordService);
        List<Application> applications = applicationService.findAll();
        recordService.delete(deleteRecord);


        for (Application application : applications) {
            if (application.getId() == deleteRecord.getApplication().getId()) {
                applicationService.delete(application);
            }
        }

        return ResponseEntity.ok().body(deleteRecord);
    }
}
