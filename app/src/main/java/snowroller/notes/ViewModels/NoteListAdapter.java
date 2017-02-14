package snowroller.notes.viewmodels;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
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
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ListItemBinding binder;

        public ViewHolder(View v) {
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
                    intent.putExtra("id", ViewHolder.this.getItemId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Note> list;

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
