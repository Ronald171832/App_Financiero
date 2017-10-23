package com.proyectosi2.multibanco.multibanco;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyectosi2.multibanco.multibanco.Fragmentos.PerfilFragment;
import com.proyectosi2.multibanco.multibanco.Fragmentos.SaldoFragment;

public class Perfil extends AppCompatActivity {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_pantalla_principal);
        iniciar();
    }

    private void iniciar() {
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.inicioItem) {
                    infoTextView.setText("Perfil");
                    changeFragment(new PerfilFragment());
                } else if (item.getItemId() == R.id.buscarItem) {
                    infoTextView.setText("Saldo");
                    changeFragment(new SaldoFragment());
                } else if (item.getItemId() == R.id.camaraItem) {
                    infoTextView.setText("Banco");
                } else if (item.getItemId() == R.id.favoritosItem) {
                    infoTextView.setText("Transferencias");
                } else if (item.getItemId() == R.id.perfilItem) {
                    infoTextView.setText("Historico");
                }
                return true;
            }
        });
    }

    private void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragmento")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}