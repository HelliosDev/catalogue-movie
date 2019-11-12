package com.coding.wk.cataloguefavorite.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static String TABLE_NAME = "table_favorite";

    public static final class MoviesColumns implements BaseColumns{
        public static String ID_MOVIE = "id_movie";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String DESCRIPTION = "description";
        public static String IMAGE_URL = "image_url";
    }
    private static final String AUTHORITY = "com.coding.wk.cataloguemovieextendedapplication";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
