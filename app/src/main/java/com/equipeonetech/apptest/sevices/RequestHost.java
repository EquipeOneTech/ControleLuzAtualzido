package com.equipeonetech.apptest.sevices;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHost {
    OkHttpClient client = new OkHttpClient();

    String url = "http://192.168.15.26/:3097/medidorLigth";

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }


}
