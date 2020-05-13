package snowroller.notes.viewmodels;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import snowroller.notes.NewNote;
import snowroller.notes.models.Note;
import snowroller.notes.databinding.ListItemBinding;

/**
 * Created by Martin on 2017-02-09.
 */
class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemBinding binder;

        ViewHolder(View v) {
            super(v);
            binder = DataBindingUtil.bind(v);

            final long id = this.getItemId();
            v.setOnClickListener(v1 -> {
                /*
                NoteListAdapter.this.list.remove(ViewHolder.this.getAdapterPosition());
                NoteListAdapter.this.notifyItemRemoved(ViewHolder.this.getAdapterPosition());
                */
                Intent intent = new Intent(v1.getContext(), NewNote.class);
                intent.putExtra("id", ViewHolder.this.getItemId());
                v1.getContext().startActivity(intent);
            });
        }
    }

    private final List<Note> list;

    public NoteListAdapter(List<Note> list) {
        this.list = list;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false).getRoot();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note info = list.get(position);
        holder.binder.setViewmodel(info);
        holder.binder.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position)._id;
    }

}
