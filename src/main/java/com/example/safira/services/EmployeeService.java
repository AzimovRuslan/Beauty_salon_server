package com.example.safira.services;

import com.example.safira.models.Employee;
import com.example.safira.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class EmployeeService implements Service<Employee>{
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void delete(Employee entity) {
        employeeRepository.delete(entity);
    }

}
