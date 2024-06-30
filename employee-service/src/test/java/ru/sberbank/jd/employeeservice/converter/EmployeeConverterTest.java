package ru.sberbank.jd.employeeservice.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.dto.RoleDto;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.entity.Role;

/**
 * Тесты конвертации для Employee.
 */

class EmployeeConverterTest {

    private final EmployeeConverter converter = new EmployeeConverter();

    /**
     * Тест конвертации Employee в DTO.
     */
    @Test
    void convertEntityToDto() {
        Employee employee = new Employee();
        employee.setLogin("testLogin");
        employee.setFirstName("testFirstName");
        employee.setLastName("testLastName");
        employee.setPassword("testPassword");
        employee.setRoles(Set.of(Role.ROLE_EMPLOYEE));

        EmployeeDto dto = converter.convertEntityToDto(employee);
        assertEquals("testLogin", dto.getLogin());
        assertEquals("testPassword", dto.getPassword());
        assertEquals("testFirstName", dto.getFirstName());
        assertEquals("testLastName", dto.getLastName());
        assertEquals(Set.of(RoleDto.ROLE_EMPLOYEE), dto.getRoles());

    }

    /**
     * Тест конвертации DTO в Employee.
     */
    @Test
    void convertDtoToEntity() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLogin("testLogin");
        employeeDto.setFirstName("testFirstName");
        employeeDto.setLastName("testLastName");
        employeeDto.setPassword("testPassword");
        employeeDto.setRoles(Set.of(RoleDto.ROLE_EMPLOYEE));

        Employee employee= converter.convertDtoToEntity(employeeDto);
        assertEquals("testLogin", employee.getLogin());
        assertEquals("testPassword", employee.getPassword());
        assertEquals("testFirstName", employee.getFirstName());
        assertEquals("testLastName", employee.getLastName());
        assertEquals(Set.of(Role.ROLE_EMPLOYEE), employee.getRoles());
    }
}