package ru.sberbank.jd.employeeservice.converter;

import org.springframework.stereotype.Component;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.entity.Employee;

/**
 * Конвертер для Employee
 */
@Component
public class EmployeeConverter {

    private static final String EMPTY_STRING = "";

    /**
     * Конвертер Employee -> EmployeeDto
     *
     * @param employee - employee
     * @return - employeeDto
     */
    public EmployeeDto convertEntityToDto(Employee employee) {
        return EmployeeDto.builder()
                .login(employee.getLogin())
                .password(employee.getPassword())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .roles(employee.getRoles())
                .birthDate(employee.getBirthDate())
//                .department_id(employee.getDepartment().getId())
                .build();
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
        employee.setRoles(employeeDto.getRoles());
        return employee;
    }

    private String convertIfNull(String someString) {
        return someString != null ? someString : EMPTY_STRING;
    }

}
