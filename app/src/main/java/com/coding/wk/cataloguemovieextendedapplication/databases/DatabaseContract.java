package com.coding.wk.cataloguemovieextendedapplication.databases;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "table_favorite";

    public static final class MoviesColumns implements BaseColumns{
        public static String ID_MOVIE = "id_movie";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String DESCRIPTION = "description";
        public static String IMAGE_URL = "image_url";
    }
    public static final String AUTHORITY = "com.coding.wk.cataloguemovieextendedapplication";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
