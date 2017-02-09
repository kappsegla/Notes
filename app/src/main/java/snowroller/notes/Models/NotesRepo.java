package snowroller.notes.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 2017-02-09.
 */

public class NotesRepo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NotesDb";
    private static final int VERSION = 1;
    public NotesRepo(Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("","onCreate Database!");
        db.execSQL("CREATE TABLE Notes (id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "body TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Note note)
    {
        SQLiteDatabase notesdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.title);
        values.put("body", note.body);
        if( note._id == 0) {
            //New Note, Insert into DataBase
            note._id = notesdb.insert("Notes", null, values);
        }
        else {
            //Update existing note
            String[] args = new String[]{Long.toString(note._id)};

            if( notesdb.update("Notes", values,"id=?",args) != 1)
            {
                //Something wrong here.
            }
        }
        notesdb.close();
        return note._id;
    }

    public List<Note> getNotes()
    {
        SQLiteDatabase notesdb = this.getReadableDatabase();
        Cursor cursor = notesdb.rawQuery("SELECT * FROM Notes", null);
        List<Note> list = new ArrayList<Note>();

        int column_id = cursor.getColumnIndex("id");
        int column_title = cursor.getColumnIndex("title");
        int column_body = cursor.getColumnIndex("body");

        while(cursor.moveToNext())
        {
            Note n = new Note();
            n._id = cursor.getLong(column_id);
            n.title = cursor.getString(column_title);
            n.body = cursor.getString(column_body);

            list.add(n);
        }
        cursor.close();
        notesdb.close();
        return list;
    }
}
