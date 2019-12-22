package com.ngenge.apps.notestaker.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ngenge.apps.notestaker.models.Note;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesRepository {
    private NotesDao notesDao;
    private LiveData<List<Note>> allNotes;

    public NotesRepository(Application application) {
        NotesDatabase db = NotesDatabase.getDatabase(application);
        notesDao = db.notesDao();
        allNotes = notesDao.getNotesByDate();
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insertNote(Note note) {
        new InsertTask(notesDao)
                .execute(note);
    }

    public void removeAllNotes() {
        new RemoveAllTasks(notesDao)
                .execute();
    }

    public void removeNote(int id) {

        new RemoveTask(notesDao)
                .execute(id);
    }

    public List<Note> allTasks() {
        try {
            return new AllTasks(notesDao)
                    .execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertMany(List<Note> notes) {
       new InsertManyNotesTask(notesDao)
                .execute(notes);
    }







    private static class InsertTask extends AsyncTask<Note, Void, Void> {

        private NotesDao asyncTaskDao;

        InsertTask(NotesDao movieDao) {
            asyncTaskDao = movieDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            asyncTaskDao.insertNote(notes[0]);
            return null;
        }
    }


    private static class InsertManyNotesTask extends AsyncTask<List<Note>,Void,Boolean> {

        private NotesDao asyncTaskDao;

        InsertManyNotesTask(NotesDao notesDao) {
            asyncTaskDao = notesDao;
        }
        @Override
        protected Boolean doInBackground(List<Note>... lists) {
            try {
                asyncTaskDao.insertManyNotes(lists[0]);
                return true;
            } catch (Exception e) {
                return false;
            }

        }
    }




    private static class RemoveTask extends AsyncTask<Integer,Void,Void> {

        private NotesDao asyncTaskDao;

        RemoveTask(NotesDao notesDao) {
            asyncTaskDao = notesDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            asyncTaskDao.removeNote(integers[0]);
            return null;
        }
    }

    private static class RemoveAllTasks extends AsyncTask<Void,Void,Void> {

        private NotesDao asyncTaskDao;

        RemoveAllTasks(NotesDao notesDao) {
            asyncTaskDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.removeAllNotes();
            return null;
        }
    }

    private static class AllTasks extends AsyncTask<Void,Void,List<Note>> {

        private NotesDao asyncTaskDao;
        AllTasks(NotesDao notesDao) {
            asyncTaskDao = notesDao;
        }
        @Override
        protected List<Note> doInBackground(Void... voids) {
            return asyncTaskDao.getNotesByDate().getValue();
        }
    }
}
