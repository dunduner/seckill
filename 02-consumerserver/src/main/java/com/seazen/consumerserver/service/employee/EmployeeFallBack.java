package com.seazen.consumerserver.service.employee;

import java.util.List;

import com.seazen.consumerserver.entity.Employee;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

/**
 * 1、	在Feign接口所在包下定义服务降级处理类
 * 实现FallbackFactory
 * @author HUAWEI
 *
 */
@Component
public class EmployeeFallBack implements FallbackFactory<EmployeeService>{
	@Override
	public EmployeeService create(Throwable throwable) {
		return new EmployeeService() {
			@Override
			public Employee getEmployeeById(Long id) {
				Employee employee = new Employee();
				employee.setName("找不到此id呀用的类级别的服务熔断");
				return employee;
			}
			@Override
			public List<Employee> list() {
				System.out.println("类级别的服务熔断");
				return null;
			}
		} ;
	}
}
