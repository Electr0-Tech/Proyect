package com.example.electro.api;

import com.example.electro.utils.DateSerializer;
import com.example.electro.utils.TimeSerializer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    public static  final String baseUrlE="http://192.168.137.108:9090";

    private static Retrofit retrofit;
    private static  String token="";
    private static UsuarioApi usuarioApi;

    private  static ClienteApi clienteApi;
    private static DocumentoAlmacenadoApi documentoAlmacenadoApi;


    static {
        initClient();
    }

    private static void initClient() {
        Gson gson= new GsonBuilder()
                .registerTypeAdapter(Date.class,new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit= new Retrofit.Builder()
                .baseUrl(baseUrlE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getCliente())
                .build();



    }

    private static OkHttpClient getCliente() {
        HttpLoggingInterceptor loggin= new HttpLoggingInterceptor();
        StethoInterceptor stetho= new StethoInterceptor();
        OkHttpClient.Builder builder= new OkHttpClient.Builder();

        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);

        return builder.build();

    }

    public static  void setToken(String value){
        token=value;
        initClient();
    }

    public static UsuarioApi getUsuarioApi(){
        if(usuarioApi==null){
            usuarioApi=retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;

    }

    public static ClienteApi getClienteApi(){
        if(clienteApi==null){
            clienteApi= retrofit.create(ClienteApi.class);
        }
        return clienteApi;
    }

    public static DocumentoAlmacenadoApi getDocumentoAlmacenadoApi(){
        if(documentoAlmacenadoApi==null){
            documentoAlmacenadoApi=retrofit.create(DocumentoAlmacenadoApi.class);
        }

        return documentoAlmacenadoApi;
    }




}
