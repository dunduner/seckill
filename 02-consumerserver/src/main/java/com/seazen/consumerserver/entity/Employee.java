package com.seazen.consumerserver.entity;

import java.io.Serializable;



public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -991963286980565253L;

	private Long id; // 主键ID
 
	private String name; // 姓名
 
	
	private Department department; // 部门
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public Department getDepartment() {
		return department;
	}
 
	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + "]";
	}
	
	
}
