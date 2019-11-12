package com.coding.wk.cataloguemovieextendedapplication.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.coding.wk.cataloguemovieextendedapplication.databases.MoviesHelper;


import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.AUTHORITY;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.TABLE_NAME;

public class MoviesProvider extends ContentProvider {
    private MoviesHelper moviesHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#",MOVIE_ID);
    }
    @Override
    public boolean onCreate() {
        moviesHelper = new MoviesHelper(getContext());
        moviesHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        String id = uri.getLastPathSegment();
        switch (uriMatcher.match(uri)){
            case MOVIE:
                cursor = moviesHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = moviesHelper.queryByIdProvider(id);
                break;
            default:
                cursor = null;
        }
        if(cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added ;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                added = moviesHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted =  moviesHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = moviesHelper.updateProvider(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
