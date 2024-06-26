package ru.sberbank.jd.employeeservice.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.service.EmployeeService;

/**
 * Ui-контроллер для Employee
 */
@Controller
@RequiredArgsConstructor
public class EmployeeUiController {

    private final EmployeeService employeeService;

    /**
     * Корневая страница - список сотрудников.
     *
     * @param principal - principal
     * @param model     - model
     * @return - адрес корневой страницы
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public String employees(Principal principal, Model model) {
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("user", employeeService.getUserByPrincipal(principal));
        return "employee-list";
    }

    /**
     * Форма логина.
     *
     * @param error - ошибка аутентификации
     * @param model - модель
     * @return - адрес  UI-страницы формы логина
     */
    @GetMapping("/ui/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    /**
     * Форма создания сотрудника.
     *
     * @return - адрес UI-страницы, на который выполняем переход
     */
    @GetMapping("/ui/employee/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String newEmployeeForm() throws Forbidden {
        return "new-employee";
    }


    /**
     * Обработчик создания сотрудника.
     *
     * @param model       - модель
     * @param employeeDto - данные сотрудника
     * @return - адрес UI-страницы, на который выполняем переход
     */
    @PostMapping("/ui/employee/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String createEmployee(EmployeeDto employeeDto, Model model) {
        try {
            employeeService.createEmployee(employeeDto);
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "new-employee";
        }
        return "redirect:/";
    }


    /**
     * Форма просмотра данных сотрудника.
     *
     * @return - адрес UI-страницы, на который выполняем переход
     */
    @GetMapping("/ui/employee/{login}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getEmployeeInfo(@PathVariable("login") String login, Model model) {

        EmployeeDto employeeDto = employeeService.findEmployeeByLogin(login);
        model.addAttribute("employee", employeeDto);
        model.addAttribute("allRoles", EmployeeService.ALL_ROLES);
        return "employee";
    }

    /**
     * Обработать изменение данных сотрудника.
     *
     * @param employeeDto - данные сотрудника
     * @param model       - модель
     * @return - адрес UI-страницы, на который выполняем переход
     */
    @PostMapping("/ui/employee/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String updateEmployee(EmployeeDto employeeDto, Model model) {
        try {
            employeeService.updateEmployee(employeeDto);
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "employee";
        }
        return "redirect:/";
    }

    /**
     * Обработать увольнение сотрудника.
     *
     * @param login - логин сотрудника
     * @param model - модель
     * @return - адрес UI-страницы, на который выполняем переход
     */
    @PostMapping("/ui/employee/delete/{login}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable("login") String login, Model model) {
        try {
            employeeService.deleteByLogin(login);
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "employee";
        }
        return "redirect:/";
    }
}
