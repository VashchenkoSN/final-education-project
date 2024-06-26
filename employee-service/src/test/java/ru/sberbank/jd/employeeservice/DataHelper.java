package ru.sberbank.jd.employeeservice;

import java.time.LocalDate;
import java.util.Set;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.dto.RoleDto;

/**
 * Создание тестовых данных.
 */
public class DataHelper {
    public static EmployeeDto createEmployeeDto(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLogin("test_login");
        employeeDto.setPassword("test_password");
        employeeDto.setFirstName("FirstName");
        employeeDto.setLastName("LastName");
        employeeDto.setRoles(Set.of(RoleDto.ROLE_EMPLOYEE));
        employeeDto.setBirthDate(LocalDate.now().minusYears(30));
        return employeeDto;

    }
}
