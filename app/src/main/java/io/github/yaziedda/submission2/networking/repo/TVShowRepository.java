package io.github.yaziedda.submission2.networking.repo;


import android.arch.lifecycle.MutableLiveData;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.model.ResponseTVShow;
import io.github.yaziedda.submission2.model.TVShowResult;
import io.github.yaziedda.submission2.networking.ApiInterface;
import io.github.yaziedda.submission2.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowRepository {

    private static TVShowRepository repository;

    public static TVShowRepository getInstance(){
        if (repository == null){
            repository = new TVShowRepository();
        }
        return repository;
    }

    private ApiInterface apiInterface;

    public TVShowRepository(){
        apiInterface = RetrofitService.cteateService(ApiInterface.class);
    }

    public MutableLiveData<ResponseTVShow> getData(String key, String lang){
        final MutableLiveData<ResponseTVShow> newsData = new MutableLiveData<>();
        apiInterface.getTVShowList(key, lang).enqueue(new Callback<ResponseTVShow>() {
            @Override
            public void onResponse(Call<ResponseTVShow> call,
                                   Response<ResponseTVShow> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTVShow> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<ResponseTVShow> getDataSearch(String key, String lang, String search){
        final MutableLiveData<ResponseTVShow> newsData = new MutableLiveData<>();
        apiInterface.getTVShowListSearch(key, lang, search).enqueue(new Callback<ResponseTVShow>() {
            @Override
            public void onResponse(Call<ResponseTVShow> call,
                                   Response<ResponseTVShow> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTVShow> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<List<TVShowResult>> getLocalData(){
        final MutableLiveData<List<TVShowResult>> newsData = new MutableLiveData<>();
        List<TVShowResult> list = SQLite.select().from(TVShowResult.class).queryList();
        newsData.setValue(list);
        return newsData;

    }
}
