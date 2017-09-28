package com.example.customrecycle.frame.retrofit;


import android.util.Base64;

import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.utils.AESUtil;
import com.example.customrecycle.frame.utils.ConfigUtils;
import com.example.customrecycle.frame.utils.JsonUtil;
import com.example.customrecycle.frame.utils.PreferencesUtils;
import com.example.customrecycle.frame.utils.StringUtils;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhkj on 16/3/11.
 */
public class HttpRequest {

    private static Retrofit retrofitInstance;

    public static Retrofit GetRetrofitInstance(String server) {
        if (retrofitInstance == null) {
            MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor();
//            if (BaseApp.DEBUG) {//DEBUG模式打印日志
//                loggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY);
//            } else {//非DEBUG模式不打印日志
//                loggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.NONE);
//            }
            loggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            FormBody formBody = null;
                            RequestBody requestBody = original.body();
                            try {
                                formBody = (FormBody) original.body();
                            } catch (Exception e) {
                            }

                            //获取url地址
                            String url = original.url().url().toString();

                            //返回的数据
                            Response response = null;
//                            if (BaseApp.DEBUG) {//调试模式  不加密
                                Request request = original.newBuilder()
//                                        .header("TokenId", PreferencesUtils.getString(BaseApp.getContext(), "tokenid", ""))
                                        .method(original.method(), requestBody)
                                        .build();
                                response = chain.proceed(request);
//                            } else {//发布模式 加密
//                                Request request;
//                                if (url.contains("File/")
//                                        || url.contains("file/")
//                                        ) {//不加密
//                                    request = original.newBuilder()
//                                            .header("TokenId", PreferencesUtils.getString(BaseApp.getContext(), "tokenid", ""))
//                                            .method(original.method(), requestBody)
//                                            .build();
//                                    response = chain.proceed(request);
//                                } else if (url.contains("User/Login")//登录
//                                        || url.contains("User/Regedit")//注册
//                                        || url.contains("User/ResetUserPws")//重置密码 忘记密码修改密码
//                                        || url.contains("User/GetSmsCode")//修改用户手机之前发送验证码
//                                        ) {//BASE64加密
//                                    String content = "";
//                                    if (formBody != null) {
//                                        content = getJson(formBody);
//                                    } else {
//                                        content = getJson(requestBody);
//                                    }
//                                    //加密
//                                    content = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
//                                    content = StringUtils.replaceBlank(content);
//                                    RequestBody requestBodyTemp = RequestBody.create(MediaType.parse("text/plain"), content);
//                                    request = original.newBuilder()
//                                            .method(original.method(), requestBodyTemp)
//                                            .build();
//                                    response = chain.proceed(request);
//
//                                    //解码
//                                    String result = response.body().string();
//                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain"), Base64.decode(result, Base64.DEFAULT));
//                                    response = response.newBuilder().body(responseBody).build();
//                                } else {//AES加密
//                                    String content = "";
//                                    if (formBody != null) {
//                                        content = getJson(formBody);
//                                    } else {
//                                        content = getJson(requestBody);
//                                    }
//                                    //加密
//                                    String tokenid = PreferencesUtils.getString(BaseApp.getContext(), "tokenid", "");
//                                    content = AESUtil.encrypt(content);
//                                    content = StringUtils.replaceBlank(content);
//                                    String er = "{e:\"" + tokenid + "\",r:\"" + System.currentTimeMillis() + "\"}";
//                                    er = Base64.encodeToString(er.getBytes(), Base64.DEFAULT);//BASE64编码
//                                    er = StringUtils.replaceBlank(er);
//                                    RequestBody requestBodyTemp = RequestBody.create(MediaType.parse("text/plain"), content);
//                                    request = original.newBuilder()
//                                            .header("er", er)
//                                            .method(original.method(), requestBodyTemp)
//                                            .build();
//                                    response = chain.proceed(request);
//                                    //解码
//                                    String result = response.body().string();
//                                    result = AESUtil.desEncrypt(result);
//                                    if (url.contains("User/LogOut")//注销登录 要用base64
//                                            ) {
//                                        result = new String(Base64.decode(result, Base64.DEFAULT), "utf-8");
//                                    }
//                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain"), result);
//                                    response = response.newBuilder().body(responseBody).build();
//                                }
//                            }
                            return response;
                        }
                    })
                    .addInterceptor(loggingInterceptor)//添加拦截器
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            retrofitInstance = new Retrofit.Builder().baseUrl(server).client(client)
//                    .addConverterFactory(new UserConverterFactory())
//                    .addConverterFactory(MyGsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitInstance;
    }

    private static IResource newsIResource;

    public static IResource getIResource() {
        if (newsIResource == null) {
            String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
            newsIResource = GetRetrofitInstance(server).create(IResource.class);
        }
        return newsIResource;
    }

    /**
     * 转换参数为json
     *
     * @param formBody key=value&key1=value1
     * @return json格式字符串
     */
    private static String getJson(FormBody formBody) {
        Buffer buffer = new Buffer();
        try {
            formBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String param = buffer.readString(Charset.forName("UTF-8"));
        try {
            param = URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (JsonUtil.isGoodJson(param)) {
            return param;
        }

        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < formBody.size(); i++) {
            try {
                jsonObject.addProperty(URLDecoder.decode(formBody.encodedName(i), "UTF-8"), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String paramJson = jsonObject.toString().trim();
        return paramJson;
    }

    /**
     * 转换参数为json
     *
     * @param requestBody key=value&key1=value1
     * @return json格式字符串
     */
    private static String getJson(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String param = buffer.readString(Charset.forName("UTF-8"));
        try {
            param = URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (JsonUtil.isGoodJson(param)) {
            return param;
        }

        //通过key=value&key1=value1解析
        JsonObject jsonObject = new JsonObject();
        String[] arr;
        if (StringUtils.isEmpty(param)) {
            arr = new String[]{};
        } else if (param.contains("&")) {
            arr = param.split("&");
        } else {
            if (param.contains("=")) {
                arr = new String[]{param};
            } else {
                arr = new String[]{};
            }
        }
        for (int i = 0; i < arr.length; i++) {
            String[] keyValue = arr[i].split("=");
            jsonObject.addProperty(keyValue[0], keyValue[1]);
        }
        String paramJson = jsonObject.toString().trim();
        return paramJson;
    }
    /*private static Retrofit retrofitInstanceDownload;

    public static Retrofit GetRetrofitInstanceDownload(String server) {
        if (retrofitInstanceDownload == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BaseApp.DEBUG) {//DEBUG模式打印日志
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {//非DEBUG模式不打印日志
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("tokenid", PreferencesUtils.getString(BaseApp.getContext(),"tokenid",""))
                                    .method(original.method(), original.body())
                                    .build();
                            Response proceed = chain.proceed(request);
                            return proceed;
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .build();

            retrofitInstanceDownload = new Retrofit.Builder().baseUrl(server).client(client)
//                    .addConverterFactory(new UserConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofitInstanceDownload;
    }

    private static IResourceDownload newsIResourceDownload;

    public static IResourceDownload getIResourceDownload() {
        if (newsIResourceDownload == null) {
            String server = ConfigUtils.getConfig(ConfigUtils.KEY.serverDownload);
            newsIResourceDownload = GetRetrofitInstanceDownload(server).create(IResourceDownload.class);
        }
        return newsIResourceDownload;
    }*/
}
