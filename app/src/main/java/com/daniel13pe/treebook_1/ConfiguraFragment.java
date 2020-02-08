package com.daniel13pe.treebook_1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguraFragment extends Fragment {

    Button cambio;
    NavigatorActivity navigatorActivity =new NavigatorActivity();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private GoogleApiClient gooogleApiClient;

    public ConfiguraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view= inflater.inflate(R.layout.fragment_configura, container, false);
        cambio = view.findViewById(R.id.bcambiar);

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }



    public void cerrarSesion() {
        firebaseAuth.signOut();
        if(Auth.GoogleSignInApi != null){
            Auth.GoogleSignInApi.signOut(gooogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        goLoggin();
                    }else{
                        Toast.makeText(getActivity(),"Error al Cerrar Cuenta Gmail!",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } if(LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
    }

    private void goLoggin() {
        Intent intent = new Intent(getActivity(),LogginActivity.class);
        startActivity(intent);
    }


}
