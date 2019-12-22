package com.ngenge.apps.notestaker.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ngenge.apps.notestaker.models.Note;

@Database(entities = {Note.class},exportSchema = false,version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase sInstance;
    public abstract NotesDao notesDao();

    static NotesDatabase getDatabase(final Context context) {

        if (sInstance == null) {
            synchronized (NotesDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class, "notes_database")
                            .build();

                }
            }
        }
        return sInstance;
    }


}
