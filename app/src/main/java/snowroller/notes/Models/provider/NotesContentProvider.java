package snowroller.notes.models.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.util.Objects;

import snowroller.notes.models.NotesRepo;

/**
 * Created by Martin on 2017-02-12.
 */

public class NotesContentProvider extends ContentProvider {
    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + "snowroller.notes.models.provider.NotesContentProvider" +
            "/"+"notes");

    static {
        sUriMatcher.addURI("snowroller.notes.models.provider.NotesContentProvider", "notes", 1);
        sUriMatcher.addURI("snowroller.notes.models.provider.NotesContentProvider", "notes/#", 2);
    }

    private NotesRepo notesrepo;

    @Override
    public boolean onCreate() {
        notesrepo = new NotesRepo(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("notes");

        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case 2:
                queryBuilder.appendWhere("_id="+uri.getLastPathSegment() );
                break;
            case 1:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(notesrepo.getReadableDatabase(),
                projection,selection,selectionArgs,null,null,
                sortOrder);
        //Set the Uri that clients can track for updates
        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //TODO: Implement gettype on ContentProvider
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = notesrepo.getWritableDatabase();

        if (uriType == 1) {
            long rowId = sqLiteDatabase.insert("notes", null, values);
            if (rowId > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);

                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = notesrepo.getWritableDatabase();
        int rowsDeleted;

        switch (uriType) {
            case 1:
                rowsDeleted = sqlDB.delete("notes", selection, selectionArgs);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete("notes", "_id=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete("notes", "_id=" + id
                            + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = notesrepo.getWritableDatabase();
        int rowsUpdated;

        switch (uriType) {
            case 1:
                rowsUpdated = sqlDB.update("notes", values, selection, selectionArgs);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update("notes", values, "_id=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update("notes", values, "_id=" + id
                            + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
