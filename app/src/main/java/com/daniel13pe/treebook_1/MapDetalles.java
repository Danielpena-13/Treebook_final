package com.daniel13pe.treebook_1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class MapDetalles extends AppCompatActivity {

    public static final String s="Inicio";
    private GoogleMap mMap;
    private MapView mapView;
    String Inicio, Final;

    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detalles);

        Final = getIntent().getStringExtra("Fin");
        Inicio = getIntent().getStringExtra("Inicio");

        Toast.makeText(MapDetalles.this,Inicio,Toast.LENGTH_SHORT).show();

        MapFragment Navi1 = new MapFragment();
        Bundle bundle = getIntent().getExtras();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Bundle Ini = new Bundle();
        //Ini.putString("Inicio", Inicio);
        Ini.putString("Fin", Final);
        Navi1.setArguments(Ini);




        ft.add(android.R.id.content, Navi1).commit();
    }
}
