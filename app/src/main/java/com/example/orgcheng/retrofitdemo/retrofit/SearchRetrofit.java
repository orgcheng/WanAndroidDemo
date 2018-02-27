package com.example.orgcheng.retrofitdemo.retrofit;

import android.util.Log;

import com.example.orgcheng.retrofitdemo.App;
import com.example.orgcheng.retrofitdemo.bean.CollectionBean;
import com.example.orgcheng.retrofitdemo.bean.UserBean;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orgcheng on 18-1-27.
 */

public class SearchRetrofit {
    private static final String TAG = "SearchRetrofit";

    private String mHostUrl = "http://www.wanandroid.com/";
    private Retrofit mRetrofit;

    static class Holder {
        public static SearchRetrofit sInstance = new SearchRetrofit();
    }

    public static SearchRetrofit getInstance() {
        return Holder.sInstance;
    }

    private SearchRetrofit() {
        OkHttpClient okHttpClient = OkHttpManager.getOkHttpClient(App.getAppContext());
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mHostUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void login() {
        Log.e(TAG, "login: ");

        SearchApi searchApi = mRetrofit.create(SearchApi.class);
        Call<UserBean> call = searchApi.login("orgcheng", "justok05");
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()) );
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
            }
        });

    }

    public void getCollection() {
        Log.e(TAG, "getCollection: ");

        SearchApi searchApi = mRetrofit.create(SearchApi.class);
        Call<CollectionBean> call = searchApi.getCollection();
        call.enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()) );
            }

            @Override
            public void onFailure(Call<CollectionBean> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
            }
        });

    }
}
