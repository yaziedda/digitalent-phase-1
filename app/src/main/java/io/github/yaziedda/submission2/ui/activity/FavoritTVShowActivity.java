package io.github.yaziedda.submission2.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.adapter.MoviesMVVMAdapter;
import io.github.yaziedda.submission2.adapter.TVShowMVVMAdapter;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.TVShowResult;
import io.github.yaziedda.submission2.viewmodles.MoviesViewModels;
import io.github.yaziedda.submission2.viewmodles.TVShowViewModels;

public class FavoritTVShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TVShowResult> list = new ArrayList<>();
    TVShowMVVMAdapter tvShowMVVMAdapter;
    TVShowViewModels tvShowViewModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit_tvshow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.favorit_film_settings);

        recyclerView = findViewById(R.id.recyclerView);

        tvShowViewModels = ViewModelProviders.of(this).get(TVShowViewModels.class);
        tvShowViewModels.init();
        loadData();
        setupRecyclerView();
    }

    private void loadData() {
        tvShowViewModels.getLocalData();
        tvShowViewModels.getLocalData().observe(this, moviesResults -> {
            list.clear();
            list.addAll(moviesResults);
            tvShowMVVMAdapter.notifyDataSetChanged();
        });
    }

    private void setupRecyclerView() {
        if (tvShowMVVMAdapter == null) {

            tvShowMVVMAdapter = new TVShowMVVMAdapter(getApplicationContext(), list, model -> {
                Intent intent = new Intent(getApplicationContext(), DetailTVShowActivity.class);
                intent.putExtra(TVShowResult.class.getName(), model);
                startActivity(intent);
            });
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerView.setAdapter(tvShowMVVMAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            tvShowMVVMAdapter.notifyDataSetChanged();
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
