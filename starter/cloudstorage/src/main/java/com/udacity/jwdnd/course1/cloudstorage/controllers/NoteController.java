package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Najed Alseghair at 1/3/2024
 */
@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    @PostMapping("/addNote")
    public String addNote(Authentication authentication, @ModelAttribute("noteForm") NoteModel noteForm, Model model) {
        try {
            noteService.addNote(noteForm, authentication.getName());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/deleteNote")
    public String removeNote(@RequestParam("noteId") Integer noteId, Model model) {
        try {
            noteService.deleteNote(noteId);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

}
