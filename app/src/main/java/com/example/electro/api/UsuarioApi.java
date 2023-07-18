package com.example.electro.api;

import com.example.electro.entity.GenericResponse;
import com.example.electro.entity.service.Usuario;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioApi {
    //RUTA DEL SERVICIO
    String base="/api/usuario";

    //RUTA DEL CONTROLADOR USUARIO + LA RUTA DEL METODO LOGIN

    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login (@Body Map<String, String> params);

    @POST(base)
    Call<GenericResponse<Usuario>> save(@Body Usuario u);


}