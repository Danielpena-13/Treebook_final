package com.daniel13pe.treebook_1;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daniel13pe.treebook_1.model.Usuarios;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    EditText econtra, ecorreo;
    String scontra, scorreo, susuario;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient gooogleApiClient;
    private SignInButton btnSignInGoogle;
    int LOGIN_CON_GOOGLE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        inicializar();
        getHashes();

        loginButton = findViewById(R.id.login_button);
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);
        loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();

        //Facebook LogIn Button---------------------------------------------------------------------
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login Facebook", "OK");
               // Toast.makeText(LogginActivity.this,"Facebook OK",Toast.LENGTH_SHORT).show();
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login Facebook", "Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login Facebook", "Error");
                error.printStackTrace();
            }
        });

        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(gooogleApiClient);
                startActivityForResult(i, LOGIN_CON_GOOGLE);
            }
        });
        ecorreo = findViewById(R.id.ecorreo);
        econtra = findViewById(R.id.econtra);
    }

    private void signInFacebook(AccessToken accessToken) {///FACEBOOK-------------
        //Toast.makeText(LogginActivity.this,"face SignIn",Toast.LENGTH_SHORT).show();
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    JumpMain();
                    Toast.makeText(LogginActivity.this,"Sesion con FaceBook Iniciada!",Toast.LENGTH_SHORT).show();
                    Log.d("Sesion", "Sesion iniciada Facebook");
                }else{
                    Toast.makeText(LogginActivity.this,"Error al Iniciar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getHashes() {///FACEBOOK----------
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.daniel13pe.treebook_1",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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

    public void OnClickInicioSesion(View view){

        if(econtra.getText().toString().equals(scontra) && ecorreo.getText().toString().equals(scorreo)){
            iniciarsecion(scorreo, scontra);
        }if(ecorreo.getText().toString().equals("")){
            ecorreo.setError("vacio");
        }if(econtra.getText().toString().equals("")) {
            econtra.setError("vacio");
        }else if(!ecorreo.getText().toString().equals(scorreo)){
            Toast.makeText(LogginActivity.this,"Correo No Coincide",Toast.LENGTH_SHORT).show();
        }else if(!econtra.getText().toString().equals(scontra)){
            Toast.makeText(LogginActivity.this,"Contraseñea Incorrecta",Toast.LENGTH_SHORT).show();
        }else{

        }
    }

    private void iniciarsecion(String correo, String contrasena) {
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogginActivity.this,"Sesion iniciada con Exito!",Toast.LENGTH_SHORT).show();
                            Log.d("Sesion", "Sesion iniciada");
                            JumpMain();
                        }else{
                            Toast.makeText(LogginActivity.this,"Error al Iniciar Facebook",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void crearUsuario() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuarios").child(firebaseUser.getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(LogginActivity.this,"Ya Existe!!",Toast.LENGTH_SHORT).show();
                        }else{
                            Usuarios usuarios1 = new Usuarios(firebaseUser.getUid(),
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getPhoneNumber(),
                                    firebaseUser.getEmail());
                            databaseReference.child("Usuarios").child(firebaseUser.getUid()).setValue(usuarios1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        }else if(requestCode == LOGIN_CON_GOOGLE){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);

        }else{//Logeo con Facebook
            //Toast.makeText(LogginActivity.this,"Cancelado!",Toast.LENGTH_SHORT).show();
            //ecorreo.setText(requestCode+" "+resultCode);
           // econtra.setText("");
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void signInGoogle(GoogleSignInResult googleSignInResult) {
        if(googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            JumpMain();
                        }
                    });
        } else{
            Toast.makeText(LogginActivity.this,"No Exitosa con google",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void JumpMain() {
        crearUsuario();
        Intent intent3 = new Intent(LogginActivity.this,NavigatorActivity.class);
        startActivity(intent3);
        finish();
    }

    public void onClickFace(View view) {
    }

    public void OnClickGmail(View view) {
    }

}
