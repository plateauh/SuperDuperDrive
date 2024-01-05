package com.udacity.jwdnd.course1.cloudstorage.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Najed Alseghair at 1/4/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    /**
     * credentialid INT PRIMARY KEY auto_increment,
     *     url VARCHAR(100),
     *     username VARCHAR (30),
     *     key VARCHAR,
     *     password VARCHAR,
     *     userid INT
     */
    private Integer credentialid;
    private String url;
    private String username;
    private String password;
    private String key;
    private Integer userid;
}
