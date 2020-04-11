package com.advenspirit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.advenspirit.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query( "select emp from Employee emp where emp.empId = :empId" )
	Optional<Employee> findByEmpId(@Param("empId") String empId);

}
