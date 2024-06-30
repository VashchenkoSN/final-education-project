package ru.sberbank.jd.employeeservice;

import java.time.LocalDate;
import java.util.Set;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.dto.RoleDto;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.entity.Role;

/**
 * Создание тестовых данных.
 */
public class DataHelper {

    /**
     * Создать EmployeeDto.
     *
     * @return - EmployeeDto
     */
    public static EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLogin("test_login");
        employeeDto.setPassword("test_password");
        employeeDto.setFirstName("FirstName");
        employeeDto.setLastName("LastName");
        employeeDto.setRoles(Set.of(RoleDto.ROLE_EMPLOYEE));
        employeeDto.setBirthDate(LocalDate.now().minusYears(30));
        return employeeDto;
    }

    /**
     * Создать Employee.
     *
     * @return - Employee
     */
    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setLogin("test_login");
        employee.setPassword("test_password");
        employee.setFirstName("FirstName");
        employee.setLastName("LastName");
        employee.setRoles(Set.of(Role.ROLE_EMPLOYEE));
        employee.setBirthDate(LocalDate.now().minusYears(30));
        return employee;
    }
}
