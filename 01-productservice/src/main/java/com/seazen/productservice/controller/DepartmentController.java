package com.seazen.productservice.controller;

import com.seazen.productservice.dao.DepartmentRepository;
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


@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Value("${server.port}")
    private String port;
    @Autowired
    private DepartmentRepository departmentRepository;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Department> list() {
        List<Department> findAll = departmentRepository.findAll();
        return findAll;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Department getDepartmentById(@PathVariable(value = "id") Long id) {
        Department one = departmentRepository.findById(id).get();
        return one;
    }

    @RequestMapping(value="/add/{department}",method = RequestMethod.GET)
    @ResponseBody
    public Department addDepartment(@PathVariable("department") String departmentName){
        Department department = new Department();
        department.setName(departmentName);
        Department departmentSave = departmentRepository.save(department);
        System.out.println("新增："+departmentSave);
        return  departmentSave;
    }
}
