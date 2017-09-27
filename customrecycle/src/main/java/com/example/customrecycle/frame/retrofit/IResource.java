package com.example.customrecycle.frame.retrofit;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by zbx on 16/1/11.
 */
public interface IResource {

    /**
     * action 拼接的url地址   @Url动态的url地址请求;半静态的url地址请求 @GET("users/{user}/repos") @Path("id") String id
     * map 传入的参数
     */
    @FormUrlEncoded
    @POST
    Call<JsonObject> GetRequest(@Url String action, @FieldMap Map<String, String> map);


    /**
     * Body体
     *
     * @param action
     * @param body
     * @return
     */
    @POST
    Call<JsonObject> GetRequestBody(@Url String action, @Body RequestBody body);

    /**
     * 自动升级  暂时测试使用
     *
     * @param app
     * @return
     */
    @FormUrlEncoded
    @POST("App/Index")
    Call<JsonObject> getNewVersion(@Field("App") String app);


//    https://api.douban.com/v2/movie/top250?start=0&count=10


    @GET("v2/movie/top250?start=0&count=10")
    Call<JsonObject> getTop();

}
