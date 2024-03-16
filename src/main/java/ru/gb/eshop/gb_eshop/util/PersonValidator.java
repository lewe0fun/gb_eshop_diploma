package ru.gb.eshop.gb_eshop.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.services.PersonService;

/**
 * Клас вспомогательный, для проверки отсутствующих/повторяющихся сущностей Person
 */

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.findByLogin(person) != null) {
            errors.rejectValue("login", "", "Логин занят!!!");
        }
        if(((Person) target).getPassword().isEmpty()){
            errors.rejectValue("password", "", "Пароль не может быть пустым!!!");
        }
    }
}
