package com.ngenge.apps.notestaker.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public  class Utils {


    private static ArrayList<Note> notes;

    public static List<String> getColorCodes() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#FF5252");
        colors.add("#795548");
        colors.add("#FF5722");
        colors.add("#FF9800");
        colors.add("#9E9E9E");
        colors.add("#7C4DFF");

        return colors;
    }

    public static List<Note> getNotes() {
        notes = new ArrayList<>();
        for (int i=0;i<15;i++) {
            Note note = new Note("Title "+ i,"This is the description for the note at the index "+ i + " and it is valid",new Random().nextLong());
            notes.add(note);
        }
        return notes;
    }

    public static void logMessage(String message) {
        Log.d("LOGGING--",message);
    }
}
