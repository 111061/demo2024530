package com.example.demo.Service;
import com.example.demo.DTO.Employee;
import com.example.demo.DTO.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }
    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }
    // 員工服務
    public Employee addEmployee(Employee employee) {
        // 處理新增員工的邏輯
        return employeeRepository.save(employee);
    }
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }
    // 其他查詢方法...
}
