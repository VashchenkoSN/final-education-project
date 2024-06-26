package ru.sberbank.jd.employeeservice.converter;

import org.springframework.stereotype.Component;
import ru.sberbank.jd.employeeservice.dto.DepartmentDto;
import ru.sberbank.jd.employeeservice.entity.Department;

/**
 * Конвертер для Department.
 */
@Component
public class DepartmentConverter {
    /**
     * Конвертер Department -> DepartmentDto
     *
     * @param department - department
     * @return - departmentDto
     */
    public DepartmentDto convertEntityToDto(Department department) {
      DepartmentDto departmentDto = new DepartmentDto();
      departmentDto.setId(department.getId());
      departmentDto.setTitle(department.getTitle());
      return departmentDto;
    }

    /**
     * Конвертер DepartmentDto -> Department
     *
     * @param departmentDto - departmentDto
     * @return - department
     */
    public Department convertDtoToEntity(DepartmentDto departmentDto) {
         Department department = new Department();
         department.setId(departmentDto.getId());
         department.setTitle(departmentDto.getTitle());
         return department;
    }
}
