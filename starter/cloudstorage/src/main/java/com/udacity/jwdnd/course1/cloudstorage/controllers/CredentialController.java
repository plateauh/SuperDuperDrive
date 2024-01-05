package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Najed Alseghair at 1/4/2024
 */
@Controller
@RequiredArgsConstructor
public class CredentialController {
    private final CredentialService credentialService;
    public final Logger logger = LoggerFactory.getLogger(CredentialController.class);
    @PostMapping("/addCredential")
    public String addCredential(Authentication authentication, @ModelAttribute("credentialsForm") CredentialModel credentialModel, Model model) {
        try {
            credentialService.addCredential(authentication.getName(), credentialModel);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/deleteCredential")
    public String removeCredential(@RequestParam("credentialId") Integer credentialId, Model model) {
        try {
            credentialService.deleteCredential(credentialId);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }
}
