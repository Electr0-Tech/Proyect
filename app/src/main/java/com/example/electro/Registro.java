package com.example.electro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.example.electro.databinding.ActivityRegistroBinding;

public class Registro extends AppCompatActivity {

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

        final TextView signUpBtn = findViewById(R.id.btnRegistrar);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }
}
