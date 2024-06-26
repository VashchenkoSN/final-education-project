package ru.sberbank.jd.employeeservice.converter;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.dto.RoleDto;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.entity.Role;

/**
 * Конвертер для Employee.
 */
@Component
public class EmployeeConverter {
    /**
     * Конвертер Employee -> EmployeeDto
     *
     * @param employee - employee
     * @return - employeeDto
     */
    public EmployeeDto convertEntityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLogin(employee.getLogin());
        employeeDto.setPassword(employee.getPassword());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setRoles(employee.getRoles().stream().map(role -> RoleDto.valueOf(role.name())).collect(Collectors.toSet()));
        employeeDto.setBirthDate(employee.getBirthDate());

        return employeeDto;
    }

    /**
     * Конвертер EmployeeDto -> Employee
     *
     * @param employeeDto - employeeDto
     * @return - employee
     */
    public Employee convertDtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setLogin(employeeDto.getLogin());
        employee.setPassword(employeeDto.getPassword());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setRoles(employeeDto.getRoles().stream().map(role -> Role.valueOf(role.name())).collect(Collectors.toSet()));
        return employee;
    }

}
