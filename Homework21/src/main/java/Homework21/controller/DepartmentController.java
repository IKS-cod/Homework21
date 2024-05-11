package Homework21.controller;

import Homework21.model.Employee;
import Homework21.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalaryInDepartment(@RequestParam int departmentId) {
        return departmentService.findEmployeeWithMaxSalaryInDepartment(departmentId);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalaryInDepartment(@RequestParam Integer departmentId) {
        return departmentService.findEmployeeWithMinSalaryInDepartment(departmentId);
    }

    @GetMapping(value = "/all",params = {"departmentId"})
    public Collection<Employee> findEmployeesByDepartment(@RequestParam Integer departmentId) {
        return departmentService.findEmployeesByDepartment(departmentId);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmployeesAllDepartment() {
        return departmentService.findEmployeesAllDepartment();
    }
}
