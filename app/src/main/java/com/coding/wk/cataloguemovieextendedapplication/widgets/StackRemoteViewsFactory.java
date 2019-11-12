package com.coding.wk.cataloguemovieextendedapplication.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.models.MoviesContents;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor moviesList;
    private Context context;
    private int appWidgetId;
    StackRemoteViewsFactory(Context context, Intent intent){
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private MoviesContents getItemAtPosition(int position){
        if(!moviesList.moveToPosition(position)) throw new IllegalStateException("Invalid Position");
        return new MoviesContents(moviesList);
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        moviesList = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(moviesList == null)return 0;
        return moviesList.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (moviesList == null || position == AdapterView.INVALID_POSITION || !moviesList.moveToPosition(position))return null;
        MoviesContents moviesContents = getItemAtPosition(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        try {

            bitmap = Glide.with(context)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w500/"+moviesContents.getPosterPath())
                    .apply(new RequestOptions().error(R.drawable.no_img))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.image_view_favorite, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(MovieStackWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.image_view_favorite, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
