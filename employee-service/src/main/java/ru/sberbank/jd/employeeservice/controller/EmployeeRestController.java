package ru.sberbank.jd.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class EmployeeRestController {

    private final EmployeeService employeeService;

    /**
     * Получить данные сотрудника по логину.
     *
     * @param login - логин сотрудника
     * @return - данные сотрудника
     */
    @GetMapping("employee/{login}")
    @SneakyThrows
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
    public EmployeeDto update(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeDto);
    }

    /**
     * Удалить сотрудника.
     *
     * @param login - логин сотрудника
     */
    @PutMapping("employee/{login}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable("login") String login) {
        employeeService.deleteByLogin(login);
    }


}
