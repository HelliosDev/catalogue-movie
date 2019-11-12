package com.coding.wk.cataloguefavorite.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coding.wk.cataloguefavorite.R;

import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.DESCRIPTION;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.IMAGE_URL;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.MoviesColumns.TITLE;
import static com.coding.wk.cataloguefavorite.database.DatabaseContract.getColumnString;

public class FavoriteMoviesAdapter extends CursorAdapter{
    public Activity activity;
    public FavoriteMoviesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_movie_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textTitle = view.findViewById(R.id.text_title);
        TextView textReleaseDate = view.findViewById(R.id.text_release_date);
        TextView textDescription = view.findViewById(R.id.text_description);
        ImageView imageMovie = view.findViewById(R.id.img_list);

        textTitle.setText(getColumnString(cursor,TITLE));
        textReleaseDate.setText(getColumnString(cursor,RELEASE_DATE));
        textDescription.setText(getColumnString(cursor,DESCRIPTION));
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/"+getColumnString(cursor, IMAGE_URL))
                .apply(new RequestOptions().error(R.drawable.no_img))
                .into(imageMovie);

    }
}
