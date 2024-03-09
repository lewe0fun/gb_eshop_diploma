package ru.gb.eshop.gb_eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер аутентификации
 */

@Controller
public class LoginController {

    /**
     * Метод получение страницы логина
     *
     * @return представление страницы логина
     */
    @GetMapping("/login")
    public String auth() {
        return "login";
    }
}
