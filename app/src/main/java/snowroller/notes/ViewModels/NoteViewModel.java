package snowroller.notes.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by Martin on 2017-02-08.
 */

public class NoteViewModel extends BaseObservable {

    private String title;
    private String body;
    private boolean changed;

    public NoteViewModel(Bundle savedInstanceState) {
        changed = false;
        if(savedInstanceState != null) {
            title = savedInstanceState.getString("title", "");
            body = savedInstanceState.getString("body", "");
            changed = savedInstanceState.getBoolean("changed", false);
        }
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        changed = true;
        notifyPropertyChanged(BR.title);
        Log.i("AndroidExample", title);
    }

    @Bindable
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        changed = true;
        notifyPropertyChanged(BR.body);
    }

    public boolean isChanged() {return changed;}

    public void saveInstanceState(Bundle outState) {
        outState.putString("title", title);
        outState.putString("body", body);
        outState.putBoolean("changed", changed);
    }

    public void onFabClick(View v){
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
