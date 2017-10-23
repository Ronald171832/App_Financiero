package com.proyectosi2.multibanco.multibanco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        consultarLogin();
        iniciar();
    }

    private void consultarLogin() {
        sharedPreferences = getSharedPreferences("multibanco", MODE_PRIVATE);
        boolean registro = sharedPreferences.getBoolean("login", false);
        if (registro) {
            startActivity(new Intent(MainActivity.this, Perfil.class));
        }
    }

    private void iniciar() {
        final EditText correo = (EditText) findViewById(R.id.et_login_usuario);
        final EditText contra = (EditText) findViewById(R.id.et_login_contrasena);
        Button boton = (Button) findViewById(R.id.btn_iniciar_sesion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Autentificando Cuenta...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                //  ConsultaPass("http://192.168.0.30:8080/IntroPro/public/alumno/login/" + correo.getText().toString(), contra.getText().toString(), correo.getText().toString());
                ConsultaPass(rutaWS.LOGIN + correo.getText().toString(), contra.getText().toString(), correo.getText().toString());
            }
        });
    }

    private void ConsultaPass(String URL, final String contraC, final String correoC) {
        Log.i("url", "" + URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray ja = root.getJSONArray("login");
                    String contrasena = (String) ja.getJSONObject(0).get("password");
                    String correo = (String) ja.getJSONObject(0).get("correo");
                    String nombre = (String) ja.getJSONObject(0).get("nombre");
                    if (correo.equals(correoC)) {
                        if (contrasena.equals(contraC)) {
                            sharedPreferences.edit().putBoolean("login", true).commit();
                            sharedPreferences.edit().putString("correo", correo).commit();
                            Toast.makeText(getApplicationContext(), "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Perfil.class);
                            startActivity(intent);
                            pDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Verifique su contraseña " + nombre, Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "El usuario no existe en la base de datos", Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "El usuario no existe en la base de datos :(", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Problemas de Internet verifique su conexion!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }
}
