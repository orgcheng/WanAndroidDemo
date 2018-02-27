package com.example.orgcheng.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.orgcheng.retrofitdemo.retrofit.SearchRetrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        SearchRetrofit.getInstance().login();
    }

    public void collection(View view){
        SearchRetrofit.getInstance().getCollection();
    }
}
