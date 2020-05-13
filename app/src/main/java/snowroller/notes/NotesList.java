package snowroller.notes;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

//import com.google.firebase.firestore.FirebaseFirestore;

import snowroller.notes.databinding.ActivityNotesListBinding;
import snowroller.notes.viewmodels.NotesListViewModel;

public class NotesList extends AppCompatActivity {

    private NotesListViewModel viewmodel;

    //private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityNotesListBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_notes_list);


        viewmodel = new NotesListViewModel(this);
        binding.setViewmodel(viewmodel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
          //  testFirebaseAdd();

            Intent intent = new Intent(NotesList.this, NewNote.class);
            NotesList.this.startActivity(intent);

        });

        //Firestore
      //  db = FirebaseFirestore.getInstance();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_list, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewmodel.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0) {
                    viewmodel.filter("");
                }
                else
                    viewmodel.filter(s);
                return true;
            }
        });
        return true;
    }

//    public void testFirebaseAdd()
//    {
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        Map<String, Object> name = new HashMap<>();
//        user.put("name", name);
//        name.put("first", "Ada");
//        name.put("last", "Lovelace");
//        user.put("born", 1815);
//
//        // Add a new document with a specified name
//        db.collection("users").document("User1")
//                .set(user)
//                .addOnSuccessListener( aVoid -> {});
//        //Add a new document with a generated Id
//        db.collection("users").add(user).addOnSuccessListener(documentReference -> {
//
//        });
//    }
 }
