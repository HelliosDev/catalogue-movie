package com.coding.wk.cataloguemovieextendedapplication.fragments;


import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.adapters.MoviesAdapter;
import com.coding.wk.cataloguemovieextendedapplication.loaders.MoviesLoader;
import com.coding.wk.cataloguemovieextendedapplication.models.MoviesContents;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MoviesContents>>{
    private RecyclerView rvMovies ;
    private EditText edtSearch;
    private Button btnSearch;
    private Bundle bundle = new Bundle();
    MoviesAdapter adapter;
    static final String KEYWORD = "keyword";
    Context context;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = view.getContext();
        adapter = new MoviesAdapter(context);
        adapter.notifyDataSetChanged();

        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(context));
        rvMovies.setAdapter(adapter);

        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);

        String keywordSearch = edtSearch.getText().toString();
        bundle.putString(KEYWORD, keywordSearch);
        btnSearch.setOnClickListener(listener);

        getLoaderManager().initLoader(0, bundle, SearchFragment.this);
        return view;
    }

    @Override
    public Loader<ArrayList<MoviesContents>> onCreateLoader(int id, Bundle args) {
        String filmSearch = "";
        if (args != null){
            filmSearch = args.getString(KEYWORD);
        }

        return new MoviesLoader(context,filmSearch,"search");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MoviesContents>> loader, ArrayList<MoviesContents> data) {
        Log.d(SearchFragment.class.getSimpleName(), "Berhasil di Load");
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MoviesContents>> loader) {

    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String filmSearch = edtSearch.getText().toString();
            bundle.putString(KEYWORD, filmSearch);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
        }
    };
}
