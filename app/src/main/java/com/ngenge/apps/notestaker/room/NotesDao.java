package com.ngenge.apps.notestaker.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ngenge.apps.notestaker.models.Note;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Query("DELETE  FROM notes WHERE id = :id")
    void removeNote(int id);

    @Query("SELECT * FROM notes ORDER BY dateTime ASC")
    LiveData<List<Note>> getNotesByDate();

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    Note getNoteById(int id);

    @Query("DELETE FROM notes")
    void removeAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertManyNotes(List<Note> notes);

}
