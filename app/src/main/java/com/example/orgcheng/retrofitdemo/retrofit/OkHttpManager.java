package com.example.orgcheng.retrofitdemo.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.orgcheng.retrofitdemo.App;
import com.example.orgcheng.retrofitdemo.cookie.CookieManger;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by orgcheng on 18-1-27.
 */

public class OkHttpManager {
    private static final String TAG = "OkHttpManager";
    private volatile static OkHttpClient sOkHttpClient;

    public static OkHttpClient getOkHttpClient(Context context) {
        if (sOkHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (sOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    sOkHttpClient = builder.connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .addInterceptor(sLogInterceptor)
                            .cookieJar(new CookieManger(App.getAppContext()))   // 持久化Cookie
                            .sslSocketFactory(createSSLSocketFactory())   // 这两个设置，避免访问类似12306报错
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .build();

                }
            }
        }
        return sOkHttpClient;
    }

    private static Interceptor sLogInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间
            Log.d(TAG, String.format("发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            Log.d(TAG, String.format("接收响应: [%s] %n耗时:%.1fms%n%s %n返回:【%s】",
                    response.request().url(),
                    (t2 - t1) / 1e6d,
                    response.headers(),
                    responseBody.string()));

            return response;
        }
    };

    static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

}
