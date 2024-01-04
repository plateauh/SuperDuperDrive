package com.udacity.jwdnd.course1.cloudstorage.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Data
@AllArgsConstructor
public class Users {
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;
}
