package snowroller.notes.viewmodels;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.net.ssl.HandshakeCompletedEvent;

import snowroller.notes.NewNote;
import snowroller.notes.databinding.ListItemBinding;
import snowroller.notes.models.Note;
import snowroller.notes.models.NotesRepo;

public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleCursorRecyclerAdapter.SimpleViewHolder> {

    public class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public ListItemBinding binder;

        public SimpleViewHolder(View v) {
            super(v);
            binder = DataBindingUtil.bind(v);

            final long id = this.getItemId();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    NoteListAdapter.this.list.remove(ViewHolder.this.getAdapterPosition());
                    NoteListAdapter.this.notifyItemRemoved(ViewHolder.this.getAdapterPosition());
                    */
                    Intent intent = new Intent(v.getContext(), NewNote.class);
                    intent.putExtra("id", SimpleViewHolder.this.getItemId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }


    public SimpleCursorRecyclerAdapter () {
        super(null);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
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
        holder.binder.setViewmodel(n);
        holder.binder.executePendingBindings();
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        return super.swapCursor(c);
    }
}