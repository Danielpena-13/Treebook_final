package com.daniel13pe.treebook_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogginActivity extends AppCompatActivity {

    EditText econtra, ecorreo;
    String scontra, scorreo, susuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        ecorreo = findViewById(R.id.ecorreo);
        econtra = findViewById(R.id.econtra);
    }

    public void OnClickInicioSesion(View view){
        if(econtra.getText().toString().equals(scontra) && ecorreo.getText().toString().equals(scorreo)){
            Intent intent3 = new Intent(LogginActivity.this,MainActivity.class);
            intent3.putExtra("correo1",scorreo);
            intent3.putExtra("contra1",scontra);
            intent3.putExtra("nombre",susuario);
            startActivityForResult(intent3,1001);
        }if(ecorreo.getText().toString().equals("")){
            ecorreo.setError("vacio");
        }if(econtra.getText().toString().equals("")){
            econtra.setError("vacio");
        }else if(!ecorreo.getText().toString().equals(scorreo)){
            Toast.makeText(LogginActivity.this,"Correo No Coincide",Toast.LENGTH_SHORT).show();
        }else if(!econtra.getText().toString().equals(scontra)){
            Toast.makeText(LogginActivity.this,"Contraseñea Incorrecta",Toast.LENGTH_SHORT).show();
        }
    }

    public void OnclickRegistrarse(View view) {
        int id = view.getId();
        if(id == R.id.tregistrarse){
            Intent intent = new Intent(LogginActivity.this,RegistroActivity.class);
            intent.putExtra("correo1","");
            intent.putExtra("contra1","");
            intent.putExtra("nombre","");
            startActivityForResult(intent,1000);//inicia con retorno de datos
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Toast.makeText(LogginActivity.this,"recived!",Toast.LENGTH_SHORT).show();
            scorreo = data.getExtras().getString("correo1");
            scontra = data.getExtras().getString("contra1");
        }

        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000 && resultCode == RESULT_OK){
            scorreo = data.getExtras().getString("correo1");
            scontra = data.getExtras().getString("contra1");
            susuario = data.getExtras().getString("nombre");
          //  ecorreo.setText("");
          //  econtra.setText("");

        }else if(requestCode == 1001 && resultCode == RESULT_OK ){
            scorreo = data.getExtras().getString("correo1");
            scontra = data.getExtras().getString("contra1");
            Log.v("Loggin", "CerrarSesion");
          //  ecorreo.setText("");
           // econtra.setText("");
        }else if(requestCode == 1001 && resultCode == 3){
            Toast.makeText(LogginActivity.this,"no se!",Toast.LENGTH_SHORT).show();
            finish();
        }else if(requestCode == 1001 && resultCode == RESULT_CANCELED){
            Toast.makeText(LogginActivity.this,"backPress",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LogginActivity.this,"Cancelado!",Toast.LENGTH_SHORT).show();
            //ecorreo.setText(requestCode+" "+resultCode);
           // econtra.setText("");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
