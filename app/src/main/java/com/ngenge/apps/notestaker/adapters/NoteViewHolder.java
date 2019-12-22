package com.ngenge.apps.notestaker.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngenge.apps.notestaker.R;
import com.ngenge.apps.notestaker.models.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView descriptionTextView;
    public RelativeLayout titleHeader;
    public ImageView shareImageView;
    public ImageView deleteImageView;
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titletextView);
        descriptionTextView = itemView.findViewById(R.id.bodyTextView);
        titleHeader = itemView.findViewById(R.id.titleHeader);
        shareImageView = itemView.findViewById(R.id.imageViewShare);
        deleteImageView = itemView.findViewById(R.id.imageViewDelete);
    }

    public void bind(Note note,String colorCode) {
        titleTextView.setText(note.getTitle());
        descriptionTextView.setText(note.getDescription());
        titleHeader.setBackgroundColor(Color.parseColor(colorCode));

    }


}
