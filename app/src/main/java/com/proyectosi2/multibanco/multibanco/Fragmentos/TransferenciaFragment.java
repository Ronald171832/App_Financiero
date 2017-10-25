package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyectosi2.multibanco.multibanco.Perfil;
import com.proyectosi2.multibanco.multibanco.R;
import com.proyectosi2.multibanco.multibanco.rutaWS;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransferenciaFragment extends Fragment {

    private View parentView;
    EditText monto, cuentaO,cuentaD;
    Button comfirmar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_transferncia, container, false);
        iniciar();
        return parentView;
    }

    private void iniciar() {
        monto= (EditText) parentView.findViewById(R.id.et_monto);
        cuentaO= (EditText) parentView.findViewById(R.id.et_cuentaOrigen);
        cuentaD= (EditText) parentView.findViewById(R.id.et_cuentaDestino);
        comfirmar=(Button)parentView.findViewById(R.id.bt_confirmarTransacion);
        comfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    Calendar horaI = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha = sdf.format(horaI.getTime());
                    System.out.println(fecha+"    -----------------------------------------------");
                    WebView webView = (WebView) parentView.findViewById(R.id.webviewSocio);
                    webView.loadUrl(rutaWS.TRANSFERENCIA+fecha+"/"+monto.getText().toString()+"/"
                    +cuentaO.getText().toString()+"/"+cuentaD.getText().toString());
                    webView.setWebViewClient(new WebViewClient());
                    Toast.makeText(getActivity(),"Transferencia realiza conrectamente "+monto.getText().toString()+" ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Perfil.class));

                }else{
                    Toast.makeText(getActivity(),"Ingresar Todos los Campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validar(){
        if(monto.getText().toString().equals("")||cuentaO.getText().toString().equals("")||cuentaD.getText().toString().equals("")){
            return false;
        }
        return true;
    }
}
