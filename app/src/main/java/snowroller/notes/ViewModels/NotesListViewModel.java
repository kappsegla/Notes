package snowroller.notes.ViewModels;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import snowroller.notes.Models.Note;
import snowroller.notes.Models.NotesRepo;

/**
 * Created by Martin on 2017-02-09.
 */

public class NotesListViewModel {

    private NotesRepo repo;

    public NotesListViewModel(NotesRepo repo)
    {
        this.repo = repo;
    }

    public List<Note> getNotes()
    {
        return repo.getNotes();
    }

    @BindingAdapter("app:items")
    public static void bindList(RecyclerView view, List<Note> list) {
        view.setAdapter(new NoteListAdapter(list));
    }
}
