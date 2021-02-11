package io.github.yaziedda.submission2.networking;

import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.model.ResponseTVShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<ResponseMovies> getMoviesList(@Query("api_key") String apiKey, @Query("language") String lang);

    @GET("search/movie")
    Call<ResponseMovies> getMoviesListSearch(@Query("api_key") String apiKey, @Query("language") String lang, @Query("query") String search);

    @GET("discover/movie")
    Call<ResponseMovies> getMoviesListUpcoming(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String dateGte, @Query("primary_release_date.lte") String dateLte);

    @GET("discover/tv")
    Call<ResponseTVShow> getTVShowList(@Query("api_key") String apiKey, @Query("language") String lang);

    @GET("search/tv")
    Call<ResponseTVShow> getTVShowListSearch(@Query("api_key") String apiKey, @Query("language") String lang, @Query("query") String search);
}
