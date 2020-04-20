package com.advenspirit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.advenspirit.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query( "select emp from Employee emp where emp.emailId = :empEmailId" )
	Optional<Employee> findEmployeeByEmailId(@Param("empEmailId") String empEmailId);
	
	@Query( "select emp from Employee emp where emp.id = :empId" )
	Optional<Employee> findEmployeeById(@Param("empId") Long empId);

}
