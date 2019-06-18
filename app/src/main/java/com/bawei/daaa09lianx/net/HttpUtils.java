package com.bawei.daaa09lianx.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bawei.daaa09lianx.api.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:杨帅
 * Date:2019/6/17 9:34
 * Description：
 */
public class HttpUtils {
    //单例模式
    private static HttpUtils httpUtils;
    private final Retrofit retrofit;

    private HttpUtils(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.BASE_URL)
                .build();
    }

    /**
     * 双重检验锁
     * @return
     */
    public static HttpUtils getInstance(){
        if(httpUtils==null){
            synchronized (HttpUtils.class){
                if(httpUtils==null){
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     * 判断网络状态方法
     * @param context
     * @return
     */
    public boolean isNetWork(Context context){
        //获取连接管理者
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取连接状态信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        //判断网络状态
        if(info!=null){
            return info.isConnected();
        }
        return false;
    }

    /**
     * 动态代理获取ApiService
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
