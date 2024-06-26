package ru.sberbank.jd.employeeservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.jd.employeeservice.entity.Department;

/**
 * Репозиторий для Department.
 */
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

}

