package ru.sberbank.jd.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.jd.employeeservice.entity.Employee;

/**
 * Репозиторий для Employee.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
