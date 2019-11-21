package com.seazen.productservice.dao;

import com.seazen.productservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 部门的jpa  dao层类
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
