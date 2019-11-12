package com.coding.wk.cataloguemovieextendedapplication.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.ID_MOVIE;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.TABLE_NAME;


public class MoviesHelper {
    private String DATABASE_TABLE = TABLE_NAME;
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase db;

    public MoviesHelper (Context context){
        this.context = context;
    }

    public Cursor queryByIdProvider(String id){
        return db.query(DATABASE_TABLE,null,ID_MOVIE + " = ?",new String[]{id},null,null,null,null);
    }
    public Cursor queryProvider(){
        return db.query(DATABASE_TABLE,null,null,null,null,null,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return db.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return db.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return db.delete(DATABASE_TABLE,ID_MOVIE+ " = ?", new String[]{id});
    }
    public MoviesHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }
}
