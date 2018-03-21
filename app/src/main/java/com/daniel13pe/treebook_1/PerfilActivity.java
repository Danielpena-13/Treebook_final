package com.daniel13pe.treebook_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    String scorreo, scontra,susuario;
    TextView tshower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tshower = findViewById(R.id.tperfil);

        Bundle extras = getIntent().getExtras();
        scorreo = extras.getString("correo1");
        scontra = extras.getString("contra1");
        susuario = extras.getString("nombre");
        tshower.setText("*Nombre Usuario:\n  "+susuario+"\n\n*Correo:\n  "+scorreo+"\n\n*Contraseña:\n  "+scontra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent7 = new Intent(PerfilActivity.this,MainActivity.class);
        intent7.putExtra("correo1",scorreo);
        intent7.putExtra("contra1",scontra);
        startActivityForResult(intent7,1005);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.mPrincipal2){
            Intent intent2 = new Intent(PerfilActivity.this,MainActivity.class);
            intent2.putExtra("correo1",scorreo);
            intent2.putExtra("contra1",scontra);
            startActivityForResult(intent2,1004);
            finish();
        }else if(id == R.id.mcerrar2){
            Intent intent6 = new Intent();
            intent6.putExtra("correo1",scorreo);
            intent6.putExtra("contra1",scontra);
            setResult(13,intent6);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
