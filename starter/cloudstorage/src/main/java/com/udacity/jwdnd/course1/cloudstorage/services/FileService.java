package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Files;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;
    public final Logger logger = LoggerFactory.getLogger(FileService.class);

    public void addFile(MultipartFile multipartFile, String username) {
        Files file;
        try {
            file = mapFile(multipartFile, username);
        } catch (IOException e) {
            throw new RuntimeException("Error in mapping file");
        }
        int fileCount = fileMapper.countFileByFilenameAndUserId(file.getFilename(), file.getUserid());
        if (fileCount > 0) {
            logger.error("File with the same name is already uploaded");
            throw new RuntimeException("File with the same name is already uploaded");
        }
        int filesAdded = fileMapper.addFile(file);
        if (filesAdded <= 0) {
            logger.error("Error in storing file");
            throw new RuntimeException("Error in storing file");
        }
        logger.info("File added");
    }

    public List<FileModel> getUserFiles(String username) {
        Users user = userMapper.getUser(username);
        logger.info("getting user {} files...", username);
        List<Files> files = fileMapper.getFiles(user.getUserid());
        List<FileModel> fileModels = new ArrayList<>();
        files.forEach(file -> fileModels.add(new FileModel(file.getFilename(), file.getFiledata())));
        return fileModels;
    }

    private Files mapFile(MultipartFile fileModel, String username) throws IOException {
        Users user = userMapper.getUser(username);
        return new Files(fileModel.getOriginalFilename(),
                fileModel.getContentType(),
                String.valueOf(fileModel.getSize()),
                user.getUserid(),
                fileModel.getBytes());
    }
}
