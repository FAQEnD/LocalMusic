package ua.com.free.localmusic.networkoperations.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public class NetworkServiceFactory {

    private static final String BASE_URL = "http://www.youtubeinmp3.com";
    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static NetworkService create() {
        return sRetrofit.create(NetworkService.class);
    }

}
