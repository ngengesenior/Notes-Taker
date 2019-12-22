package com.ngenge.apps.notestaker.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngenge.apps.notestaker.R;
import com.ngenge.apps.notestaker.models.Note;
import com.ngenge.apps.notestaker.models.Utils;

import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> noteList;
    private OnNoteListener onNoteListener;
    private OnNoteDeleteListener onNoteDeleteListener;





    public NotesListAdapter(List<Note> notes,OnNoteListener onNoteListener,OnNoteDeleteListener onNoteDeleteListener) {
        this.noteList = notes;
        this.onNoteListener = onNoteListener;
        this.onNoteDeleteListener = onNoteDeleteListener;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        int randomInt = new Random().nextInt(Utils.getColorCodes().size());
        holder.bind(note,Utils.getColorCodes().get(randomInt));
        holder.itemView.setOnClickListener(v -> onNoteListener.onNoteSelected(note));

        holder.deleteImageView.setOnClickListener(v -> onNoteDeleteListener.onNoteDeleted(note.getId()));
        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, note.getDescription());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                holder.itemView.getContext().startActivity(shareIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (noteList == null) {
            return 0;
        }
        return noteList.size();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public interface OnNoteListener{
        void onNoteSelected(Note note);
    }
    public interface OnNoteDeleteListener{
        void onNoteDeleted(int id);
    }
}

