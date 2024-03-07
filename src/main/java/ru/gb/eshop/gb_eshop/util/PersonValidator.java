package ru.gb.eshop.gb_eshop.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.services.PersonService;

/**
 * Клас вспомогательный, для проверки отсутствующих/повторяющихся сущностей
 */

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    // В данно методе указываем для какой модели предназначен данный валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // Если метод по поиску пользователя по логину не равен 0 тогда такой логин уже занят
        if(personService.findByLogin(person) != null){
            errors.rejectValue("login", "", "Логин занят");
        }
    }

    public void findUser(Object target, Errors errors) {
        Person person = (Person) target;
        // Если метод по поиску пользователя по логину равен 0 тогда такой логин не найден
        if(personService.findByLogin(person) == null){
            errors.rejectValue("login", "", "Пользователь c таким логином не найден");
        }
    }
}
