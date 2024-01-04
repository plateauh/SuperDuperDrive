package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Najed Alseghair at 1/3/2024
 */
@Data
@AllArgsConstructor
public class NoteModel {
    private Integer id;
    private String title;
    private String description;
}
