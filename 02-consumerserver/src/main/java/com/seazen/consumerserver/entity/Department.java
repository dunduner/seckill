package com.seazen.consumerserver.entity;

import java.io.Serializable;


public class Department  implements Serializable{
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5900200245183856220L;

	private Long id; // 主键ID
 
	private String name; // 部门名称
 
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

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
 
}
