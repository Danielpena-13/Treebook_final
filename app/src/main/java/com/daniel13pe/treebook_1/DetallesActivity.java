package com.daniel13pe.treebook_1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel13pe.treebook_1.model.Montallantas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetallesActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView.Adapter adapterMontallantas;
    private android.support.design.widget.FloatingActionButton fab3;

    private  android.support.design.widget.CollapsingToolbarLayout image;
    private TextView planta, fami,ncien,descrip;
    ImageView foto2;
    Drawable foto;
    String reto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        setupActionBar();

        image=findViewById(R.id.toolbar_layout);
        planta=findViewById(R.id.tPlanta);
        fami=findViewById(R.id.tFami);
        ncien=findViewById(R.id.tNcien);
        descrip=findViewById(R.id.tDescrip);
        foto2 =findViewById(R.id.ifoto2);
        fab3 = findViewById(R.id.fab3);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Proximante Para Compartir en Redes! ;)" , Snackbar.LENGTH_SHORT)
                      .setAction("Action", null).show();
            }
        });

        if(!getIntent().equals(null)){
            Bundle extras = getIntent().getExtras();
            reto = extras.getString("Arbol");
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("Montallantas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Montallantas montallantas = snapshot.getValue(Montallantas.class);
                        if(montallantas.getNombre().toString().equals(reto)) {
                            Picasso.get().load(montallantas.getFoto()).into(foto2);
                            image.setBackground((Drawable) foto2.getBackground());
                            planta.setText(montallantas.getNombre());
                            fami.setText(montallantas.getValor());
                            ncien.setText(montallantas.getNombrecien());
                            descrip.setText(montallantas.getDescrip());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle("");
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
