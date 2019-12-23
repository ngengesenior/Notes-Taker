package com.ngenge.apps.notestaker;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ngenge.apps.notestaker.models.Note;
import com.ngenge.apps.notestaker.room.NotesViewModel;
import com.ngenge.apps.notestaker.widget.NoteWidget;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewNoteFragment extends Fragment {

    private TextInputEditText noteTitleEditText;
    private TextInputEditText noteDescriptionEditText;
    private FloatingActionButton floatingActionButtonDone;
    private NotesViewModel notesViewModel;


    public NewNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        View view =  inflater.inflate(R.layout.fragment_new_note, container, false);
        findViews(view);

        Intent intent = new Intent(getContext(), NoteWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(),NoteWidget.class));

        floatingActionButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitleEditText.getText().toString().trim();
                String description = noteDescriptionEditText.getText().toString().trim();
                long timestamp = new Date().getTime();
                if (!title.isEmpty()) {
                    Note note = new Note(title,description,timestamp);
                    notesViewModel.insertNote(note);

                    //Update the widget

                    NoteWidget.updateAppWidget(getContext(),appWidgetManager,appWidgetIds,note);

                    Navigation.findNavController(view).navigate(R.id.nav_note_list);
                } else {
                    Toast.makeText(view.getContext(),"Note title cannot be empty",Toast.LENGTH_LONG)
                            .show();
                    return;
                }
            }
        });

        return view;
    }

    private void addNote() {

    }

    private void findViews(View view) {
        noteTitleEditText = view.findViewById(R.id.editTextTitle);
        noteDescriptionEditText = view.findViewById(R.id.editTextDescription);
        floatingActionButtonDone = view.findViewById(R.id.fabDone);
    }

}
