package com.ngenge.apps.notestaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.ngenge.apps.notestaker.models.Note;
import com.ngenge.apps.notestaker.models.Utils;
import com.ngenge.apps.notestaker.room.NotesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout root;
    private ActionBar toolbar;
    private FirebaseFirestore firestore;
    private WriteBatch writeBatch;
    private String userId;
    private SharedPreferences preferences;

    NotesViewModel notesViewModel;
    Observer<List<Note>> notesObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        toolbar = getSupportActionBar();

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        NavController controller = Navigation.findNavController(this, R.id.fragmentContainer);

        controller.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_new_note) {
                    toolbar.setTitle(getString(R.string.new_note));
                } else if (destination.getId() == R.id.nav_details) {
                    toolbar.setTitle(getString(R.string.note_details));
                } else if (destination.getId() == R.id.nav_note_list) {
                    toolbar.setTitle(getString(R.string.app_name));
                }
            }
        });

        initFirebase();

        preferences = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
        userId = preferences.getString(getString(R.string.user_id), null);

        notesObserver = new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {


                CollectionReference reference = firestore.collection(userId);

                if (notes != null && notes.size() > 0) {

                    for (Note note: notes) {
                        DocumentReference ref = reference.document(String.valueOf(note.getId()));
                        writeBatch.set(ref,note);

                    }


                    writeBatch.commit()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    showToast("Syncing complete");
                                    notesViewModel.getAllNotes().removeObservers(MainActivity.this);
                                } else {

                                    showToast("An error occurred");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    showToast("Syncing failed");
                                    Utils.logMessage("Exception "+ e.getMessage());
                                }
                            });

                }


            }
        };
    }


    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    private void initFirebase() {
        firestore = FirebaseFirestore.getInstance();
        //writeBatch = firestore.batch();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.sync_offline) {
            syncFirebaseOffline();
            return true;
        }

        else if (itemId == R.id.sync_online) {
            syncToFirebase();
        }
        return super.onOptionsItemSelected(item);
    }


    private void syncToFirebase() {
        writeBatch = firestore.batch();
        notesViewModel.getAllNotes().observe(this,notesObserver);
    }

    private void syncFirebaseOffline() {
        CollectionReference ref = firestore.collection(userId);

        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    Note note = document.toObject(Note.class);

                    notesViewModel.insertNote(note);

                }

                showToast("Syncing offline succeeded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.logMessage("Failed with exception "+ e.getMessage());
            }
        });
    }
}
