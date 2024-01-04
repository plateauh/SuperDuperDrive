package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    /*
        The controllers you write should also be responsible for determining what, if any, error messages the application displays to the user.
         */
    @GetMapping
    public String loginView() {
        return "login";
    }
}
