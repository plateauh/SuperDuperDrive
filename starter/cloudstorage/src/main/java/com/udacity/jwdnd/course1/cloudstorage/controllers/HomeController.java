package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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
    /*
    The controllers you write should also be responsible for determining what, if any, error messages the application displays to the user.
     */

    private final NoteService noteService;
    public final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping
    public String homeView(Authentication authentication, @ModelAttribute("noteForm") NoteModel noteModel, Model model) {
        logger.debug("authentication value {}", authentication.getName());
        List<Notes> notes = noteService.getUserNotes(authentication.getName());
        logger.debug("notes list {}", notes);
        model.addAttribute("notes", notes);
        return "home";
    }

}
