package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.persistence.entities.Users;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Najed Alseghair at 1/1/2024
 */
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;
    public final Logger logger = LoggerFactory.getLogger(NoteService.class);

    public void addNote(NoteModel noteModel, String username) {
        Users user = userMapper.getUser(username);
        Notes note = new Notes(noteModel.getId(), noteModel.getTitle(), noteModel.getDescription(), user.getUserid());
        if (note.getNoteid() != null) {
            noteMapper.updateNote(note);
            logger.info("Note updated:");
        } else {
            int notesAdded = noteMapper.insertNote(note);
            if (notesAdded > 0) {
                logger.info("Note added: {}", notesAdded);
            }
            else {
                logger.error("Error in adding note");
                throw new RuntimeException("Error in adding note");
            }
        }
    }

    public List<Notes> getUserNotes(String username) {
        Users user = userMapper.getUser(username);
        logger.info("getting user {} notes...", username);
        logger.debug("user.getUserid() {}", user.getUserid());
        return noteMapper.getNotes(user.getUserid());
    }

    public void deleteNote(Integer noteId) {
        int deletedId = noteMapper.deleteNote(noteId);
        if (deletedId > 0)
            logger.info("note {} deleted", noteId);
        else {
            logger.error("error in deleting note {}", noteId);
            throw new RuntimeException("Error in deleting note");
        }
    }

}
