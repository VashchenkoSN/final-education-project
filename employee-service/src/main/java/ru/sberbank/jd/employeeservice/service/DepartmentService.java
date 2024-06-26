package ru.sberbank.jd.employeeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.jd.employeeservice.converter.DepartmentConverter;
import ru.sberbank.jd.employeeservice.dto.DepartmentDto;
import ru.sberbank.jd.employeeservice.entity.Department;
import ru.sberbank.jd.employeeservice.repository.DepartmentRepository;

/**
 * Сервисный класс для операций с сущностью "Отдел".
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {
    private final DepartmentRepository repository;
    private final DepartmentConverter departmentConverter;

    /**
     * Создать Отдел.
     * @param departmentDto - данные отдела
     * @return - данные отдела
     */
    public Department createDepartment(DepartmentDto departmentDto){
        Department department = departmentConverter.convertDtoToEntity(departmentDto);
        repository.save(department);
        log.info("Saving new department with data {}", department);

        return department;
    }


}
