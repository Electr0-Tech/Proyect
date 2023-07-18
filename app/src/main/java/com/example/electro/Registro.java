package com.example.electro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electro.databinding.ActivityRegistroBinding;
import com.example.electro.entity.service.Cliente;
import com.example.electro.entity.service.Usuario;
import com.example.electro.viewmodel.ClienteViewModel;
import com.example.electro.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Registro extends AppCompatActivity {

    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;

    private Button btnGuardarDatos;
    private CircleImageView imageUser;
    private AutoCompleteTextView dropdownDepartamento, dropdownProvincia,dropdownDistrito;
    private EditText edtNameuser,edtTelefonoU,edtEdadU,edtDireccionU,edtPasswordUser,edtEmailUser;
    private EditText txtInputNameUser,txtInputDepartamento,txtInputProvincia,txtInputDistrito,txtInputTelefonoU,txtInputEdad,txtInputDireccionU,txtInputEmailUser,txtInputPasswordUser;
    private  final static int LOCATION_REQUEST_CODE=23;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ActivityRegistroBinding binding = ActivityRegistroBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            binding.topLinearLayout.setAnimation(bottom_down);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    binding.cardView.setAnimation(fade_in);
                    binding.cardView2.setAnimation(fade_in);
                    binding.textView.setAnimation(fade_in);
                }
            };
            handler.postDelayed(runnable, 1000);


        this.init();
        this.initViewModel();
        /*
        final TextView signUpBtn = findViewById(R.id.btnRegistrar);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });*/
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.clienteViewModel=vmp.get(ClienteViewModel.class);
        this.usuarioViewModel=vmp.get(UsuarioViewModel.class);
    }

    private void init(){
        btnGuardarDatos=findViewById(R.id.btnRegistrar);
        edtNameuser=findViewById(R.id.txtInputNameUser);
        edtTelefonoU=findViewById(R.id.txtInputTelefono);
        edtPasswordUser=findViewById(R.id.txtInputPass);
        edtEmailUser=findViewById(R.id.txtInputEmail);
        edtEdadU=findViewById(R.id.txtInputEdad);

        btnGuardarDatos.setOnClickListener(v ->{
            this.guardarDatos();
        });

    }

    private boolean validar(){
        boolean retorno = true;
        String nombres, telefono, correo, clave,
                 dropDepartamento, edad;
        nombres = edtNameuser.getText().toString();
        telefono = edtTelefonoU.getText().toString();
        correo = edtEmailUser.getText().toString();
        clave = edtPasswordUser.getText().toString();
        edad=edtEdadU.getText().toString();
        if (nombres.isEmpty()) {

            retorno = false;
        }
        if (edad.isEmpty()) {
            retorno = false;
        }
        if (telefono.isEmpty()) {
            retorno = false;
        }
        if (correo.isEmpty()) {
            retorno = false;
        }
        if (clave.isEmpty()) {
            retorno = false;
        }

        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }


    private void guardarDatos() {
        Cliente c;
        if(validar()){
            c = new Cliente();
            try {
                c.setNombres(edtNameuser.getText().toString());
                c.setEdad(edtEdadU.getText().toString());
                c.setTelefono(edtTelefonoU.getText().toString());
                c.setId(0);

                this.clienteViewModel.guardarCliente(c).observe(this,cResponse->{
                    if(cResponse.getRpta()==1){
                        int idc= cResponse.getBody().getId();//Se obtiene el id para el usuario
                        Usuario u = new Usuario();
                        u.setEmail(edtEmailUser.getText().toString());
                        u.setClave(edtPasswordUser.getText().toString());
                        u.setCliente(new Cliente(idc));
                        u.setVigencia(true);
                        this.usuarioViewModel.save(u).observe(this,uResponse ->{

                            if(uResponse.getRpta()==1){
                                successMessage("Felicidades"+"Su información ha sigo guardada");
                                //Toast.makeText(this,"Sus datos y credenciales son credos",Toast.LENGTH_SHORT).show();
                                //this.finish();
                                limpiarCampos();
                                Intent intent = new Intent(this, Login.class);
                                startActivity(intent);
                            }else{
                                errorMessage("Error");
                            }
                        });
                    }else{
                        Toast.makeText(this, "No se ha podido guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                });




            }catch (Exception e){
                warningMessage("Se ha producido un error "+e.getMessage());

            }
        }else{
            errorMessage("POr favor complete todos los campos");
        }

    }

    private void limpiarCampos() {
        edtNameuser.setText("");
        edtTelefonoU.setText("");
        edtEmailUser.setText("");
        edtPasswordUser.setText("");
        edtEdadU.setText("");
    }

}
