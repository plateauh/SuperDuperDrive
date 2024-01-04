package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Controller
@RequestMapping(value = "/signup")
@RequiredArgsConstructor
public class SignupController {
    /*
    The controllers you write should also be responsible for determining what, if any, error messages the application displays to the user.
     */

    private final UserService userService;
    @GetMapping
    public String signupView() {
        return "signup";
    }
    @PostMapping
    public String signup(@ModelAttribute Users user, Model model) {
        try {
            userService.signup(user);
            model.addAttribute("signupSuccess", true);
        } catch (RuntimeException e) {
            model.addAttribute("signupError", e.getMessage());
        }
        return "signup";
    }

}
