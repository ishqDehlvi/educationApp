package com.mp.neetjeeiitprep.network;

import com.google.gson.Gson;
import com.mp.neetjeeiitprep.Constant;

import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getApiInstance() {
        /*if (retrofit == null) {

            *//*Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
*//*
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;*/

        HttpLoggingInterceptor interceptor =new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient =new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60 / 2, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(null)
                .build();

        Gson gson =new GsonBuilder()
                .setLenient()
                .create();

        retrofit =new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }

}
