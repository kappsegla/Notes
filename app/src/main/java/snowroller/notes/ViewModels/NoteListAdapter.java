package snowroller.notes.ViewModels;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import snowroller.notes.Models.Note;
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
        }
    }

    private List<Note> list;

    public NoteListAdapter(List<Note> list) {
        this.list = list;
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
}
