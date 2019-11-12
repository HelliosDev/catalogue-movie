package com.coding.wk.cataloguefavorite.models;

public class MoviesItems {
    private int id;
    private String title;
    private String releaseDate;
    private String description;
    private String posterPath;

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
