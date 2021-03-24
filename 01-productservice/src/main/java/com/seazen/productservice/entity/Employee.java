package com.seazen.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


//员工实体类
@Entity
@Table(name = "tbl_employee") // 指定关联的数据库的表名
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Employee implements Serializable {
	private static final long serialVersionUID = -991963286980565253L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 主键ID
 
	private String name; // 姓名
	@ManyToOne
	@JoinColumn(name = "department_id")
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
