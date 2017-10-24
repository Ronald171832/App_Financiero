package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.proyectosi2.multibanco.multibanco.R;
import com.proyectosi2.multibanco.multibanco.rutaWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class PerfilFragment extends Fragment {

    private View parentView;
    SharedPreferences sharedPreferences;
    String correo;
    TextView tv_nombre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_perfil, container, false);
        //setUpViews();
        iniciar();
        cargarDatos();
        return parentView;
    }

    private void iniciar() {
    tv_nombre=(TextView)parentView.findViewById(R.id.tv_nombre_perfil);

    }

    private void cargarDatos() {
        sharedPreferences = getActivity().getSharedPreferences("multibanco", MODE_PRIVATE);
        correo = sharedPreferences.getString("correo", "");
        String URL = rutaWS.DATOS + correo;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray jsonArray = root.getJSONArray("datos");
                    String datos= (String) jsonArray.getJSONObject(0).get("paterno");
                    datos=datos+" "+ (String) jsonArray.getJSONObject(0).get("nombre")+"\n"+
                            (String) jsonArray.getJSONObject(0).get("ci")+"\n";
                    tv_nombre.setText(datos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

}
