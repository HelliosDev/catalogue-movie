package com.coding.wk.cataloguemovieextendedapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MoviesContents>>{
    private RecyclerView rvUpcoming ;
    MoviesAdapter adapter;
    Context context;
    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        context = view.getContext();
        adapter = new MoviesAdapter(context);
        adapter.notifyDataSetChanged();

        rvUpcoming = view.findViewById(R.id.rv_upcoming);
        rvUpcoming.setHasFixedSize(true);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(context));
        rvUpcoming.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, UpcomingFragment.this);
        return view;
    }

    @Override
    public Loader<ArrayList<MoviesContents>> onCreateLoader(int id, Bundle args) {
        String filmSearch = "";
        return new MoviesLoader(context, filmSearch,"upcoming");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MoviesContents>> loader, ArrayList<MoviesContents> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MoviesContents>> loader) {

    }
}
