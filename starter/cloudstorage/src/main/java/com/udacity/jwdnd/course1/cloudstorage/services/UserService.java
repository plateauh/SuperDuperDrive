package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;
    public final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void signup(Users user) {
        if (getUserExists(user.getUsername())) {
            logger.error("User already exists");
            throw new IllegalArgumentException("User already exists");
        }
        String salt = generateSalt();
        user.setSalt(salt);
        user.setPassword(hashPassword(user.getPassword(), salt));
        int rowsAdded = userMapper.insertUser(user);
        if (rowsAdded < 1) {
            logger.error("there is an error signing the user up");
            throw new RuntimeException("There is an error signing the user up, please try again later");
        }
        logger.info("signup successful");
    }

    private boolean getUserExists(String username) {
        return userMapper.getUser(username) != null;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String plainPassword, String salt) {
        return hashService.getHashedValue(plainPassword, salt);
    }

}
