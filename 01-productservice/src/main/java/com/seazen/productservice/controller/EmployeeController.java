package com.seazen.productservice.controller;

import com.seazen.productservice.dao.EmployeeDao;
import com.seazen.productservice.entity.Department;
import com.seazen.productservice.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	protected Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private HttpServletRequest request;

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
		String remoteAddr = request.getRemoteAddr();//返回发出请求的ip地址
		String requestURI = request.getRequestURI();//返回请求中的资源名称
		String RequestURL = request.getRequestURL().toString();//获取客户端发送请求的完整url
		String queryString = request.getQueryString();//返回发出请求行中的参数部分
		String remoteHost = request.getRemoteHost();//返回请求端的主机名
		int remotePort = request.getRemotePort();//返回请求端的主机的端口号
		System.out.println("返回发出请求的ip地址："+remoteAddr);
		System.out.println("requestURI:"+requestURI);
		System.out.println("RequestURL:"+RequestURL);
		System.out.println("queryString:"+queryString);
		System.out.println("remoteHost:"+remoteHost);
		System.out.println("remotePort:"+remotePort);
		System.out.println();
		logger.info("employee/list---zn");
		List<Employee> findAll = employeeDao.findAll();
		return findAll;
	};

}
