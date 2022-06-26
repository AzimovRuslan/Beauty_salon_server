package com.example.safira.controllers;

import com.example.safira.models.Employee;
import com.example.safira.models.Review;
import com.example.safira.services.EmployeeService;
import com.example.safira.services.ReviewService;
import com.example.safira.utils.Util;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ReviewService reviewService;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        LOGGER.info("Received all employees");

        return ResponseEntity.ok().body(employeeService.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Employee> get(@PathVariable long id) {
        Employee employee = getEmployeeFromDb(id);
        LOGGER.info("Received employee");

        return ResponseEntity.ok().body(employee);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> add(@RequestBody Employee employee) {
        Employee employeeForCreate = employeeService.findAll()
                .stream()
                .filter(e -> e.getName().equals(employee.getName()) && e.getSurname().equals(employee.getSurname())
                        && e.getPatronymic().equals(employee.getPatronymic()) && e.getPhoneNumber() == employee.getPhoneNumber())
                .findFirst()
                .orElse(null);

        if (employeeForCreate == null) {
            employeeForCreate = employeeService.save(employee);
            LOGGER.info(String.format("%s added", employeeForCreate.getName()));
        } else {
            LOGGER.error("This service already exists");
        }
        return ResponseEntity.status(201).body(employeeService.save(employeeForCreate));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee employeeDetails) {
        Employee updateEmployee = Util.getRecordFromTable(id, employeeService);

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setSurname(employeeDetails.getSurname());
        updateEmployee.setPatronymic(employeeDetails.getPatronymic());
        updateEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
        updateEmployee.setSalary(employeeDetails.getSalary());
        updateEmployee.setServiceType(employeeDetails.getServiceType());

        employeeService.save(updateEmployee);

        return ResponseEntity.ok().body(updateEmployee);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> delete(@PathVariable long id) {
        List<Review> reviews = reviewService.findAll();

        for (Review review : reviews) {
            if (review.getEmployee().getId() == id) {
                reviewService.delete(review);
            }
        }

        Employee deleteEmployee = Util.getRecordFromTable(id, employeeService);
        employeeService.delete(deleteEmployee);

        return ResponseEntity.ok().body(deleteEmployee);
    }

    private Employee getEmployeeFromDb(Long id) {
        Optional<Employee> employeeFromDb = employeeService.findById(id);

        return employeeFromDb.orElseThrow(() -> new EntityNotFoundException("id-" + id));
    }
}
