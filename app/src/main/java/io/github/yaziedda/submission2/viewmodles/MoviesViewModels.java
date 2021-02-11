package io.github.yaziedda.submission2.viewmodles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.networking.repo.MoviesRepository;
import io.github.yaziedda.submission2.util.Static;

/**
 * Created by yaziedda on 2019-11-11.
 */
public class MoviesViewModels extends ViewModel {

    private MutableLiveData<ResponseMovies> mutableLiveData;
    private MoviesRepository repository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        repository = MoviesRepository.getInstance();

    }

    public LiveData<ResponseMovies> getRepository(String lang) {
        return repository.getData(Static.API_KEY, lang);
    }

    public LiveData<ResponseMovies> getRepositorySearch(String lang, String search) {
        return repository.getDataSearch(Static.API_KEY, lang, search);
    }


    public LiveData<List<MoviesResult>> getLocalData() {
        return repository.getLocalData();
    }

}
