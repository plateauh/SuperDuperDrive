package com.udacity.jwdnd.course1.cloudstorage.persistence.mappers;

import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Najed Alseghair at 1/3/2024
 */
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    int insertNote(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNote(Integer noteid);

    @Select("SELECT count(*) FROM NOTES WHERE notetitle = #{noteTitle} AND notedescription = #{noteDescription} AND userid = #{userid}")
    int countNotes(String noteTitle, String noteDescription, Integer userid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    void updateNote(Notes note);
}
