package ru.sberbank.jd.employeeservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
     * Тест эндпойнта поиска сотрудника по логину.
     */
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findEmployeeByLogin() throws Exception {
        EmployeeDto employeeDto = DataHelper.createEmployeeDto();
        Mockito.when(employeeService.findEmployeeByLogin("test_login")).thenReturn(employeeDto);

        mockMvc.perform(get("/employee/test_login"))
                .andExpect(status().isOk());
    }

    /**
     * Тест эндпойнта создания сотрудника с ролью ROLE_ADMIN- успешно.
     */
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createEmployee_200OK() throws Exception {

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n"
                                + "  \"login\" : \"petrovSV\",\n"
                                + "  \"password\": \"init\",\n"
                                + "  \"firstName\": \"Egor\",\n"
                                + "  \"lastName\": \"Petrov\",\n"
                                + "  \"roles\": [\"ROLE_EMPLOYEE\", \"ROLE_ADMIN\"]\n"
                                + "}"))
                .andExpect(status().isOk());
    }

    /**
     * Тест эндпойнта обновления данных сотрудника с ролью ROLE_ADMIN- успешно.
     */
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateEmployee_200OK() throws Exception {

        mockMvc.perform(put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n"
                                + "  \"login\" : \"petrovSV\",\n"
                                + "  \"password\": \"init\",\n"
                                + "  \"firstName\": \"Egor\",\n"
                                + "  \"lastName\": \"Petrov\",\n"
                                + "  \"roles\": [\"ROLE_EMPLOYEE\", \"ROLE_ADMIN\"]\n"
                                + "}"))
                .andExpect(status().isOk());
    }

    /**
     * Тест эндпойнта увольнения сотрудника с ролью ROLE_ADMIN- успешно.
     */
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteEmployee_200OK() throws Exception {

        mockMvc.perform(delete("/employee/test_login"))
                .andExpect(status().isOk());
    }
}