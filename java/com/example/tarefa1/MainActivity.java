package com.example.tarefa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Ponto p1, p2;
    String PROVIDER;

    EditText edtPontoA, edtPontoB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Calculo Distância");
        PROVIDER = LocationManager.GPS_PROVIDER;

        edtPontoA = findViewById(R.id.edtPontoA);
        edtPontoB = findViewById(R.id.edtPontoB);

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1 = new Ponto();
                p2 = new Ponto();
                edtPontoA.setText("");
                edtPontoB.setText("");
            }
        });

        Button btnLerA = findViewById(R.id.btnLerA);
        btnLerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                p1 = getPonto();
                edtPontoA.setText(p1.imprimir());
            }
        });

        Button btnLerB = findViewById(R.id.btnLerB);
        btnLerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                p2 = getPonto();
                edtPontoB.setText(p2.imprimir());
            }
        });

        Button btnDistancia = findViewById(R.id.btnDistancia);
        btnDistancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                float[] resultado = new float[1];
                Location.distanceBetween(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude(), resultado);

                if (mLocManager.isProviderEnabled(PROVIDER)){
                    String texto = "Distância: "+resultado[0] + "\n";
                    Toast.makeText(MainActivity.this, texto, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "GPS DESATIVADO", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnVerA = findViewById(R.id.btnVerA);
        btnVerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarGoogleMaps(p1.getLatitude(), p1.getLongitude());
            }
        });

        Button btnVerB = findViewById(R.id.btnVerB);
        btnVerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarGoogleMaps(p2.getLatitude(), p2.getLongitude());
            }
        });
    }

    public void mostrarGoogleMaps(double latitude, double longitude) {
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + ", " + longitude);
    }

        public Ponto getPonto(){
        LocationManager mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacao();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return null;
        }

        mLocManager.requestLocationUpdates(PROVIDER, 0, 0, mLocListener);
        Location localAtual = mLocManager.getLastKnownLocation(PROVIDER);
        if (! mLocManager.isProviderEnabled(PROVIDER)){
            Toast.makeText(MainActivity.this, "GPS Desabilitado!", Toast.LENGTH_LONG).show();
        }
        return new Ponto(localAtual.getLatitude(), localAtual.getLongitude(), localAtual.getAltitude());

    }
}