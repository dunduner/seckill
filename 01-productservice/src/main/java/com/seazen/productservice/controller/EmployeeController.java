package com.seazen.productservice.controller;

import com.seazen.productservice.dao.EmployeeDao;
import com.seazen.productservice.entity.Department;
import com.seazen.productservice.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
	@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeDao employeeDao;

	@Value("${server.port}")
	private String port;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Employee getEmployeeById(@PathVariable(value = "id") Long id) {
		Optional<Employee> byId = employeeDao.findById(id);
		Employee employee = byId.get();
		employee.setName("端口号是："+port);
		return employee;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> list(){
		List<Employee> findAll = employeeDao.findAll();
		return findAll;
	};

}
