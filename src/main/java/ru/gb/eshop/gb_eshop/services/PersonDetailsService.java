package ru.gb.eshop.gb_eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.PersonRepository;


import java.util.Optional;

/**
 * Сервис информации о пользователе
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;


    /**
     * Метод loadUserByUsername
     *
     * @param username логин пользователя
     * @return объект UserDetail, который также является интерфейсом и содержит
     * некоторые методы для описания информации о пользователе
     * @throws UsernameNotFoundException исключение если пользователь отсутствует
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByLogin(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с именем " + username + " не найден");
        }
        return person.get();
    }
}
