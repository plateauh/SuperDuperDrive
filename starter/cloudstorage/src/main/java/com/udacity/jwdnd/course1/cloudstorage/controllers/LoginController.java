package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String loginView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean signupSuccess = session.getAttribute("signupSuccess") != null && (boolean) session.getAttribute("signupSuccess");
        model.addAttribute("signupSuccess", signupSuccess);
        return "login";
    }
}
