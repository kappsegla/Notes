package snowroller.notes;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Objects;

import snowroller.notes.databinding.NewNoteBinding;
import snowroller.notes.models.NotesRepo;
import snowroller.notes.viewmodels.NoteViewModel;

public class NewNote extends AppCompatActivity {

    private NoteViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NewNoteBinding binder = DataBindingUtil.setContentView(this,
                R.layout.activity_new_note);
        viewmodel = new NoteViewModel(this, savedInstanceState, new NotesRepo(this));

        if (savedInstanceState == null) {
            //Fresh start
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                //New Note
                viewmodel.setId(0);
            } else {
                //Edit note
                viewmodel.setId(extras.getLong("id"));
            }
        }
//        else {
//            //Just restart of activity
//        }

        //if( viewmodel.isEditmode())
        //    this.setTitle(R.string.title_activity_edit_note);

        binder.setViewmodel(viewmodel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_back_arrow_light);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewmodel.saveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if(viewmodel.isChanged()) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.alert_exit_no_save)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes,
                            (dialog, which) -> NewNote.this.finish())
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();// Respond to the action bar's Up/Home button
        if (itemId == android.R.id.home) {
            //return true;
        } else if (itemId == R.id.menu_delete) {
            if (viewmodel.delete())
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
