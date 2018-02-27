package com.example.orgcheng.retrofitdemo.retrofit;

import com.example.orgcheng.retrofitdemo.bean.CollectionBean;
import com.example.orgcheng.retrofitdemo.bean.UserBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
API的数据来源

https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650825048&idx=1&sn=a69c0888efcb35937fc7023ef1c9ceb5&chksm=80b7b5c6b7c03cd0443a93498b7ab74c73655adce8a98f0153bca0cab480ac5ea02623d9a67e&mpshare=1&scene=23&srcid=0227EjDYShhK0GQ6LbAAcnzv#rd

*/

public interface SearchApi {


    /*
    登录

    http://www.wanandroid.com/user/login
    方法：POST
    参数：
    username，password
    */

    @FormUrlEncoded
    @POST("user/login")
    Call<UserBean> login(@Field("username") String username, @Field("password") String password);


    // 注意所有收藏相关都需要登录操作，建议登录将返回的cookie（其中包含账号、密码）持久化到本地即可。
    /*
    收藏文章列表

    http://www.wanandroid.com/lg/collect/list/0/json
    方法：GET
    参数： 页码：拼接在链接中，从0开始。
    */
    @GET("lg/collect/list/0/json")
    Call<CollectionBean> getCollection();
}
