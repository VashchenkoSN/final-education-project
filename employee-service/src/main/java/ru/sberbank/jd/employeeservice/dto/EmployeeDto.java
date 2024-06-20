package ru.sberbank.jd.employeeservice.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberbank.jd.employeeservice.entity.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Set<Role> roles;

    private UUID department_id;
}
