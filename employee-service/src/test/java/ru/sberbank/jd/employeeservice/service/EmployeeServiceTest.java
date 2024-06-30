package ru.sberbank.jd.employeeservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sberbank.jd.employeeservice.DataHelper;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.repository.EmployeeRepository;


/**
 * Тесты для EmployeeService.
 */

@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository repository;
    @Autowired
    private EmployeeService service;

    /**
     * Тест поиска сотрудника по логину.
     */
    @Test
    void findEmployeeByLogin() {
        Employee employee = DataHelper.createEmployee();
        when(repository.findById(any())).thenReturn(Optional.of(employee));

        EmployeeDto employeeActual = service.findEmployeeByLogin("test_login");

        Mockito.verify(repository, times(1)).findById(any());
        Assertions.assertEquals("test_login", employeeActual.getLogin());
    }

    /**
     * Тест создания сотрудника.
     */
    @Test
    void createEmployee() {
        EmployeeDto employeeDto = DataHelper.createEmployeeDto();

        service.createEmployee(employeeDto);
        Mockito.verify(repository, times(1)).save(any());
    }

    /**
     * Тест обновления данных сотрудника.
     */
    @Test
    void updateEmployee() {
        EmployeeDto employeeDto = DataHelper.createEmployeeDto();
        Employee employee = DataHelper.createEmployee();
        when(repository.findById("test_login")).thenReturn(Optional.of(employee));
        service.updateEmployee(employeeDto);

        Mockito.verify(repository, times(1)).save(any());
    }

    @Test
    void deleteByLogin() {
        service.deleteByLogin("test_login");
        Mockito.verify(repository, times(1)).deleteById("test_login");
    }

    @Test
    void getAll() {
        service.getAll();
        Mockito.verify(repository, times(1)).findAll();
    }

}