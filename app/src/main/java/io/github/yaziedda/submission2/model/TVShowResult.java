package io.github.yaziedda.submission2.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

import io.github.yaziedda.submission2.util.AppDatabase;

/**
 * Created by yaziedda on 2019-11-12.
 */

@Table(database = AppDatabase.class)
public class TVShowResult extends BaseModel implements Serializable {

    @Column
    @PrimaryKey
    @SerializedName("original_name")
    private String originalName;

    @SerializedName("name")
    @Column
    private String name;

    @SerializedName("popularity")
    @Column
    private Double popularity;

    @SerializedName("vote_count")
    @Column
    private Integer voteCount;

    @SerializedName("first_air_date")
    @Column
    private String firstAirDate;

    @SerializedName("backdrop_path")
    @Column
    private String backdropPath;

    @SerializedName("original_language")
    @Column
    private String originalLanguage;

    @SerializedName("id")
    @Column
    private Integer id;

    @SerializedName("vote_average")
    @Column
    private Double voteAverage;

    @SerializedName("overview")
    @Column
    private String overview;

    @SerializedName("poster_path")
    @Column
    private String posterPath;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
