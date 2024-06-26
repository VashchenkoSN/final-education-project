package ru.sberbank.jd.employeeservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;

public interface IEmployeeRestController {

    /**
     * Получить данные сотрудника по логину.
     *
     * @param login - логин сотрудника
     * @return - данные сотрудника
     */
    @GetMapping("employee/{login}")
    EmployeeDto findByLogin(@PathVariable("login") String login);


    /**
     * Создать сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    @PostMapping("employee")
    EmployeeDto create(@RequestBody EmployeeDto employeeDto);


    /**
     * Обновить данные сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    @PutMapping("employee")
    EmployeeDto update(@RequestBody EmployeeDto employeeDto);


    /**
     * Удалить сотрудника.
     *
     * @param login - логин сотрудника
     */
    @DeleteMapping("employee/{login}")
    void delete(@PathVariable("login") String login);


    /**
     * Получить список всех сотрудников.
     *
     * @return - список сотрудников
     */
    @GetMapping("employees")
    List<EmployeeDto> getEmpoloyeesList();

}
