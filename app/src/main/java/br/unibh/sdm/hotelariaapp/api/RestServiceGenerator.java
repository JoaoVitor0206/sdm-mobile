package br.unibh.sdm.hotelariaapp.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceGenerator {
    //public static final String API_BASE_URL = "http://load-balance-backend-cliente-243964297.us-east-1.elb.amazonaws.com/cliente-api/";
    public static final String API_BASE_URL = "http://10.0.2.2:8080/cliente-api/";

    public static <S> S createService(Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Log.i("RestServiceGenerator","Criada a conexão com a api rest");
        return retrofit.create(serviceClass);
    }

}
