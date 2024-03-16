package ru.gb.eshop.gb_eshop.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.eshop.gb_eshop.models.Category;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.services.PersonService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Клас вспомогательный, для проверки отсутствующих/повторяющихся сущностей Person
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Component
public class PersonValidator implements Validator {

    /**
     * Поле personService
     */
    private final PersonService personService;

    /**
     * Конструктор - создание нового объекта
     *
     * @see PersonValidator#PersonValidator(PersonService)
     * @param personService - сервис пользователя
     */
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Метод отсеивает классы на обрабатываемые и не обрабатываемые
     *
     * @param clazz класс
     * @return boolean значение
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    /**
     * Метод валидности объекта
     *
     * @param target объект
     * @param errors детали об ошибках
     */
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
