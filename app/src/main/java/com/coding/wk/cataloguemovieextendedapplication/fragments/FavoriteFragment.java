package com.coding.wk.cataloguemovieextendedapplication.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coding.wk.cataloguemovieextendedapplication.MovieDetailActivity;
import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.adapters.FavoriteAdapter;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private Cursor list;
    private FavoriteAdapter adapter;
    RecyclerView rvFavorites;
    Context context;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();
        rvFavorites = view.findViewById(R.id.rv_favorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavorites.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getActivity());
        adapter.setListMovies(list);
        rvFavorites.setAdapter(adapter);

        new FavoritesLoadAsync().execute();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        new FavoritesLoadAsync().execute();
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    private class FavoritesLoadAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);

            list = data;
            adapter.setListMovies(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showToast("Belum ada favorite");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}
