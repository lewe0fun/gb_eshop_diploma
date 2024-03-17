package ru.gb.eshop.gb_eshop.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.eshop.gb_eshop.enums.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Класс сущности пользователь со свойствами id, login, password, firstName,
 * lastName, telephone, email, address, role, productList, orderList
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Person")
public class Person implements UserDetails {

    /**
     * Поле id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Поле login
     */
    @NotEmpty(message = "Логин не должен быть пустым!")
    @Size(min = 3, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    /**
     * Поле password
     */
    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Column(name = "password")
    private String password;

    /**
     * Поле firstName
     */
    @Column(name = "first_name")
    @Pattern(regexp = "^$|^([А-Я][а-яё]{0,23}|[A-Z][a-z]{0,23})$", message = "Имя должно быть 1-23 символов,\nс заглавной буквы,\nна одном языке")
    private String firstName;

    /**
     * Поле lastName
     */
    @Column(name = "last_name")
    @Pattern(regexp = "^$|^([А-Я][а-яё]{0,23}|[A-Z][a-z]{0,23})$", message = "Фамилия должна быть 1-23 символов,\nс заглавной буквы,\nна одном языке")
    private String lastName;

    /**
     * Поле telephone
     */
    @Column(name = "telephone")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Неправильный телефон!\nТелефонный номер состоит из 10 цифр.")
    private String telephone;

    /**
     * Поле email
     */
    @Email(message = "Неправильный email!")
    @Column(name = "email")
    private String email;

    /**
     * Поле address
     */
    @Column(name = "address")
    private String address;

    /**
     * Поле role
     */
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Поле productList
     */
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "person_id"),inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    /**
     * Поле orderList
     */
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Order> orderList;

    /**
     * Переопределенный метод equals
     *
     * @param o - объект
     * @return возвращает boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(login, person.login) && Objects.equals(password, person.password);
    }

    /**
     * Переопределенный метод hashCode
     *
     * @return генерирует целочисленный код экземпляра класса
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    /**
     * Переопределенный метод getAuthorities
     *
     * @return возвращает информацию о полномочиях, установленную пользователем.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    /**
     * Переопределенный метод getUsername
     *
     * @return возвращает логин
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Переопределенный метод isAccountNonExpired
     *
     * @return возвращает boolean значение
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Переопределенный метод isAccountNonLocked
     *
     * @return возвращает boolean значение
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Переопределенный метод isCredentialsNonExpired
     *
     * @return возвращает boolean значение
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Переопределенный метод isEnabled
     *
     * @return возвращает boolean значение
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
