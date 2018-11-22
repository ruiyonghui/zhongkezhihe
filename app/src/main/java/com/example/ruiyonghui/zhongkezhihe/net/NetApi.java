package com.example.ruiyonghui.zhongkezhihe.net;

public class NetApi
{
    private NetApiService netApiService;
    private static NetApi netApi;
    public NetApi(NetApiService netApiService) {
        this.netApiService = netApiService;
    }

    public static NetApi getNetApi(NetApiService netApiService) {
        if (netApi == null)
            netApi = new NetApi( netApiService );
        return netApi;
    }
}
