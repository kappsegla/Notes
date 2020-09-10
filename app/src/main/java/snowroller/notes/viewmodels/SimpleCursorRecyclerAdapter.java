package snowroller.notes.viewmodels;

import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import snowroller.notes.NewNote;
import snowroller.notes.databinding.ListItemBinding;
import snowroller.notes.models.Note;

public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleCursorRecyclerAdapter.SimpleViewHolder> {

    static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        final ListItemBinding binder;

        SimpleViewHolder(View v) {
            super(v);
            binder = DataBindingUtil.bind(v);

            final long id = this.getItemId();
            v.setOnClickListener(v1 -> {
                /*
                NoteListAdapter.this.list.remove(ViewHolder.this.getAdapterPosition());
                NoteListAdapter.this.notifyItemRemoved(ViewHolder.this.getAdapterPosition());
                */
                Intent intent = new Intent(v1.getContext(), NewNote.class);
                intent.putExtra("id", SimpleViewHolder.this.getItemId());
                v1.getContext().startActivity(intent);
            });
        }
    }


    public SimpleCursorRecyclerAdapter () {
        super(null);
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View v = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false).getRoot();
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder (SimpleViewHolder holder, Cursor cursor) {
        //TODO: Move the getColumnIndex into swap method to only do it once.
        int column_id = cursor.getColumnIndex("_id");
        int column_title = cursor.getColumnIndex("title");
        int column_body = cursor.getColumnIndex("body");

        Note n = new Note();
        n._id = cursor.getLong(column_id);
        n.title = cursor.getString(column_title);
        n.body = cursor.getString(column_body);
        Objects.requireNonNull(holder.binder).setViewmodel(n);
        holder.binder.executePendingBindings();
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        return super.swapCursor(c);
    }
}