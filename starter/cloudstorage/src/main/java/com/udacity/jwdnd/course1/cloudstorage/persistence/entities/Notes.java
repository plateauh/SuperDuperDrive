package com.udacity.jwdnd.course1.cloudstorage.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Najed Alseghair at 1/3/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;
}
