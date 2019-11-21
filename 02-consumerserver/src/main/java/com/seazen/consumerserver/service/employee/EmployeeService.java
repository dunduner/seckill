package com.seazen.consumerserver.service.employee;

import java.util.List;

import com.seazen.consumerserver.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Service
@FeignClient(value = "product-server",fallbackFactory = EmployeeFallBack.class)
//@FeignClient(value = "PRODUCT-SERVER")
public interface EmployeeService {
	
	@RequestMapping(value="/employee/{id}")
	Employee getEmployeeById(@PathVariable(value = "id") Long id);
	@RequestMapping(value = "/employee/list", method = RequestMethod.GET)
	public List<Employee> list();

}
