package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @PostMapping(value = "/addFile", consumes = {"multipart/form-data"})
    public String addFile(Authentication authentication, @RequestPart("fileUpload") MultipartFile file, Model model) {
        try {
            fileService.addFile(file, authentication.getName());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }
}
