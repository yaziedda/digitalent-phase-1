package io.github.yaziedda.submission2.networking.repo;


import android.arch.lifecycle.MutableLiveData;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.networking.ApiInterface;
import io.github.yaziedda.submission2.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository repository;

    public static MoviesRepository getInstance(){
        if (repository == null){
            repository = new MoviesRepository();
        }
        return repository;
    }

    private ApiInterface apiInterface;

    public MoviesRepository(){
        apiInterface = RetrofitService.cteateService(ApiInterface.class);
    }

    public MutableLiveData<ResponseMovies> getData(String key, String lang){
        final MutableLiveData<ResponseMovies> newsData = new MutableLiveData<>();
        apiInterface.getMoviesList(key, lang).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call,
                                   Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<ResponseMovies> getDataSearch(String key, String lang, String search){
        final MutableLiveData<ResponseMovies> newsData = new MutableLiveData<>();
        apiInterface.getMoviesListSearch(key, lang, search).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call,
                                   Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<List<MoviesResult>> getLocalData(){
        final MutableLiveData<List<MoviesResult>> newsData = new MutableLiveData<>();
        List<MoviesResult> list = SQLite.select().from(MoviesResult.class).queryList();
        newsData.setValue(list);
        return newsData;

    }
}
