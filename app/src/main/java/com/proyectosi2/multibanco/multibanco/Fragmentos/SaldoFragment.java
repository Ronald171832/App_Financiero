package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SaldoFragment extends Fragment {

    private View parentView;
    ListView listaResultado;
    SharedPreferences sharedPreferences;
    String correo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_saldo, container, false);
        //setUpViews();
        iniciar();
        return parentView;
    }

    private void iniciar() {
        listaResultado = (ListView) parentView.findViewById(R.id.lv_idfacturas);
        cargarLista();
    }

    private void cargarLista() {
        sharedPreferences = getActivity().getSharedPreferences("multibanco", MODE_PRIVATE);
        correo = sharedPreferences.getString("correo", "");

        String URL = rutaWS.HISTORICO + correo;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<String> lista = new ArrayList<>();
                    JSONObject root = new JSONObject(response);
                    JSONArray jsonArray = root.getJSONArray("historia");
                    for (int i = jsonArray.length()-1; i >=0; i--) {
                        try {
                            int monto = (int) jsonArray.getJSONObject(i).get("monto");
                            String tipo = (String) jsonArray.getJSONObject(i).get("tipo");
                            String moneda = (String) jsonArray.getJSONObject(i).get("moneda");
                            String detalle = (String) jsonArray.getJSONObject(i).get("detalle");
                            String fecha = (String) jsonArray.getJSONObject(i).get("fecha");

                            lista.add(monto + " " + moneda + "  " + tipo+"  "+detalle+"  "+fecha);
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
                    listaResultado.setAdapter(adaptador);
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
