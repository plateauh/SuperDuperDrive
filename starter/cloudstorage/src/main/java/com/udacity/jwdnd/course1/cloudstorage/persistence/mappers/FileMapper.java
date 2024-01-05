package com.udacity.jwdnd.course1.cloudstorage.persistence.mappers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Najed Alseghair at 1/5/2024
 */
@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> getFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid} AND userid = #{userid}")
    Files getFileByIdAndUserId(Integer fileId, Integer userid);

    @Select("SELECT count(fileid) FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    int countFileByFilenameAndUserId(String filename, Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    int addFile(Files file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
