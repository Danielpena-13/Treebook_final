package com.daniel13pe.treebook_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel13pe.treebook_1.model.Usuarios;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class NavigatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    FragmentManager fm;
    FragmentTransaction ft;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private  FirebaseUser firebaseUser;
    private GoogleApiClient gooogleApiClient;
    private SignInButton btnSignInGoogle;
    int LOGIN_CON_GOOGLE=1;

    private  ImageView UserImage;
    private TextView UserEmail;
    private  FloatingActionButton fab;

    private DatabaseReference databaseReference;

    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){
            Intent i = new Intent(NavigatorActivity.this, LogginActivity.class);
            startActivity(i);
            finish();
        }

        name = Environment.getExternalStorageDirectory()+"/foto.jpg";
        fab = findViewById(R.id.fab);

        inicializar();
        vibracion();

        MontallantasFragment Navi1 = new MontallantasFragment();
        ft.add(R.id.contenedorFrame, Navi1).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri salida = Uri.fromFile(new File(name));
                i.putExtra(MediaStore.EXTRA_OUTPUT,salida);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Edicion Parametros Navigation!!
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        //Correo:
        TextView UserName = (TextView) headerView.findViewById(R.id.textView);
        UserName.setText(firebaseUser.getEmail().toString());
        //Foto:
        ImageView UserPhoto = (ImageView) headerView.findViewById(R.id.imageView);
        Picasso.get().load(firebaseUser.getPhotoUrl()).into(UserPhoto);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void vibracion() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(130,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(130);
        }
    }

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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gooogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        firebaseAuth.signOut();
        if(Auth.GoogleSignInApi != null){
            Auth.GoogleSignInApi.signOut(gooogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        goLoggin();
                    }else{
                        Toast.makeText(NavigatorActivity.this,"Error al Cerrar Cuenta Gmail!",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } if(LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
    }

    private void goLoggin() {
        Intent intent = new Intent(NavigatorActivity.this,LogginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_hallazgo) {
            MontallantasFragment Navi1 = new MontallantasFragment();
            ft.replace(R.id.contenedorFrame, Navi1).commit();
        } else if (id == R.id.nav_busqueda) {
            BusquedaFragment Navi2 = new BusquedaFragment();
            ft.replace(R.id.contenedorFrame, Navi2).commit();
        } else if (id == R.id.nav_recorrido) {
            RecorridoFragment Navi3 = new RecorridoFragment();
            ft.replace(R.id.contenedorFrame, Navi3).commit();
        } else if (id == R.id.nav_notifi) {
            NotificacionFragment Navi4 = new NotificacionFragment();
            ft.replace(R.id.contenedorFrame, Navi4).commit();
            //Intent intent2 = new Intent(NavigatorActivity.this,MontallantasActivity.class);
            //startActivity(intent2);
        } else if (id == R.id.nav_confi) {
           // ConfiguraFragment Navi5 = new ConfiguraFragment();
           // ft.replace(R.id.contenedorFrame, Navi5).commit();
            Intent intent = new Intent(NavigatorActivity.this,PruebaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_salir) {
            cerrarSesion();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
        gooogleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gooogleApiClient.stopAutoManage(this);
        gooogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gooogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gooogleApiClient.stopAutoManage(this);
        gooogleApiClient.disconnect();
    }
}
