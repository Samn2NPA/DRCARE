package com.tvnsoftware.drcare.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvnsoftware.drcare.Utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thieusike on 7/10/2017.
 */

public class RetrofitManager {
    private static RetrofitManager sRetrofitManager;
    private static RestApiEndpointInterface restApiEndpointInterface;
    private OkHttpClient okHttpClient;

    public static RetrofitManager getInstance() {
        if (null == sRetrofitManager) {
            sRetrofitManager = new RetrofitManager();
        }
        return sRetrofitManager;
    }

    public void config(Context context) {
        okHttpClient = null;
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RestApiEndpointInterface getRestApiEndpointInterface() {
        if (null == restApiEndpointInterface) {
            restApiEndpointInterface = initialRetrofit(Constants.BASE_URL);
        }
        return restApiEndpointInterface;
    }

    public RestApiEndpointInterface initialRetrofit(String movieUrl) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(movieUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient)
                .build();
        return retrofit.create(RestApiEndpointInterface.class);
    }

    public <T> void sendApiRequest(Call<T> call, final CommonInterface.ModelResponse<T> callBack) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callBack.onFail();
            }
        });
    }
}
