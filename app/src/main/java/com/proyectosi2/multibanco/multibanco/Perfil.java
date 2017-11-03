package com.proyectosi2.multibanco.multibanco;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyectosi2.multibanco.multibanco.Fragmentos.BancoFragment;
import com.proyectosi2.multibanco.multibanco.Fragmentos.ConfiguracionesFragment;
import com.proyectosi2.multibanco.multibanco.Fragmentos.PerfilFragment;
import com.proyectosi2.multibanco.multibanco.Fragmentos.SaldoFragment;
import com.proyectosi2.multibanco.multibanco.Fragmentos.TransferenciaFragment;

public class Perfil extends AppCompatActivity {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_pantalla_principal);
        iniciar();
        if (savedInstanceState == null) changeFragment(new PerfilFragment());

    }

    private void iniciar() {
        double ronaldXD=1.0;
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.inicioItem) {
                    infoTextView.setText("Tarjeta");
                    changeFragment(new PerfilFragment());
                } else if (item.getItemId() == R.id.buscarItem) {
                    infoTextView.setText("Historico");
                    changeFragment(new SaldoFragment());
                } else if (item.getItemId() == R.id.camaraItem) {
                    infoTextView.setText("Transferencias");
                    changeFragment(new TransferenciaFragment());
                } else if (item.getItemId() == R.id.favoritosItem) {
                    infoTextView.setText("Banco");
                    changeFragment(new BancoFragment());
                } else if (item.getItemId() == R.id.perfilItem) {
                    infoTextView.setText("Configuraciones");
                    changeFragment(new ConfiguracionesFragment());

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

    @Override
    public void onBackPressed() {
        //
    }
}
