package io.github.yaziedda.submission2.viewmodles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.model.ResponseTVShow;
import io.github.yaziedda.submission2.model.TVShowResult;
import io.github.yaziedda.submission2.networking.repo.MoviesRepository;
import io.github.yaziedda.submission2.networking.repo.TVShowRepository;
import io.github.yaziedda.submission2.util.Static;

/**
 * Created by yaziedda on 2019-11-11.
 */
public class TVShowViewModels extends ViewModel {

    private MutableLiveData<ResponseTVShow> mutableLiveData;
    private TVShowRepository repository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        repository = TVShowRepository.getInstance();
    }

    public LiveData<ResponseTVShow> getRepository(String lang) {
        return repository.getData(Static.API_KEY, lang);
    }

    public LiveData<ResponseTVShow> getRepositorySearch(String lang, String search) {
        return repository.getDataSearch(Static.API_KEY, lang, search);
    }

    public LiveData<List<TVShowResult>> getLocalData() {
        return repository.getLocalData();
    }

}
