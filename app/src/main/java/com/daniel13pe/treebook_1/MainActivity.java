package com.daniel13pe.treebook_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    String scorreo, scontra, susuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        scorreo = extras.getString("correo1");
        scontra = extras.getString("contra1");
        susuario = extras.getString("nombre");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent7 = new Intent(MainActivity.this,LogginActivity.class);
        intent7.putExtra("correo1",scorreo);
        intent7.putExtra("contra1",scontra);
        setResult(RESULT_OK, intent7);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.mMiperfil){
            Intent intent6 = new Intent(MainActivity.this,PerfilActivity.class);
            intent6.putExtra("correo1",scorreo);
            intent6.putExtra("contra1",scontra);
            intent6.putExtra("nombre",susuario);
            startActivityForResult(intent6,1002);
        }if(id == R.id.mClose){
            Intent intent = new Intent(MainActivity.this,LogginActivity.class);
            intent.putExtra("correo1",scorreo);
            intent.putExtra("contra1",scontra);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
