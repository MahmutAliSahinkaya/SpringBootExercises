package com.humanResourceProject.repository;

import com.humanResourceProject.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("From Employee e where e.isRest = :true")
    List<Employee> getRestedUsers(Pageable pageable);

    @Query("From Employee e where e.isRest = :false")
    List<Employee> getNotRestedUsers(Pageable pageable);

}
