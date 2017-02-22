package snowroller.notes.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import snowroller.notes.R;
import snowroller.notes.models.Note;
import snowroller.notes.models.NotesRepo;

/**
 * Created by Martin on 2017-02-08.
 */

public class NoteViewModel extends BaseObservable {

    private String title;
    private String body;

    private long id;

    private boolean changed;
    private boolean editmode;
    private NotesRepo repo;

    private Context context;

    public NoteViewModel(Context context, Bundle savedInstanceState, NotesRepo repo) {
        changed = false;
        this.repo = repo;
        this.context = context;
        id = 0;
        editmode = false;
        if(savedInstanceState != null) {
            title = savedInstanceState.getString("title", "");
            body = savedInstanceState.getString("body", "");
            changed = savedInstanceState.getBoolean("changed", false);
            id = savedInstanceState.getLong("id", 0);
            editmode = savedInstanceState.getBoolean("editmode", false);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;

        if(this.id != 0) {
            editmode = true;
            Note note = repo.getNote(id);
            if(note.title != null)
                this.setTitle(note.title);
            if(note.body != null)
                this.setBody(note.body);
            changed = false;
        }
    }

    @Bindable
    public String getToolbarTitle()
    {
        if( isEditmode())
            return context.getResources().getString(R.string.title_activity_edit_note);
        else
            return context.getString(R.string.title_activity_new_note);
    }

    public boolean isEditmode() {
        return editmode;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if( !title.equals(this.title)) {
            this.title = title;
            changed = true;
            notifyPropertyChanged(BR.title);
        }
    }

    @Bindable
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        if( !body.equals(this.body)) {
            this.body = body;
            changed = true;
            notifyPropertyChanged(BR.body);
        }
    }

    public boolean isChanged() {return changed;}

    public void saveInstanceState(Bundle outState) {
        outState.putString("title", title);
        outState.putString("body", body);
        outState.putBoolean("changed", changed);
        outState.putBoolean("editmode", editmode);
        outState.putLong("id", id);
    }

    public void onFabClick(View v){

        Note note = new Note();
        note._id = this.id;
        note.title = this.title;
        note.body = this.body;

        if( this.id == 0)
            id = repo.insert(note);
        else
            id = repo.update(note);

        Snackbar.make(v, "Saved, id: " + id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        changed = false;
    }

    public boolean delete() {
        return repo.delete(this.id);
    }
}
