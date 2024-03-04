package ru.gb.eshop.gb_eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.PersonRepository;
import ru.gb.eshop.gb_eshop.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;


    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByLogin(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
}
