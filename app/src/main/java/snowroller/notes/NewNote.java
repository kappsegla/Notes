package snowroller.notes;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import snowroller.notes.Models.NotesRepo;
import snowroller.notes.ViewModels.NoteViewModel;
import snowroller.notes.databinding.NewNoteBinding;

public class NewNote extends AppCompatActivity {

    private NoteViewModel viewmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_note);
        NewNoteBinding binder = DataBindingUtil.setContentView(this,
                R.layout.activity_new_note);
        viewmodel = new NoteViewModel(savedInstanceState, new NotesRepo(this));
        binder.setViewmodel(viewmodel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
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
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    NewNote.this.finish();
                                }
                            })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
