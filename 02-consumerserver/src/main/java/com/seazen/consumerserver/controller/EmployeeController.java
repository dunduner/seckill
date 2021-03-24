package com.seazen.consumerserver.controller;

import java.util.List;

import com.seazen.consumerserver.entity.Employee;
import com.seazen.consumerserver.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    //方式一：发生服务无法提供时候 调用getIdNoThing方法
    //commandProperties = {@HystrixProperty(name = "circuitBreaker.forceOpen", value = "true")} //强行打开断路器

//    @HystrixCommand(fallbackMethod = "getIdNoThing")
    @RequestMapping(value = "/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long id) {
        Employee employeeById = employeeService.getEmployeeById(id);
        if (employeeById == null) {
            throw new RuntimeException("运行时异常：no employeeById__id=" + id);
        }
        String Authorization = request.getHeader("Authorization");//返回发出请求的ip地址
        String token = request.getHeader("token");//返回发出请求的ip地址
        System.out.println("消费者收到的token值是:"+token);
        System.out.println("消费者收到的Authorization值是:"+Authorization);
//        employeeById.setName("服务消费者的port是："+port);
        return employeeById;
    }

    public Employee getIdNoThing(@PathVariable(value = "id") Long id) {
        System.out.println("方法级别的熔断器");
        Employee employee = new Employee();
        employee.setName("此id不存在-方法级别");
        employee.setId(null);
        employee.setDepartment(null);
        return employee;
    }

    @RequestMapping(value = "/list")
    public List<Employee> getEmployeeList() {
        return employeeService.list();

    }
}

