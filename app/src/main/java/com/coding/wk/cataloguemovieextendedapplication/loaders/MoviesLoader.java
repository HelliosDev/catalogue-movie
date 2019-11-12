package com.coding.wk.cataloguemovieextendedapplication.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.coding.wk.cataloguemovieextendedapplication.BuildConfig;
import com.coding.wk.cataloguemovieextendedapplication.models.MoviesContents;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesLoader extends AsyncTaskLoader<ArrayList<MoviesContents>> {
    private ArrayList<MoviesContents> moviesData;
    private static final String API_KEY = BuildConfig.API_KEY;
    private String NAMA_MOVIE;
    private String segment;
    private boolean berisi = false;
    public MoviesLoader(Context context, String keyword, String segment) {
        super(context);
        onContentChanged();
        this.NAMA_MOVIE = keyword;
        this.segment = segment;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (berisi)
            deliverResult(moviesData);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (berisi) {
            moviesData = null;
            berisi = false;
        }
    }

    @Override
    public ArrayList<MoviesContents> loadInBackground() {
        final ArrayList<MoviesContents> moviesList = new ArrayList<>();
        SyncHttpClient client = new SyncHttpClient();
        String url = "";
        switch(segment){
            case "search":
                url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+NAMA_MOVIE;
                break;
            case "now_playing":
                url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
                break;
            case "upcoming":
                url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
                break;
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray list = new JSONObject(new String(responseBody)).getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        MoviesContents moviesContents = new MoviesContents(list.getJSONObject(i));
                        moviesList.add(moviesContents);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

        return moviesList;
    }

    @Override
    public void deliverResult(ArrayList<MoviesContents> data) {
        moviesData = data;
        berisi = true;
        super.deliverResult(data);
    }
}

