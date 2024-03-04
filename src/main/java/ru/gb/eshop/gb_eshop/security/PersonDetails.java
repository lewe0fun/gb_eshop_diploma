package ru.gb.eshop.gb_eshop.security;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.eshop.gb_eshop.models.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    public Person getPerson(){
        return this.person;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getLogin();
    }

    // Метод проверяет то, что аккаунт действителен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Метод проверяет то, что аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Метод проверяет то, что пароль является действительным
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Метод проверяет то, что аккаунт активен
    @Override
    public boolean isEnabled() {
        return true;
    }
}