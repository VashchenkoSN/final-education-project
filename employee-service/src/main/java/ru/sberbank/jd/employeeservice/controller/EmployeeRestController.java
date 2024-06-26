package ru.sberbank.jd.employeeservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.service.EmployeeService;

/**
 * Контроллер для Employee
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class EmployeeRestController implements IEmployeeRestController{

    private final EmployeeService employeeService;

    /**
     * Получить данные сотрудника по логину.
     *
     * @param login - логин сотрудника
     * @return - данные сотрудника
     */
    @GetMapping("employee/{login}")
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public EmployeeDto findByLogin(@PathVariable("login") String login) {
        return employeeService.findEmployeeByLogin(login);
    }

    /**
     * Создать сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    @PostMapping("employee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    /**
     * Обновить данные сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    @PutMapping("employee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public EmployeeDto update(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeDto);
    }

    /**
     * Удалить сотрудника.
     *
     * @param login - логин сотрудника
     */
    @DeleteMapping("employee/{login}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public void delete(@PathVariable("login") String login) {
        employeeService.deleteByLogin(login);
    }

    /**
     * Получить список всех сотрудников.
     *
     * @return - список сотрудников
     */
    @GetMapping("employees")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public List<EmployeeDto> getEmpoloyeesList() {
        return employeeService.getAll();
    }

}
