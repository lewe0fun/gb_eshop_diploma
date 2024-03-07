package ru.gb.eshop.gb_eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер аутентификации
 */
@Controller
public class AuthController {
    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }
}
