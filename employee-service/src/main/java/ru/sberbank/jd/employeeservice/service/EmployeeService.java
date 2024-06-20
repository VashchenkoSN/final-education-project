package ru.sberbank.jd.employeeservice.service;

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ru.sberbank.jd.employeeservice.converter.EmployeeConverter;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.repository.EmployeeRepository;

/**
 * Сервисный класс для Employee
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeConverter converter;

    /**
     * Найти и вернуть сотрудника по логину.
     *
     * @param login - логин сотрудника
     * @return - данные сотрудника
     */
    @SneakyThrows
    public EmployeeDto findEmployeeByLogin(String login) {
        Employee employee = repository.findById(login)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        String.format("Сотрудник с логином %s не найден", login)));

        return converter.convertEntityToDto(employee);
    }

    /**
     * Создать нового сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = converter.convertDtoToEntity(employeeDto);
        String login = employee.getLogin();
        if (login.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Логин должен быть заполнен");
        }
        if (employee.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пароль должен быть заполнен");
        }
        if (repository.findById(login).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Сотрудник с логином %s уже существует", login));
        }
        repository.save(employee);
        log.info("Saving new employee: {}", employee.getLogin());

        return employeeDto;
    }

    /**
     * Обновить данные сотрудника в репозитории.
     *
     * @param employeeDto - данные сотрудника
     * @return - данные сотрудника
     */
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        String login = employeeDto.getLogin();
        repository.findById(login)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        String.format("Employee with login %s is not exists", login)));
        Employee employee = converter.convertDtoToEntity(employeeDto);
        repository.save(employee);
        return employeeDto;
    }

    /**
     * Удалить сотрудника из репозитория.
     *
     * @param login - логин удаляемого сотрудника
     */
    public void deleteByLogin(String login) {
        repository.deleteById(login);
    }

    /**
     * Вернуть список всех сотрудников компании.
     *
     * @return - список сотрудников компании
     */
    public List<Employee> getAll() {
        return repository.findAll();
    }

    /**
     * Вернуть данные пользователя.
     *
     * @param principal - principal
     * @return - сотрудник
     */
    public Employee getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return null;
        } else {
            return repository.findById(principal.getName()).orElseThrow();
        }
    }
}