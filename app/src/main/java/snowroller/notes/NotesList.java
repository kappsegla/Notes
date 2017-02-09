package snowroller.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import snowroller.notes.Models.NotesRepo;
import snowroller.notes.ViewModels.NoteViewModel;
import snowroller.notes.ViewModels.NotesListViewModel;
import snowroller.notes.databinding.ActivityNotesListBinding;

public class NotesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesListBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_notes_list);
        binding.setViewmodel(new NotesListViewModel(new NotesRepo(this)));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
