package ru.sberbank.jd.employeeservice.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Сущность роль.
 */
@RequiredArgsConstructor
public enum Role implements GrantedAuthority
{
    ROLE_EMPLOYEE,
    ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
