package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    public final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping(value = "/addFile", consumes = {"multipart/form-data"})
    public String addFile(Authentication authentication, @RequestPart("fileUpload") MultipartFile file, Model model) {
        try {
            fileService.addFile(file, authentication.getName());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam("fileId") Integer fileId, Model model) {
        try {
            fileService.deleteFile(fileId);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> getFile(@RequestParam("fileId") Integer fileId) {
        Files file = fileService.getFile(fileId);
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
        return ResponseEntity.ok().headers(this.headers(file.getFilename()))
                .contentLength(file.getFilesize())
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .body(resource);
    }

    private HttpHeaders headers(String name) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + name);
        header.add("Cache-Control", "no-cache, no-store,"
                + " must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;
    }
}
