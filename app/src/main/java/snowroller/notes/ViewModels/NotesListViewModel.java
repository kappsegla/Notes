package snowroller.notes.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.ContentObserver;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import snowroller.notes.models.Note;
import snowroller.notes.models.NotesRepo;
import snowroller.notes.models.provider.NotesContentProvider;

/**
 * Created by Martin on 2017-02-09.
 */

public class NotesListViewModel {

    private NotesRepo repo;

    public NotesListViewModel(NotesRepo repo)
    {
        this.repo = repo;
    }

    public Cursor getNotes()
    {
        return repo.getNotes();
    }

    private static AppCompatActivity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (AppCompatActivity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @BindingAdapter("app:items")
    public static void bindList(RecyclerView view, Cursor cursor) {
        //view.setAdapter(new NoteListAdapter(list));
        SimpleCursorRecyclerAdapter adapter = new SimpleCursorRecyclerAdapter();
        getActivity(view).getSupportLoaderManager()
                .initLoader(1, null, new MyCursorLoader(view.getContext(), adapter));
        view.setAdapter(adapter);
    }

    @BindingAdapter("app:animduration")
    public static void bindItemAnimator(RecyclerView view, int duration) {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(duration);
        itemAnimator.setRemoveDuration(duration);
        view.setItemAnimator(itemAnimator);
    }
}
class MyCursorLoader implements LoaderManager.LoaderCallbacks<Cursor>
{
    private Context context;
    private CursorRecyclerAdapter adapter;

    public MyCursorLoader(Context context, CursorRecyclerAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context, NotesContentProvider.CONTENT_URI,
                new String[]{"_id","title","body"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
