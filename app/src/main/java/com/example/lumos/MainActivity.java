package com.example.lumos;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LumosNox();

        Button btnLumos = findViewById(R.id.btnLumos);

        btnLumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LumosNox();
            }
        });
    }

    public void LumosNox(){
        try {
            String retorno = (String) new HttpService().execute().get();
            Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public class HttpService extends AsyncTask {

        @Override
        protected Object doInBackground(Object... objects) {

            String request = "http://192.168.43.180/LED";

            URL url = null;
            try {
                url = new URL(request);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = null;

            try {

                conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                readStream(in);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            } catch (IOException e) {
                String retorno1 = ChamarIpWifi();
                return retorno1;
            }
            finally {
                conn.disconnect();
            }
            return "Lumos/Nox!";
        }

        private void readStream(InputStream in) {
        }

        protected String ChamarIpWifi(){
            String request = "http://192.168.0.20/LED";
            URL url = null;
            try {
                url = new URL(request);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(false);
                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                readStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString() + " - Rede wifi";
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString() + " - Rede wifi";
            }
            finally {
                conn.disconnect();
            }
            return "Lumos/Nox - Rede wifi!";
        }
    }

}
