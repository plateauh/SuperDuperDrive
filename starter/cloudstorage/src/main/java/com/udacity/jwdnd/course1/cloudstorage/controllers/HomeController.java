package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Najed Alseghair at 1/3/2024
 */
@Controller
@RequestMapping(value = "/home")
@RequiredArgsConstructor
public class HomeController {

    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    public final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping
    public String homeView(Authentication authentication,
                           @ModelAttribute("noteForm") NoteModel noteModel,
                           @ModelAttribute("credentialsForm") CredentialModel credentialModel,
                           @ModelAttribute("filesFrom") FileModel fileModel,
                           Model model) {
        List<Notes> notes = noteService.getUserNotes(authentication.getName());
        model.addAttribute("notes", notes);

        List<CredentialModel> credentials = credentialService.getUserCredentials(authentication.getName());
        model.addAttribute("cloudCredentials", credentials);

        List<FileModel> files = fileService.getUserFiles(authentication.getName());
        model.addAttribute("files", files);
        return "home";
    }

}
