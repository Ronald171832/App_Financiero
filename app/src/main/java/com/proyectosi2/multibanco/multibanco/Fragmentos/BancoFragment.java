package com.proyectosi2.multibanco.multibanco.Fragmentos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyectosi2.multibanco.multibanco.R;

public class BancoFragment extends Fragment {

    private View parentView;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_banco, container, false);
        //setUpViews();
        return parentView;
    }


}
