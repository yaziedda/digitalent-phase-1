package io.github.yaziedda.submission2.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.adapter.MoviesMVVMAdapter;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.ui.activity.DetailMovieActivity;
import io.github.yaziedda.submission2.viewmodles.MoviesViewModels;

import static io.github.yaziedda.submission2.util.Static.KEY_TVSHOW;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    List<MoviesResult> list = new ArrayList<>();
    MoviesMVVMAdapter moviesMVVMAdapter;
    MoviesViewModels moviesViewModels;
    ProgressBar progressBar;
    EditText etSearch;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String locale = Locale.getDefault().getCountry();
            Toast.makeText(context, "LOCALE CHANGED to " + locale,
                    Toast.LENGTH_LONG).show();
            loadData();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        etSearch = view.findViewById(R.id.et_search);

        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        getActivity().registerReceiver(myReceiver, filter);
        setupRecyclerView();

        moviesViewModels = ViewModelProviders.of(this).get(MoviesViewModels.class);
        moviesViewModels.init();

        if (savedInstanceState  == null){
            loadData();
        }else{
            progressBar.setVisibility(View.GONE);
            list.clear();
            String json = savedInstanceState.getString(KEY_TVSHOW);
            if (json != null && !json.isEmpty()) {
                List<MoviesResult> listBody = new Gson().fromJson(json, new TypeToken<List<MoviesResult>>() {
                }.getType());
                list.addAll(listBody);
            }
        }

        initSearch();

    }

    private void initSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2){
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    String locale = Locale.getDefault().getCountry();
                    if (locale.equals("ID")) {
                        moviesViewModels.getRepositorySearch("id-ID", s.toString()).observe(MoviesFragment.this, responseMovies -> {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            if (responseMovies != null){
                                List<MoviesResult> moviesResultList = responseMovies.getResults();
                                list.clear();
                                list.addAll(moviesResultList);
                                moviesMVVMAdapter.notifyDataSetChanged();
                            }
                        });
                    }else{
                        moviesViewModels.getRepositorySearch("en-US", s.toString()).observe(MoviesFragment.this, responseMovies -> {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            if (responseMovies != null){
                                List<MoviesResult> moviesResultList = responseMovies.getResults();
                                list.clear();
                                list.addAll(moviesResultList);
                                moviesMVVMAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }else{
                    loadData();
                }
            }
        });
    }

    private void loadData() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String locale = Locale.getDefault().getCountry();
        if (locale.equals("ID")) {
            moviesViewModels.getRepository("id-ID").observe(this, responseMovies -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (responseMovies != null){
                    List<MoviesResult> moviesResultList = responseMovies.getResults();
                    list.addAll(moviesResultList);
                    moviesMVVMAdapter.notifyDataSetChanged();
                }
            });
        }else{
            moviesViewModels.getRepository("en-US").observe(this, responseMovies -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (responseMovies != null){
                    List<MoviesResult> moviesResultList = responseMovies.getResults();
                    list.addAll(moviesResultList);
                    moviesMVVMAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setupRecyclerView() {
        if (moviesMVVMAdapter == null) {
            moviesMVVMAdapter = new MoviesMVVMAdapter(getContext(), list, model -> {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(MoviesResult.class.getName(), model);

                startActivity(intent);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(moviesMVVMAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            moviesMVVMAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver != null){
            getActivity().unregisterReceiver(myReceiver);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String json = new Gson().toJson(list);
        outState.putString(KEY_TVSHOW, json);
        super.onSaveInstanceState(outState);
    }
}
