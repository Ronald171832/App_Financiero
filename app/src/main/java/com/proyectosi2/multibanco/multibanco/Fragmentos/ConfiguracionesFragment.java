package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.proyectosi2.multibanco.multibanco.MainActivity;
import com.proyectosi2.multibanco.multibanco.R;

public class ConfiguracionesFragment extends Fragment {

    private View parentView;
    SharedPreferences sharedPreferences;
    Button logout;
    public static final int MODE_PRIVATE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_configuracion, container, false);
        //setUpViews();
        logout=(Button)parentView.findViewById(R.id.bt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getActivity().getSharedPreferences("multibanco", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("login", false).commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return parentView;
    }


}
