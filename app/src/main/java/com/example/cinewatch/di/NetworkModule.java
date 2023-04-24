package com.example.cinewatch.di;

import com.example.cinewatch.Utils.Constants;
import com.example.cinewatch.network.MovieApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abhinav Singh on 11,June,2020
 */

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    
    @Provides
    @Singleton
    public static HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return  new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    public static OkHttpClient okHttpClient (HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    @Provides
    @Singleton
    public static MovieApiService provideMovieApiService(OkHttpClient okHttpClient){
        return  new Retrofit.Builder()
                .baseUrl(Constants.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MovieApiService.class);
    }



}
