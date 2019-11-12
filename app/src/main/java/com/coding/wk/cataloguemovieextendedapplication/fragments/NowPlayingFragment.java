package com.coding.wk.cataloguemovieextendedapplication.fragments;


import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.adapters.MoviesAdapter;
import com.coding.wk.cataloguemovieextendedapplication.loaders.MoviesLoader;
import com.coding.wk.cataloguemovieextendedapplication.models.MoviesContents;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MoviesContents>>{
    MoviesAdapter adapter;
    Context context;
    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();
        adapter = new MoviesAdapter(context);
        adapter.notifyDataSetChanged();
        RecyclerView rvNow = view.findViewById(R.id.rv_now);
        rvNow.setHasFixedSize(true);
        rvNow.setLayoutManager(new LinearLayoutManager(context));
        rvNow.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, NowPlayingFragment.this);
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MoviesContents>> onCreateLoader(int id, Bundle args) {
        String filmSearch = "";
        return new MoviesLoader(context, filmSearch,"now_playing");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MoviesContents>> loader, ArrayList<MoviesContents> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MoviesContents>> loader) {
        adapter.setData(null);
    }
}
