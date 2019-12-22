package com.ngenge.apps.notestaker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngenge.apps.notestaker.models.Note;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleTextView = view.findViewById(R.id.textViewTitle);
        TextView descriptionTextView = view.findViewById(R.id.textViewBody);
        TextView dateTextView = view.findViewById(R.id.textViewDate);

        Note note = DetailsFragmentArgs.fromBundle(getArguments()).getNote();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTextView.setText(simpleDateFormat.format(note.getDateTime()));
        titleTextView.setText(note.getTitle());
        descriptionTextView.setText(note.getDescription());

    }
}
