package com.gaurav.budgetplanner.Data.API;

import com.gaurav.budgetplanner.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiInterface apiInterface;

    public static ApiInterface getInstance() {
        if (apiInterface == null) {
            final String BASE_URL = Constants.baseUrl;

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit mretrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiInterface = mretrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

}
