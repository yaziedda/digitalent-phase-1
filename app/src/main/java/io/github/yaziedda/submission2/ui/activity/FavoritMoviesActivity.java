package io.github.yaziedda.submission2.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.adapter.MoviesMVVMAdapter;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.MoviesResult_Table;
import io.github.yaziedda.submission2.viewmodles.MoviesViewModels;

public class FavoritMoviesActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    List<MoviesResult> list = new ArrayList<>();
    MoviesMVVMAdapter moviesMVVMAdapter;
    MoviesViewModels moviesViewModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit_movies);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.favorit_film_settings);

        recyclerView = findViewById(R.id.recyclerView);

        moviesViewModels = ViewModelProviders.of(this).get(MoviesViewModels.class);
        moviesViewModels.init();
        loadData();
        setupRecyclerView();
    }

    private void loadData() {
        moviesViewModels.getLocalData();
        moviesViewModels.getLocalData().observe(this, moviesResults -> {
            list.clear();
            list.addAll(moviesResults);
            moviesMVVMAdapter.notifyDataSetChanged();
        });
    }

    private void setupRecyclerView() {
        if (moviesMVVMAdapter == null) {
            moviesMVVMAdapter = new MoviesMVVMAdapter(getApplicationContext(), list, model -> {
                Intent intent = new Intent(getApplicationContext(), DetailMovieActivity.class);
                intent.putExtra(MoviesResult.class.getName(), model);
                startActivity(intent);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(moviesMVVMAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            moviesMVVMAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
