package ru.sberbank.jd.tgbot.exchange;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sberbank.jd.employeeservice.dto.EmployeeDto;
import ru.sberbank.jd.tgbot.config.FeignClientConfig;


/**
 * Клиент для сервиса employee-service.
 */
@FeignClient(name = "employee-service-client", url = "${feign.employee-service.url}", configuration = FeignClientConfig.class)
public interface EmployeeServiceClient {

    @GetMapping("/employee/{login}")
    EmployeeDto getEmployeeByLogin(@PathVariable("login") String login);

    @GetMapping("employees")
    List<EmployeeDto> getEmployeeList();

}
