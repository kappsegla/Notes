package snowroller.notes.models;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.Objects;

import snowroller.notes.models.provider.NotesContentProvider;

/**
 * Created by Martin on 2017-02-09.
 */

public class NotesRepo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NotesDb";
    private static final int VERSION = 2;

    private final ContentResolver myCR;
    public NotesRepo(Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("","onCreate Database!");
        db.execSQL("CREATE TABLE Notes (_id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "body TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion == 1 && newVersion == 2)
        {
            //Change name on notes id column to notes _id
            db.beginTransaction();
            try {
                //Rename existing table
                db.execSQL("ALTER TABLE Notes RENAME TO tmp_notes;");
                //Create new Notes table with _id column name
                db.execSQL("CREATE TABLE Notes (_id INTEGER PRIMARY KEY," +
                        "title TEXT," +
                        "body TEXT)");

                db.execSQL("INSERT INTO Notes(title, body) " +
                        "SELECT title, body " +
                        "FROM tmp_notes;");
                //Remove old table
                db.execSQL("DROP TABLE tmp_notes;");
                db.setTransactionSuccessful();
            }
            finally {
                db.endTransaction();
            }


        }
    }

    public long insert(Note note)
    {
        ContentValues values = new ContentValues();
        values.put("title", note.title);
        values.put("body", note.body);
        if( note._id == 0) {
            //New Note, Insert into DataBase
            Uri response = myCR.insert(NotesContentProvider.CONTENT_URI,values);
            note._id = Long.parseLong(Objects.requireNonNull(Objects.requireNonNull(response).getLastPathSegment()));
        }
        return note._id;
    }

    public long update(Note note)
    {
        //Update existing note
        ContentValues values = new ContentValues();
        values.put("title", note.title);
        values.put("body", note.body);
        if( note._id != 0) {
            int rowsUpdated = myCR.update(Uri.parse("content://"
                            + "snowroller.notes.models.provider.NotesContentProvider"
                            + "/" + "notes" + "/" + note._id),
                    values, null, null);

        }
        return note._id;
    }

    public boolean delete(long id){
            //Update existing note
            int rowsDeleted
                        = myCR.delete(Uri.parse("content://"
                        + "snowroller.notes.models.provider.NotesContentProvider"
                        + "/" + "notes" + "/" + id),
                        null, null);
            return true;
    }

    public Note getNote(long id)
    {
        SQLiteDatabase notesdb = this.getReadableDatabase();
        Cursor cursor = notesdb.rawQuery("SELECT * FROM Notes WHERE _id =" + id, null);

        int column_id = cursor.getColumnIndex("_id");
        int column_title = cursor.getColumnIndex("title");
        int column_body = cursor.getColumnIndex("body");

        Note n = new Note();

        if(cursor.moveToNext())
        {
            n._id = cursor.getLong(column_id);
            n.title = cursor.getString(column_title);
            n.body = cursor.getString(column_body);
        }
        cursor.close();
        notesdb.close();
        return n;
    }

    public Cursor getNotes(){
        String[] projection = {"_id","title","body"};

        return myCR.query(NotesContentProvider.CONTENT_URI,projection, null,null,null);
    }

/*
    public List<Note> getNotes()
    {
        SQLiteDatabase notesdb = this.getReadableDatabase();
        Cursor cursor = notesdb.rawQuery("SELECT * FROM Notes", null);
        List<Note> list = new ArrayList<Note>();

        int column_id = cursor.getColumnIndex("_id");
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
*/
}
