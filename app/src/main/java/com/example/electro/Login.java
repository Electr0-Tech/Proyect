package com.example.electro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.electro.Registro;

import com.example.electro.databinding.ActivityLoginBinding;
import com.example.electro.entity.service.Usuario;
import com.example.electro.utils.DateSerializer;
import com.example.electro.utils.TimeSerializer;
import com.example.electro.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    private Button botonLogin;
    private UsuarioViewModel viewModel;

    private EditText inputEmail,inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

        binding.topLinearLayout.setAnimation(bottom_down);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.registerLayout.setAnimation(fade_in);
                binding.cardView.setAnimation(fade_in);
                binding.cardView2.setAnimation(fade_in);
                binding.textView.setAnimation(fade_in);
            }
        };
        handler.postDelayed(runnable, 1000);

        this.initViewModel();
        this.init();

        final TextView signUpBtn = findViewById(R.id.textView2);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init(){
        inputEmail=findViewById(R.id.emailU);
        inputPass=findViewById(R.id.passU);
        botonLogin= findViewById(R.id.btnLogin);
        botonLogin.setOnClickListener(v->{

            try {
                if(validar()){
                    viewModel.login(inputEmail.getText().toString(),inputPass.getText().toString()).observe(this,response->{
                        if(response.getRpta()==1){
                            //toastCorrecto(response.getMessage());
                            successMessage(response.getMessage());
                            //Toast.makeText(this,response.getMessage(), Toast.LENGTH_SHORT).show();
                            Usuario u= response.getBody();
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor= preferences.edit();
                            final Gson g = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class,new TimeSerializer())
                                    .create();
                            editor.putString("UsuarioJson",g.toJson(u,new TypeToken<Usuario>(){

                            }.getType()));
                            editor.apply();
                            inputEmail.setText("");
                            inputPass.setText("");
                            startActivity(new Intent(this, MenuActivity.class));


                        }else{
                            warningMessage(response.getMessage());
                            //toastError(response.getMessage());
                            //Toast.makeText(this,"Ocurrio un error"+response.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{
                    warningMessage("Por favor,complete todos los campos.");
                    //toastError("Por favor,complete todos los campos.");
                }
            }catch (Exception e){
                warningMessage("Se ha producido un error al intertar loguearte"+e.getMessage());
                //toastError("Se ha producido un error al intertar loguearte"+e.getMessage());

            }


        });




    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }


    private boolean validar(){
        boolean ret=true;
        String usuario,password;
        usuario=inputEmail.getText().toString();
        password=inputPass.getText().toString();

        if(usuario.isEmpty()){
            ret=false;
        }

        if(password.isEmpty()){
            ret=false;
        }


        return ret;
    }
}





