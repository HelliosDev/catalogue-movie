package com.coding.wk.cataloguefavorite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coding.wk.cataloguefavorite.adapters.FavoriteMoviesAdapter;

import static com.coding.wk.cataloguefavorite.database.DatabaseContract.CONTENT_URI;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.DESCRIPTION;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.ID_MOVIE;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.IMAGE_URL;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.TITLE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{
    ListView lvMovies;
    private final int LOAD_MOVIES = 100;
    private FavoriteMoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new FavoriteMoviesAdapter(this, null, true);
        lvMovies = findViewById(R.id.lv_movies);
        lvMovies.setAdapter(adapter);
        lvMovies.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader( LOAD_MOVIES, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIES, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_MOVIES);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) adapter.getItem(position);
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_ID, cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
        intent.putExtra(MovieDetailActivity.EXTRA_TITLE, cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
        intent.putExtra(MovieDetailActivity.EXTRA_RELEASE, cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
        intent.putExtra(MovieDetailActivity.EXTRA_DESCRIPTION, cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
        intent.putExtra(MovieDetailActivity.EXTRA_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_URL)));
        intent.setData(Uri.parse(CONTENT_URI+"/"+cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE))));
        startActivity(intent);
    }
}
