package com.example.electro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.electro.entity.GenericResponse;
import com.example.electro.entity.service.Usuario;
import com.example.electro.repository.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;
    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        this.repository=UsuarioRepository.getInstance();
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String pass){
        return this.repository.login(email,pass);

    }

    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        return this.repository.save(u);
    }
}
