package com.udacity.jwdnd.course1.cloudstorage.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private byte[] filedata;

    public Files(String filename, String contenttype, Long filesize, Integer userid, byte[] filedata) {
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }
}
