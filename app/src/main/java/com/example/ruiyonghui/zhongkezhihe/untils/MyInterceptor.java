package com.example.ruiyonghui.zhongkezhihe.untils;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {
    private Response response;
    @Override
    public Response intercept(Chain chain) throws IOException {

        //区分是GET请求还是POST请求
        Request originRequest = chain.request();
        if ("GET".equals(originRequest.method())) {
            HttpUrl httpUrl = originRequest.url()
                    .newBuilder()
//                    .addQueryParameter( "version","2.0" )
                    .build();
            Request request = new Request.Builder().url(httpUrl).build();
            //发送请求
            response = chain.proceed(request);
        }
        else if ("POST".equals(originRequest.method())) {
            //POST请求
            FormBody.Builder builder = new FormBody.Builder();
            //获取原始的请求体里的参数
            RequestBody body1 = originRequest.body();
            if(body1 instanceof FormBody){
            FormBody formBody = (FormBody) originRequest.body();
            //遍历原始的请求体里的参数
            for (int i = 0; i < formBody.size(); i++) {
                builder.add(formBody.name(i), formBody.value(i));
            }
//            //添加新参数
//            builder.add("source", "android");
//            builder.add("version","2.0");
            FormBody body = builder.build();
            //添加请求的参数
            Request request = originRequest.newBuilder()
                    .url(originRequest.url())
                    .post(body)
                    .build();
            response = chain.proceed(request);
            }
            else {
                response = chain.proceed(originRequest);
            }

        }
        else{
            response = chain.proceed(originRequest);
        }
        return response;
    }
}
