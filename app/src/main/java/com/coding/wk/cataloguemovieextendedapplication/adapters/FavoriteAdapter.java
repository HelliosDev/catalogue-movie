package com.coding.wk.cataloguemovieextendedapplication.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coding.wk.cataloguemovieextendedapplication.MovieDetailActivity;
import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.models.MoviesContents;
import com.coding.wk.cataloguemovieextendedapplication.utils.CustomOnClickListener;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Cursor listMovies;
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListMovies(Cursor listMovies) {
        this.listMovies = listMovies;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textReleaseDate;
        TextView textDescription;
        ImageView imageMovie;
        Button btnMore;
        FavoriteViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.text_title);
            textReleaseDate = view.findViewById(R.id.text_release_date);
            textDescription = view.findViewById(R.id.text_description);
            imageMovie = view.findViewById(R.id.img_list);
            btnMore = view.findViewById(R.id.btn_more);
        }
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        final MoviesContents moviesContents = getItem(position);
        holder.textTitle.setText(moviesContents.getTitle());
        holder.textReleaseDate.setText(moviesContents.getReleaseDate());
        holder.textDescription.setText(moviesContents.getDescription());
        Glide
                .with(activity)
                .load("http://image.tmdb.org/t/p/w185/"+moviesContents.getPosterPath())
                .apply(new RequestOptions().error(R.drawable.no_img))
                .into(holder.imageMovie);

        holder.btnMore.setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_ID, moviesContents.getId());
                intent.putExtra(MovieDetailActivity.EXTRA_TITLE, moviesContents.getTitle());
                intent.putExtra(MovieDetailActivity.EXTRA_RELEASE, moviesContents.getReleaseDate());
                intent.putExtra(MovieDetailActivity.EXTRA_DESCRIPTION, moviesContents.getDescription());
                intent.putExtra(MovieDetailActivity.EXTRA_IMAGE, moviesContents.getPosterPath());
                intent.setData(Uri.parse(CONTENT_URI+"/"+moviesContents.getId()));
                activity.startActivityForResult(intent, MovieDetailActivity.REQUEST_CODE);
            }
        }));
    }
    private MoviesContents getItem(int position){
        if (!listMovies.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MoviesContents(listMovies);
    }
    @Override
    public int getItemCount() {
        if (listMovies == null) return 0;
        return listMovies.getCount();
    }
}
