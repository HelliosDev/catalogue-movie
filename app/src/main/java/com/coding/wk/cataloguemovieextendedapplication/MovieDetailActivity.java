package com.coding.wk.cataloguemovieextendedapplication;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coding.wk.cataloguemovieextendedapplication.databases.MoviesHelper;
import com.coding.wk.cataloguemovieextendedapplication.widgets.MovieStackWidget;

import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.CONTENT_URI;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.DESCRIPTION;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.ID_MOVIE;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.IMAGE_URL;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.coding.wk.cataloguemovieextendedapplication.databases.DatabaseContract.MoviesColumns.TITLE;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_RELEASE = "extra_release";
    public static final String EXTRA_DESCRIPTION= "extra_description";
    public static final String EXTRA_IMAGE= "extra_image";
    public static final String EXTRA_ID = "extra_id";
    private Button btnFavorite;
    private ImageView imgDetail;
    private TextView titleDetail,releaseDetail, descriptionDetail;
    private int intentId;
    private String intentTitle, intentDate, intentDescription, intentImage;
    private MoviesHelper moviesHelper;
    public static int REQUEST_CODE = 100;
    public static int RESULT_DELETE = 101;
    public static int RESULT_ADD = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        moviesHelper = new MoviesHelper(this);
        moviesHelper.open();

        imgDetail = findViewById(R.id.img_detail);
        titleDetail = findViewById(R.id.title_detail);
        releaseDetail = findViewById(R.id.release_date_detail);
        descriptionDetail = findViewById(R.id.description_detail);

        intentId = getIntent().getIntExtra(EXTRA_ID, 0);
        intentTitle = getIntent().getStringExtra(EXTRA_TITLE);
        intentDate = getIntent().getStringExtra(EXTRA_RELEASE);
        intentDescription = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        intentImage = getIntent().getStringExtra(EXTRA_IMAGE);

        titleDetail.setText(intentTitle);
        releaseDetail.setText(intentDate);
        descriptionDetail.setText(intentDescription);
        Glide.with(MovieDetailActivity.this)
                .load("http://image.tmdb.org/t/p/w500/"+intentImage)
                .apply(new RequestOptions().error(R.drawable.no_img))
                .into(imgDetail);


        btnFavorite = findViewById(R.id.btn_favorite);
        String btnText;
        if(isFavorite(intentId)){
            btnText = getResources().getString(R.string.btn_text_remove_favorite);
        }
        else{
            btnText = getResources().getString(R.string.btn_text_add_favorite);
        }
        btnFavorite.setText(btnText);
        btnFavorite.setOnClickListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (moviesHelper != null) {
            moviesHelper.close();
        }
    }
    @Override
    public void onClick(View v) {
        if(!isFavorite(intentId)){
            addFavorites();
            btnFavorite.setText(getResources().getString(R.string.btn_text_remove_favorite));
        }
        else{
            removeFavorites();
        }
    }

    public void addFavorites(){
        ContentValues values = new ContentValues();
        values.put(ID_MOVIE, intentId);
        values.put(TITLE, intentTitle);
        values.put(RELEASE_DATE, intentDate);
        values.put(DESCRIPTION, intentDescription);
        values.put(IMAGE_URL, intentImage);
        getContentResolver().insert(CONTENT_URI, values);
        showToast(intentTitle + " " + getResources().getString(R.string.message_add_favorite));
        setResult(RESULT_ADD);
        widgetBroadcastUpdate(this);
    }

    public void removeFavorites(){
        getContentResolver().delete(getIntent().getData(), null, null);
        showToast(intentTitle + " " + getResources().getString(R.string.message_remove_favorite));
        setResult(RESULT_DELETE);
        widgetBroadcastUpdate(this);
        finish();
    }

    public boolean isFavorite(int id){
        String idMovie = Integer.toString(id);
        Cursor cursor = getContentResolver().query(getIntent().getData(), null, ID_MOVIE +" = '?'", new String[]{idMovie}, null);
        boolean isFav = cursor.getCount() > 0;
        cursor.close();
        return isFav;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static void widgetBroadcastUpdate(Context context){
        Intent widgetIntent = new Intent(context, MovieStackWidget.class);
        widgetIntent.setAction(MovieStackWidget.UPDATE_WIDGET);
        context.sendBroadcast(widgetIntent);
    }
}
