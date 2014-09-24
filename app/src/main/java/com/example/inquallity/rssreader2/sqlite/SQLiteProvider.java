package com.example.inquallity.rssreader2.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.inquallity.rssreader2.BuildConfig;
import com.example.inquallity.rssreader2.content.Channel;
import com.example.inquallity.rssreader2.content.News;

/**
 * Created by Inquallity on 23.09.2014.
 */
public class SQLiteProvider extends ContentProvider {

    public static final String AUTHORITY = BuildConfig.PACKAGE_NAME;
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "rss.db";

    private SQLiteHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new SQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String where, String[] whereArgs, String orderBy) {
        final String tableName = uri.getLastPathSegment();
        final Cursor cursor = mHelper.getReadableDatabase().query(tableName, columns, where, whereArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final String tableName = uri.getLastPathSegment();
        final long lastRowID = mHelper.getWritableDatabase().insert(tableName, BaseColumns._ID, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, lastRowID);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        final String tableName = uri.getLastPathSegment();
        final int affectedRows = mHelper.getWritableDatabase().delete(tableName, where, whereArgs);
        if (affectedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return affectedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        final String tableName = uri.getLastPathSegment();
        final int affectedRows = mHelper.getWritableDatabase().update(tableName, values, where, whereArgs);
        if (affectedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return affectedRows;
    }

    private static class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + Channel.TABLE + "(_id INTEGER PRIMARY KEY, title TEXT, link TEXT, image_url TEXT, UNIQUE(link) ON CONFLICT IGNORE);");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + News.TABLE + "(_id INTEGER PRIMARY KEY, title TEXT, link TEXT, image_url TEXT, full_text TEXT, channel_id INTEGER, UNIQUE(link) ON CONFLICT IGNORE);");
            db.execSQL("CREATE INDEX IF NOT EXISTS " + News.TABLE + "_idx1 ON " + News.TABLE + "(channel_id);");
            db.execSQL("CREATE TRIGGER IF NOT EXISTS delete_news BEFORE DELETE ON " + Channel.TABLE + " BEGIN " +
                    "DELETE FROM " + News.TABLE + " WHERE channel_id=OLD._id;" +
                    "END;");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Channel.TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + News.TABLE + ";");
            onCreate(db);
        }
    }
}
