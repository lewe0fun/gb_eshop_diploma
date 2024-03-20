package ru.gb.eshop.gb_eshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private PasswordEncoder passwordEncoder;
    private Person person;

    @BeforeEach
    void setUp() {
        personRepository = Mockito.mock(PersonRepository.class);
        personService = new PersonService(personRepository, passwordEncoder);
        String password = "password";
        person = new Person();
        person.setLogin("login");
        person.setId(1);
        person.setEmail("user@gmail.com");
        person.setFirstName("user");
        person.setPassword(password);
    }

    @Test
    void findByLogin() {
        //установка
        given(personRepository.findByLogin(person.getLogin())).willReturn(Optional.ofNullable(person));

        //условие
        Person pers = personService.findByLogin(person);

        //проверка
        assertThat(pers).isNotNull();
        assertThat(pers.getLogin()).isEqualTo("login");
    }

    @Test
    void getAllPerson() {
        //установка
        Person person1 = new Person();
        given(personRepository.findAll()).willReturn(List.of(person, person1));

        //условие
        List<Person> persons = personRepository.findAll();

        //проверка
        assertThat(persons).isNotNull();
        assertThat(persons.size()).isEqualTo(2);
    }

    @Test
    void getPersonById() {
        //установка
        given(personRepository.findById(1)).willReturn(Optional.of(person));

        //условие
        Person person1 = personService.getPersonById(person.getId());

        //проверка
        assertThat(person1).isNotNull();
    }

    @Test
    void updatePerson() {
        //установка
        int id = 1;
        given(personRepository.save(person)).willReturn(person);

        //условие
        Person updatedEmployee = personService.updatePersons(id, person);

        //проверка
        assertThat(updatedEmployee.getEmail()).isEqualTo("user@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("user");
    }

    @Test
    void deletePerson() {
        //установка
        int id = 1;
        willDoNothing().given(personRepository).deleteById(id);

        //условие
        personService.deletePerson(person.getId());

        //проверка
        verify(personRepository, times(1)).deleteById(id);
    }

}