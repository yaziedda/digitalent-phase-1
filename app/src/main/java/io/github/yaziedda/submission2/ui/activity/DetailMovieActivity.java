package io.github.yaziedda.submission2.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.database.MovieContract;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.MoviesResult_Table;
import io.github.yaziedda.submission2.ui.widget.FavoritWidget;
import io.github.yaziedda.submission2.ui.widget.StackRemoteViewsFactory;
import io.github.yaziedda.submission2.util.Static;

public class DetailMovieActivity extends AppCompatActivity {

    private Menu menu;
    private MoviesResult item;
    private boolean isfavorite;
//    String id, title, image;
    private static final String TAG = DetailMovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.tv_title);
        TextView desc = findViewById(R.id.tv_desc);
        TextView release = findViewById(R.id.tv_release);
        ImageView img = findViewById(R.id.img);


        item = (MoviesResult) getIntent().getSerializableExtra(MoviesResult.class.getName());
//        item = getIntent().getParcelableExtra(MoviesResult.class.getName());
        setTitle(item.getTitle());
        title.setText(item.getTitle());
        desc.setText(item.getOverview());
        release.setText(item.getReleaseDate());

        Glide.with(getApplicationContext())
                .load(Static.getUrlPoster(Static.POSTER_w780, item.getPosterPath()))
                .placeholder(R.drawable.ic_launcher_background)
                .into(img);

        loadUI();
    }

    private void loadUI() {
        if (isFavorite(item.getId().toString())) {
            isfavorite = true;
            if (menu != null){
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        loadUI();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favorit:

                MoviesResult moviesResult = SQLite.select().from(MoviesResult.class)
                        .where(MoviesResult_Table.id.eq(this.item.getId())).querySingle();

                if(moviesResult != null){
                    moviesResult.delete();
                }else{
                    this.item.save();
                }

                new StackRemoteViewsFactory(getApplicationContext()).onDataSetChanged();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoritWidget.class));
                if (appWidgetIds.length > 0) {
                    new FavoritWidget().onUpdate(this, appWidgetManager, appWidgetIds);
                }

                if (!isfavorite) {
                    isfavorite = true;
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(getApplicationContext(), R.string.movies_fav_delete_1, Toast.LENGTH_LONG).show();

                } else {
                    isfavorite = false;
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp));
                    Toast.makeText(getApplicationContext(), R.string.movies_fav_delete_0, Toast.LENGTH_LONG).show();
                }
                saveInFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveInFavorite() {
        if (isfavorite) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieColumns.MOVIE_ID, item.getId());
            contentValues.put(MovieContract.MovieColumns.MOVIE_TITLE, item.getOriginalTitle());
            contentValues.put(MovieContract.MovieColumns.MOVIE_DESC, item.getOverview());
            contentValues.put(MovieContract.MovieColumns.MOVIE_IMAGE, item.getPosterPath());
            Uri uri = getContentResolver().insert(MovieContract.CONTENT_URI, contentValues);
            if (uri != null) {
                Log.d(TAG, "Uri " + uri);
            }
        } else {
            Uri uri = MovieContract.CONTENT_URI.buildUpon().appendPath(item.getId().toString()).build();
            getContentResolver().delete(uri, null, null);
        }
    }

    private boolean isFavorite(String id) {
        Uri uri = MovieContract.CONTENT_URI.buildUpon().appendPath(id).build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            return cursor.moveToFirst();
        }

        return false;
    }

}
