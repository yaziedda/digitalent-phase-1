package io.githu.yazied.submission.favorit;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import io.githu.yazied.submission.favorit.adapter.MoviesMVVMAdapter;
import io.githu.yazied.submission.favorit.database.DatabaseContract;

import static io.githu.yazied.submission.favorit.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView recyclerView;
    List<MoviesResult> list = new ArrayList<>();
    MoviesMVVMAdapter moviesMVVMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        setupRecyclerView();


        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieColumns.MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieColumns.MOVIE_TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieColumns.MOVIE_DESC));
            String img = cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieColumns.MOVIE_IMAGE));
            MoviesResult moviesResult = new MoviesResult();
            moviesResult.setId(Integer.valueOf(id));
            moviesResult.setTitle(title);
            moviesResult.setOverview(desc);
            moviesResult.setPosterPath(img);
            list.add(moviesResult);
            System.out.println("Cursor -> id : "+id+", title : "+title+", desc : "+desc+", img : "+img);
        }

        moviesMVVMAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        if (moviesMVVMAdapter == null) {
            moviesMVVMAdapter = new MoviesMVVMAdapter(getApplicationContext(), list, new MoviesMVVMAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(MoviesResult model) {

                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(moviesMVVMAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            moviesMVVMAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
