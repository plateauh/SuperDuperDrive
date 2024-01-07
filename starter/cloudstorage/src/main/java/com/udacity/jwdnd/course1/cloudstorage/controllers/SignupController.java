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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Controller
@RequestMapping
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;
    @GetMapping("/signup")
    public String signupView(@ModelAttribute("userForm") Users user) {
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") Users user, Model model, HttpServletRequest request) {
        try {
            userService.signup(user);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("signupSuccess", true);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("signupError", e.getMessage());
            return "signup";
        }
    }

}
