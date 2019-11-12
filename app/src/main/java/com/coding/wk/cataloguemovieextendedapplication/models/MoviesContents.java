package com.coding.wk.cataloguemovieextendedapplication.models;

import android.database.Cursor;

import com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract;

import org.json.JSONObject;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.getColumnInt;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.getColumnString;

public class MoviesContents {
    private int id;
    private String title;
    private String releaseDate;
    private String description;
    private String posterPath;
    public MoviesContents(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String releaseDate = object.getString("release_date");
            String description = object.getString("overview");
            String posterPath = object.getString("poster_path");
            setId(id);
            setTitle(title);
            setReleaseDate(releaseDate);
            setDescription(description);
            setPosterPath(posterPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MoviesContents(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.MoviesColumns.ID_MOVIE);
        this.title = getColumnString(cursor, DatabaseContract.MoviesColumns.TITLE);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MoviesColumns.RELEASE_DATE);
        this.description = getColumnString(cursor, DatabaseContract.MoviesColumns.DESCRIPTION);
        this.posterPath = getColumnString(cursor, DatabaseContract.MoviesColumns.IMAGE_URL);
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle() {

        return title;
    }
    public void setTitle(String title) {

        this.title = title;
    }
    public String getReleaseDate() {

        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {

        this.releaseDate = releaseDate;
    }
    public String getDescription() {

        return description;
    }
    public void setDescription(String description) {

        this.description = description;
    }
    public String getPosterPath() {

        return posterPath;
    }
    public void setPosterPath(String posterPath) {

        this.posterPath = posterPath;
    }
}
