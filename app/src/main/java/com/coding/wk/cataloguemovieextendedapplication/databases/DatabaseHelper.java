package com.coding.wk.cataloguemovieextendedapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL UNIQUE, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)", DatabaseContract.TABLE_NAME, MoviesColumns._ID, MoviesColumns.ID_MOVIE, MoviesColumns.TITLE, MoviesColumns.RELEASE_DATE, MoviesColumns.DESCRIPTION, MoviesColumns.IMAGE_URL);

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
