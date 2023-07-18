package com.example.electro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.electro.entity.GenericResponse;
import com.example.electro.entity.service.DocumentoAlmacenado;
import com.example.electro.repository.DocumentoAlmacenadoRepository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DocumentoAlmacenadoViewModel extends AndroidViewModel {
    private final DocumentoAlmacenadoRepository repository;
    public DocumentoAlmacenadoViewModel(@NonNull Application application) {
        super(application);
        repository= DocumentoAlmacenadoRepository.getInstance();
    }

    public LiveData<GenericResponse<DocumentoAlmacenado>> save(MultipartBody.Part part, RequestBody requestBody){
        return this.repository.saveFoto(part,requestBody);
    }
}