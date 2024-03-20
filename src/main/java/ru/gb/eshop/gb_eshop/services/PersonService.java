package ru.gb.eshop.gb_eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.enums.Role;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис пользователей
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PersonService {

    /**
     * Поле personRepository
     */
    private final PersonRepository personRepository;

    /**
     * Поле passwordEncoder
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод проверяет занят ли логин
     *
     * @param person пользователь
     */
    public Person findByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    /**
     * Метод регистрации пользователя
     *
     * @param person новый пользователь
     */
    @Transactional
    public Person register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_USER);
        return personRepository.save(person);
    }

    /**
     * Метод возвращает всех пользователей
     *
     * @return список всех пользователей
     */
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Находит пользователя по id
     *
     * @param id id пользователя
     * @return найденный пользователь
     */
    public Person getPersonById(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    /**
     * Обновление пользователя
     *
     * @param id     id пользователя, которого нужно обновить
     * @param person пользователь с обновленными данными
     */
    @Transactional
    public Person updatePersons(int id, Person person) {
        person.setId(id);
        return personRepository.save(person);
    }

    /**
     * Удаление пользователя
     *
     * @param id id пользователя
     */
    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    /**
     * Метод смены пароля (в разработке)
     *
     * @param id       id пользователя
     * @param password пароль
     */
    @Transactional
    public void changePassword(int id, String password) {
        personRepository.changePasswordPersonById(id, passwordEncoder.encode(password));
    }

}