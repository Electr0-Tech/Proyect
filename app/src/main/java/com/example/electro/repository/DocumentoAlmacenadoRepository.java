package com.example.electro.repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.electro.api.ConfigApi;
import com.example.electro.api.DocumentoAlmacenadoApi;
import com.example.electro.entity.GenericResponse;
import com.example.electro.entity.service.DocumentoAlmacenado;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class DocumentoAlmacenadoRepository {
    private static DocumentoAlmacenadoApi api;
    private  static DocumentoAlmacenadoRepository repository;

    public DocumentoAlmacenadoRepository(){
        this.api= ConfigApi.getDocumentoAlmacenadoApi();
    }

    public static DocumentoAlmacenadoRepository getInstance(){
        if (repository == null){
            repository= new DocumentoAlmacenadoRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<DocumentoAlmacenado>> saveFoto(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<GenericResponse<DocumentoAlmacenado>> mld= new MutableLiveData<>();
        this.api.save(part,requestBody).enqueue(new Callback<GenericResponse<DocumentoAlmacenado>>() {
            @Override
            public void onResponse(Call<GenericResponse<DocumentoAlmacenado>> call, Response<GenericResponse<DocumentoAlmacenado>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DocumentoAlmacenado>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
