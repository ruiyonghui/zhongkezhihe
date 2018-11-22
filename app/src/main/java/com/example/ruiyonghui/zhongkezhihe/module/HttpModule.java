package com.example.ruiyonghui.zhongkezhihe.module;
import android.util.Log;


import com.example.ruiyonghui.zhongkezhihe.net.Api;
import com.example.ruiyonghui.zhongkezhihe.net.NetApi;
import com.example.ruiyonghui.zhongkezhihe.net.NetApiService;
import com.example.ruiyonghui.zhongkezhihe.untils.MyInterceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                String text = null;
                try {
                    text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        } );
        //这行必须加 不然默认不打印
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor( new MyInterceptor() )
                .addInterceptor( httpLoggingInterceptor )
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
    }
    @Provides
    Retrofit.Builder provideRetrofit(OkHttpClient.Builder builder){
//        builder.addInterceptor(new MyInterceptor());
        return new Retrofit.Builder()
                .baseUrl( Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build());
    }
    @Provides
    NetApi provideNetApi(Retrofit.Builder builder) {
        Retrofit retrofit = builder.build();
        NetApiService netApiService = retrofit.create( NetApiService.class );
        return NetApi.getNetApi( netApiService );
    }


}
