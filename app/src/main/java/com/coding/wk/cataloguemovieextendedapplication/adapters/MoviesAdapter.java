package com.coding.wk.cataloguemovieextendedapplication.adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    private Context context;
    private ArrayList<MoviesContents> moviesData = new ArrayList<>();
    public MoviesAdapter(Context c){
        this.context = c;
    }

    public void setData(ArrayList<MoviesContents> data){
        moviesData = data;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textReleaseDate;
        TextView textDescription;
        ImageView imageMovie;
        Button btnMore;
        ViewHolder(View view) {
            super(view);

            textTitle = view.findViewById(R.id.text_title);
            textReleaseDate = view.findViewById(R.id.text_release_date);
            textDescription = view.findViewById(R.id.text_description);
            imageMovie = view.findViewById(R.id.img_list);
            btnMore = view.findViewById(R.id.btn_more);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textTitle.setText(moviesData.get(position).getTitle());
        holder.textReleaseDate.setText(moviesData.get(position).getReleaseDate());
        holder.textDescription.setText(moviesData.get(position).getDescription());
        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/"+moviesData.get(position).getPosterPath())
                .apply(new RequestOptions().error(R.drawable.no_img))
                .into(holder.imageMovie);

        holder.btnMore.setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_ID, moviesData.get(position).getId());
                intent.putExtra(MovieDetailActivity.EXTRA_TITLE, moviesData.get(position).getTitle());
                intent.putExtra(MovieDetailActivity.EXTRA_RELEASE, moviesData.get(position).getReleaseDate());
                intent.putExtra(MovieDetailActivity.EXTRA_DESCRIPTION, moviesData.get(position).getDescription());
                intent.putExtra(MovieDetailActivity.EXTRA_IMAGE, moviesData.get(position).getPosterPath());
                intent.setData(Uri.parse(CONTENT_URI+"/"+moviesData.get(position).getId()));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (moviesData == null) {
            return 0;
        }
        return moviesData.size();
    }
}
