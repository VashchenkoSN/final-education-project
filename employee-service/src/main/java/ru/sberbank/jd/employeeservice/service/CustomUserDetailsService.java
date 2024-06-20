package ru.sberbank.jd.employeeservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.sberbank.jd.employeeservice.entity.Employee;
import ru.sberbank.jd.employeeservice.entity.Role;
import ru.sberbank.jd.employeeservice.repository.EmployeeRepository;

/**
 * CustomserDetailService.
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    /**
     * Вернуть пользователя по логину.
     *
     * @param login - логин
     * @return - объект User
     * @throws UsernameNotFoundException - исключение - пользователь не найден
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) {
        Employee employee = employeeRepository
                .findById(login).orElseThrow(NotFoundException::new);

        return new User(
                employee.getLogin(),
                employee.getPassword(),
                buildSimpleGrantedAuthorities(employee.getRoles()));
    }

    private Collection<? extends GrantedAuthority> buildSimpleGrantedAuthorities(Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }
}
