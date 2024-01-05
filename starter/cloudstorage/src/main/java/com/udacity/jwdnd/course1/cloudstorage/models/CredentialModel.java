package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Najed Alseghair at 1/4/2024
 */
@Data
@AllArgsConstructor
public class CredentialModel {
    private Integer id;
    private String url;
    private String username;
    private String password;
    private String decryptedPassword;
}
