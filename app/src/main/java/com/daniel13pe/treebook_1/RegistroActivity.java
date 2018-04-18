package com.daniel13pe.treebook_1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity {

    EditText ecorreo1, econtra1, econtra2, eusuario;
    String scorreo1, scontra1, scontra2, susuario;


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializar();

        eusuario = findViewById(R.id.enombreuser);
        ecorreo1 = findViewById(R.id.ecorreo1);
        econtra1 = findViewById(R.id.econtra1);
        econtra2 = findViewById(R.id.econtra2);

        Bundle extras = getIntent().getExtras();
        scorreo1 = extras.getString("correo1");
        scontra1 = extras.getString("contra1");
        ecorreo1.setText(scorreo1);
        econtra1.setText(scontra1);

        EditText correo1 = (EditText) findViewById(R.id.ecorreo1);
        EditText usuario = (EditText) findViewById(R.id.enombreuser);
        EditText contra1 = (EditText) findViewById(R.id.econtra1);
        EditText contra2 = (EditText) findViewById(R.id.econtra2);

        usuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ImageView icon_correo1 = (ImageView) findViewById(R.id.iusuario);
                    Drawable d = icon_correo1.getDrawable();
                    d = DrawableCompat.wrap(d);
                    DrawableCompat.setTint(d, ContextCompat.getColor(RegistroActivity.this, R.color.darkviolet));
                }
            }
        });

        correo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ImageView icon_correo1 = (ImageView) findViewById(R.id.icorreo1);
                    Drawable d = icon_correo1.getDrawable();
                    d = DrawableCompat.wrap(d);
                    DrawableCompat.setTint(d, ContextCompat.getColor(RegistroActivity.this, R.color.darkviolet));
                }
            }
        });

        contra1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ImageView icon_correo1 = (ImageView) findViewById(R.id.icontra1);
                    Drawable d = icon_correo1.getDrawable();
                    d = DrawableCompat.wrap(d);
                    DrawableCompat.setTint(d, ContextCompat.getColor(RegistroActivity.this, R.color.darkviolet));
                }
            }
        });

        contra2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ImageView icon_correo1 = (ImageView) findViewById(R.id.icontra2);
                    Drawable d = icon_correo1.getDrawable();
                    d = DrawableCompat.wrap(d);
                    DrawableCompat.setTint(d, ContextCompat.getColor(RegistroActivity.this, R.color.darkviolet));
                }
            }
        });
    }

    //FAREBASE!!-------------------------------------
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("FirebaseUser", "Usuario Logeado"+firebaseUser.getEmail());
                }else{
                    Log.d("FirebaseUser", "No hay Usuario Logeado");
                }
            }
        };
    }

    public void OnclickGuardar(View view) {
        susuario = eusuario.getText().toString();
        scorreo1 = ecorreo1.getText().toString();
        scontra1 = econtra1.getText().toString();
        scontra2 = econtra2.getText().toString();

        if(susuario.equals("") || scorreo1.equals("") || scontra1.equals("") || scontra2.equals("")){
            Toast.makeText(RegistroActivity.this,"Campo(s) sin Llenar",Toast.LENGTH_SHORT).show();
        }else if(scontra2.equals(scontra1)){

            crearcuenta(scorreo1,scontra1);

            Intent intent = new Intent();
            intent.putExtra("correo1",scorreo1);
            intent.putExtra("contra1",scontra1);
            intent.putExtra("nombre",susuario);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(RegistroActivity.this,"Contraseñas no Coinciden",Toast.LENGTH_SHORT).show();
            econtra1.setFocusable(true);
        }
    }

    private void crearcuenta(String correo, String contrasena) {
        firebaseAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Log.d("Registro", "Usuario Registrado!!");
                            Toast.makeText(RegistroActivity.this,"Cuenta Creada",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistroActivity.this,"Error al crear",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


}
