package com.example.electro.repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.electro.api.ConfigApi;
import com.example.electro.api.UsuarioApi;
import com.example.electro.entity.GenericResponse;
import com.example.electro.entity.service.Usuario;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static  UsuarioRepository getInstance(){
        if(repository==null){
            repository=new UsuarioRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String contrasenia) {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("pass", contrasenia);

        this.api.login(params).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                } else {
                    // Procesar la respuesta con error
                    mld.setValue(new GenericResponse<>());
                    System.out.println("Se ha producido un error al iniciar sesión: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                // Procesar el error de la llamada
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return mld;
    }

    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.save(u).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }



}

