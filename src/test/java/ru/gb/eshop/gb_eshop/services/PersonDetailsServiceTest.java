package ru.gb.eshop.gb_eshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.PersonRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonDetailsServiceTest {
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonDetailsService personDetailsService;

    @BeforeEach
    public void setUp() {
        PersonDetailsService personDetailsService = new PersonDetailsService();
    }

    @Test
    void loadUserByUsername() {
        MockitoAnnotations.initMocks(this);
        //Arrange
        final String login = "existingUserName";
        final Person person = mock(Person.class);
        when(personRepository.findByLogin(login)).thenReturn(Optional.ofNullable(person));

        //Act
        final UserDetails userDetails = personDetailsService.loadUserByUsername(login);

        //Assert
        assertNotNull(userDetails);
       // assertEquals(person, ReflectionTestUtils.getField(userDetails, "person"));
    }

}