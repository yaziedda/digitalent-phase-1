package io.github.yaziedda.submission2.networking;

import io.github.yaziedda.submission2.util.Static;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Static.HOST)
//    movie?api_key=7b12624313469a963dfa28cedec6e6b6&language=id-US
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
