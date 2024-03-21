package ru.gb.eshop.gb_eshop.repositories;

import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.eshop.gb_eshop.models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.gb.eshop.gb_eshop.enums.Role.ROLE_USER;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
class PersonRepositoryTest {

    @Mock
    private PersonRepository personRepository;

    @Test
    public void personRepo_Save_ReturnSavedPerson() {
        Person person = new Person();
        person.setId(1);
        person.setRole(ROLE_USER);
        person.setAddress("Address");

        personRepository.save(person);

        assertNotNull(person);
        assertEquals(person.getId(), 1);
        assertEquals(person.getRole(), ROLE_USER);
        assertEquals(person.getAddress(), "Address");
    }
}