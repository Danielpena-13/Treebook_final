package com.daniel13pe.treebook_1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

       vibracion();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scannerView.resumeCameraPreview(QRActivity.this);
            }
        }, 3000);
        //Aqui es donde se van a recibir los resultados del scanner

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del scanner");
        builder.setMessage(result.getText().toString());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        scannerView.stopCamera();

        Intent intent = new Intent(QRActivity.this,NavigatorActivity.class);
        intent.putExtra("Arbol",result.getText());
        startActivity(intent);
        finish();
    }
    private void vibracion() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(130,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(80);
        }
    }
}
