package com.daniel13pe.treebook_1;


import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    String data1, data2;
    private GoogleMap mMap;
    private MapView mapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        data1 = getArguments().getString("Inicio");
        data2 = getArguments().getString("Fin");

        //Snackbar.make(view, data2, Snackbar.LENGTH_SHORT)
          //      .setAction("Action", null).show();

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (data2.equals("La Nacho")) {

            //MAPA1
            ////////CIUDAD UNIVERSITARIA
            LatLng udea = new LatLng(6.2673691, -75.5688758); //LatLng(6263935,-75567404);
            mMap.addMarker(new MarkerOptions().position(udea).title("Universidad de Antioquia").snippet("Ciudad Universitaria"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udea,16));//se mueve la camara a esa posicion
            ///////LA NACHO
            LatLng nacho = new LatLng(6.2603934, -75.5793742);
            mMap.addMarker(new MarkerOptions().position(nacho).title("Universidad Nacional de Colombia").snippet("La Nacho").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.addPolyline(new PolylineOptions().add(udea, nacho).width(10).color(Color.RED));

        } else if (data2.equals("Saragoza")) {
            //MAPA2
            ///////LA PLAYA
            LatLng playa = new LatLng(6.2496209, -75.5664775);
            mMap.addMarker(new MarkerOptions().position(playa).title("La Playa").snippet("La Playa").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(playa,11));
            //////ZARAGOZA
            LatLng zaragoza = new LatLng(7.484412, -74.8691837);
            mMap.addMarker(new MarkerOptions().position(zaragoza).title("Zaragoza").snippet("Municipio de Zaragoza"));
            mMap.addPolyline(new PolylineOptions().add(playa, zaragoza).width(10).color(Color.RED));
        } else if (data2.equals("Parque Deseos")) {
            //MAPA3
            //////LA ESTRELLA
            LatLng estrella = new LatLng(6.1387493, -75.6577942);
            mMap.addMarker(new MarkerOptions().position(estrella).title("La Estrella").snippet("La Estrella").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

            //////PARQUE DE LOS DESEOS
            LatLng parque = new LatLng(6.2682913, -75.566461);
            mMap.addMarker(new MarkerOptions().position(parque).title("Parque de los Deseos").snippet("Parque Publico"));
            mMap.addPolyline(new PolylineOptions().add(estrella, parque).width(10).color(Color.RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parque, 13));
        } else if (data2.equals("El Aero")){
            //MAPA4
            /////BLOQUE 18
            LatLng bloque18 = new LatLng(6.2677546, -75.5678466);
            mMap.addMarker(new MarkerOptions().position(bloque18).title("Universidad de Antioquia").snippet("Bloque 18"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bloque18,16));
            /////EL AERO
            LatLng aero = new LatLng(6.2689153, -75.5702173);
            mMap.addMarker(new MarkerOptions().position(aero).title("Universidad de Antioquia").snippet("El Aero").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.addPolyline(new PolylineOptions().add(bloque18, aero).width(10).color(Color.RED));
        } else {
            //MAPA5
            //////CERRO QUITASOL
            LatLng quitasol = new LatLng(6.3753363, -75.5491952);
            mMap.addMarker(new MarkerOptions().position(quitasol).title("Quitasol").snippet("Cerro Quitasol").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quitasol,11));
            /////SAN PEDRO
            LatLng pedro = new LatLng(6.4514152, -75.6285426);
            mMap.addMarker(new MarkerOptions().position(pedro).title("San Pedro").snippet("San Pedro de los Milagros"));
            mMap.addPolyline(new PolylineOptions().add(quitasol, pedro).width(10).color(Color.RED));
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
