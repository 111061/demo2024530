package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.Employee;
import com.example.demo.Service.EmployeeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.example.demo.Service.EmailService;
import com.example.demo.DTO.EmailDetails;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmailService emailService;

    // 获取所有员工
    @GetMapping("/test")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.findAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根据姓名查询员工
    @GetMapping("/test/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name) {
        try {
            List<Employee> employees = employeeService.findByName(name);
            if (employees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 添加一个新员工
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 增加錯誤輸出
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 批量删除员工
    @DeleteMapping("/delete")
    public ResponseEntity<List<Employee>> deleteEmployee(@RequestBody List<Long> employeeIds) {
        try {
            for (Long employeeId : employeeIds) {
                employeeService.deleteEmployeeById(employeeId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新員工資料
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee existingEmployee = employeeService.findEmployeeById(id);
            if (existingEmployee == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            existingEmployee.setName(employee.getName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setBirthday(employee.getBirthday());
            existingEmployee.setMail(employee.getMail());
            existingEmployee.setTel(employee.getTel());
            existingEmployee.setAddress(employee.getAddress());

            Employee updatedEmployee = employeeService.addEmployee(existingEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 群发邮件
    @PostMapping("/sendEmails")
    public ResponseEntity<?> sendEmailsToEmployees(@RequestBody EmailDetails details) {
        try {
            // 获取邮件详情
            String subject = details.getSubject();
            String content = details.getContent();
            String account = details.getAccount();
            String password = details.getPassword();

            // 发送邮件到所有收件人
            emailService.sendEmail(details.getEmails(), subject, content, account, password);

            return new ResponseEntity<>("Emails sent successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 增加错误输出
            return new ResponseEntity<>("Error sending emails", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
