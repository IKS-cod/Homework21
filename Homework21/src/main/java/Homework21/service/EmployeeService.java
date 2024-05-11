package Homework21.service;

import Homework21.exception.EmployeeAlreadyAddedException;
import Homework21.exception.EmployeeNotFoundException;
import Homework21.exception.EmployeeStorageIsFullException;
import Homework21.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class EmployeeService {
    private final int maxEmployees = 10;
    private final String backupFileName = "./data/map.json";
    private ObjectMapper objectMapper;

    public EmployeeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private Map<String, Employee> employees;

    public Employee addEmployee(String firstName, String lastName, Integer departmentId, Double salary) {
        if (employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() >= maxEmployees) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName, departmentId, salary);
        save();
        return employees.put(buildKey(firstName, lastName), employee);
    }

    public Employee removeEmployee(String firstName, String lastName) {
        if (!employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(buildKey(firstName, lastName));
    }

    public Employee findEmployee(String firstName, String lastName) {
        if (!employees.containsKey(buildKey(firstName, lastName))) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(buildKey(firstName, lastName));
    }

    public Collection<Employee> findAll() {
        return employees.values();
    }

    private String buildKey(String firstName, String lastName) {
        return firstName + lastName;
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(backupFileName)) {
            objectMapper.writeValue(fileWriter, employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        try (FileReader fileReader = new FileReader(backupFileName)) {
            employees = objectMapper.readValue(fileReader, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            employees = new HashMap<>();
        }
    }
}
