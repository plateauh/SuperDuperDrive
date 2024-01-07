package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Service
@RequiredArgsConstructor
public class CredentialService {
    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;
    public final Logger logger = LoggerFactory.getLogger(CredentialService.class);
    public void addCredential(String username, CredentialModel model) {
        Credentials credential = mapCredentials(model, username);
        if (credential.getCredentialid() != null) {
            credentialMapper.updateCredential(credential);
            logger.info("Credential {} updated", model.getId());
        } else {
            int credentialCount = credentialMapper.countCredentialByUsernameAndUserId(credential.getUsername(), credential.getUserid());
            if (credentialCount > 0) {
                logger.error("There is already a credential with the username");
                throw new RuntimeException("There is already a credential with the username");
            }
            int insertedCredential = credentialMapper.insertCredential(credential);
            if (insertedCredential <= 0) {
                logger.error("Error in adding Credential");
                throw new RuntimeException("Error in adding Credential");
            }
            logger.info("Credential added: {}", insertedCredential);
        }
    }

    public List<CredentialModel> getUserCredentials(String username) {
        Users user = userMapper.getUser(username);
        logger.info("getting user {} Credential...", username);
        logger.debug("user.getUserid() {}", user.getUserid());
        List<Credentials> credentials = credentialMapper.getCredentials(user.getUserid());
        List<CredentialModel> credentialModels = new ArrayList<>();
        credentials.forEach(credential ->
                credentialModels.add(
                        new CredentialModel(
                                credential.getCredentialid(),
                                credential.getUrl(),
                                credential.getUsername(),
                                credential.getPassword(),
                                encryptionService.decryptValue(credential.getPassword(), credential.getKey())
                                )));
        return credentialModels;
    }

    public void deleteCredential(Integer credentialId) {
        int deletedId = credentialMapper.deleteCredential(credentialId);
        if (deletedId <= 0) {
            logger.error("error in deleting Credential {}", credentialId);
            throw new RuntimeException("Error in deleting Credential");
        }
        logger.info("Credential {} deleted", credentialId);
    }

    private Credentials mapCredentials(CredentialModel model, String username) {
        // taken from https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/UrlValidator.html
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(model.getUrl())) {
            logger.error("Invalid URL: {}", model.getUrl());
            throw new RuntimeException("Invalid URL: " + model.getUrl());
        }
        Users user = userMapper.getUser(username);
        String password = encryptionService.encryptValue(model.getPassword(), "najdmohammedalse");
        return new Credentials(model.getId(), model.getUrl(), model.getUsername(), password, "najdmohammedalse", user.getUserid());
    }
}
