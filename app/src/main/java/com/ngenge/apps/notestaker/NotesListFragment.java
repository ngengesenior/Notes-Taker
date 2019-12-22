package com.ngenge.apps.notestaker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ngenge.apps.notestaker.adapters.NotesListAdapter;
import com.ngenge.apps.notestaker.models.Note;
import com.ngenge.apps.notestaker.room.NotesViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends Fragment {

    private NotesViewModel notesViewModel;
    private Observer<List<Note>> notesObserver;
    private NotesListAdapter notesListAdapter;
    private RecyclerView notesList;
    private FloatingActionButton fabAddNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public NotesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        fabAddNote = view.findViewById(R.id.fabAddNote);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        notesList = view.findViewById(R.id.notesList);
        notesListAdapter = new NotesListAdapter(new ArrayList<>(), note -> {
            NavDirections action = NotesListFragmentDirections.actionNavNoteListToDetailsFragment(note);
            Navigation.findNavController(fabAddNote).navigate(action);

        }, id -> notesViewModel.removeNote(id));
        notesList.setAdapter(notesListAdapter);

        notesObserver = notes -> notesListAdapter.setNoteList(notes);
        notesViewModel.getAllNotes().observe(this,notesObserver);



        fabAddNote.setOnClickListener(v -> {
            NavDirections action = NotesListFragmentDirections.newNoteAction();
            Navigation.findNavController(fabAddNote).navigate(action);
        });
        return view;
    }


}
