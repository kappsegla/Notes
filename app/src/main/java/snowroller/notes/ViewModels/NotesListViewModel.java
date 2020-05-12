package snowroller.notes.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import snowroller.notes.models.provider.NotesContentProvider;

/**
 * Created by Martin on 2017-02-09.
 */

public class NotesListViewModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private CursorRecyclerAdapter adapter;

    public NotesListViewModel(Context context)
    {
        this.context = context;
    }

    @Nullable
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
    @Nullable
    private static AppCompatActivity getActivity(Context context) {
            if (context instanceof Activity) {
                return (AppCompatActivity)context;
            }
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (AppCompatActivity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @BindingAdapter("app:items")
    public static void bindList(RecyclerView view, NotesListViewModel viewmodel) {
        //view.setAdapter(new NoteListAdapter(list));
        SimpleCursorRecyclerAdapter adapter = new SimpleCursorRecyclerAdapter();
        view.setAdapter(adapter);
        viewmodel.setAdapter(adapter);
        getActivity(view).getSupportLoaderManager()
                .initLoader(1, null, viewmodel);
    }

    @BindingAdapter("app:animduration")
    public static void bindItemAnimator(RecyclerView view, int duration) {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(duration);
        itemAnimator.setRemoveDuration(duration);
        view.setItemAnimator(itemAnimator);
    }

    public void setAdapter(CursorRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String SELECTION = "";
        List<String> selectionArgs = new ArrayList<String>();

        if (args != null && args.containsKey("query")) {
            SELECTION += " title LIKE ?";
            selectionArgs.add( "%" + args.getString("query") + "%");
        }

        final String[] SELECTION_ARGS = new String[selectionArgs.size()];
        selectionArgs.toArray(SELECTION_ARGS);

        return new CursorLoader(context, NotesContentProvider.CONTENT_URI,
                new String[]{"_id","title","body"}, SELECTION, SELECTION_ARGS, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void filter(String s) {
        Bundle bundle = new Bundle();
        if( !s.isEmpty())
            bundle.putString("query", s);

        getActivity(context).getSupportLoaderManager().restartLoader(1, bundle, this);
    }
}


