package ru.sberbank.jd.employeeservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.sberbank.jd.employeeservice.DataHelper;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.employeeservice.service.EmployeeService;


/**
 * Тесты для EmployeeRestController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeRestControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Тест контроллера поиска сотрудника по логину.
     */
    @Test
    void findEmployeeByLogin() throws Exception {
        EmployeeDto employeeDto = DataHelper.createEmployeeDto();
        Mockito.when(employeeService.findEmployeeByLogin("test_login")).thenReturn(employeeDto);

        mockMvc.perform(get("/employee/test_login"))
                .andExpect(status().isOk());

    }

    /**
     * Тест создания сотрудника
     */
    @Test
    void createEmployee() {
    }
}