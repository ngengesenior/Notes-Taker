package com.ngenge.apps.notestaker.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ngenge.apps.notestaker.models.Note;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private final NotesRepository notesRepository;
    private LiveData<List<Note>> allNotes;
    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        allNotes = notesRepository.getAllNotes();
    }


    public void insertNote(Note note) {
        notesRepository.insertNote(note);
    }

    public void removeNote(int id) {
        notesRepository.removeNote(id);
    }

    public void removeAllNotes() {
        notesRepository.removeAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    public void insertManyNotes(List<Note> notes) {
        notesRepository.insertMany(notes);
    }
}
