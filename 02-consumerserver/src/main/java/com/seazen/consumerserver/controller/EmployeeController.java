package com.seazen.consumerserver.controller;

import java.util.List;

import com.seazen.consumerserver.entity.Employee;
import com.seazen.consumerserver.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	//方式一：发生服务无法提供时候 调用getIdNoThing方法
	@HystrixCommand(fallbackMethod = "getIdNoThing")
	@RequestMapping(value = "/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") Long id) {
		Employee employeeById = employeeService.getEmployeeById(id);
		if(employeeById==null) {
			throw new RuntimeException("运行时异常：no employeeById__id="+id);
		}
		return employeeById ;
	}
	
	public Employee  getIdNoThing(@PathVariable(value = "id") Long id) {
		System.out.println("方法级别的熔断器");
		Employee employee = new Employee();
		employee.setName("此id不存在-方法级别");
		employee.setId(null);
		employee.setDepartment(null);
		return employee;
	}
	
	@RequestMapping(value = "/list")
	public List<Employee> getEmployeeList(){
		System.out.println("mail=======list====方法级别的熔断");
		return employeeService.list();
		
	}
}

