package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Data
@AllArgsConstructor
public class FileModel {
    private Integer id;
    private String name;
    private byte[] data;
}
