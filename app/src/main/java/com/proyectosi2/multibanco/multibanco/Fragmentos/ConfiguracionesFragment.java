package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;

import com.proyectosi2.multibanco.multibanco.MainActivity;
import com.proyectosi2.multibanco.multibanco.R;

public class ConfiguracionesFragment extends Fragment {

    private View parentView;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    Button logout;
    public static final int MODE_PRIVATE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_configuracion, container, false);
        //setUpViews();
        logout = (Button) parentView.findViewById(R.id.bt_logout);
        imageView = (ImageView) parentView.findViewById(R.id.iv_perfil);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getActivity().getSharedPreferences("multibanco", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("login", false).commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        cargarImagen();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), 2);
            }
        });
        return parentView;
    }

    private void cargarImagen() {
        sharedPreferences = getActivity().getSharedPreferences("multibanco", MODE_PRIVATE);
        String url_fotoPerfil = sharedPreferences.getString("urlFoto", "");
        if (!url_fotoPerfil.isEmpty()) {
            Uri u = Uri.parse(url_fotoPerfil);
            imageView.setImageURI(u);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == -1) {
            Uri u = data.getData();
            imageView.setImageURI(u);
            sharedPreferences.edit().putString("urlFoto", u.toString()).apply();
        }
    }
}
