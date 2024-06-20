package ru.sberbank.jd.employeeservice.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


/**
 * Сущность Сотрудник.
 */
@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {
    @Id
    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="employee_role", joinColumns = @JoinColumn(name="employee_login") )
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private Department department;
}
