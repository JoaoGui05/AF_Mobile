package com.example.sistemadeponto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "MainActivity3 carregada", Toast.LENGTH_SHORT).show();
        Log.d("LOGIN", "MainActivity3 foi iniciada");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        NotificacaoHelper.criarCanalDeNotificacao(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && "notificacoes".equals(intent.getStringExtra("abrirFragmento"))) {
            abrirFragmentoDeNotificacoes();
        } else {
            abrirFragmentoInicial();
        }

        bottomNavigation = findViewById(R.id.bottom_navigation);
         getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeGerenteFragment())
                .commit();

        String fragmentToLoad = getIntent().getStringExtra("fragment_to_load");
        if (fragmentToLoad != null) {
            if (fragmentToLoad.equals("inicio")) {
                bottomNavigation.setSelectedItemId(R.id.nav_home_gerente);
            } else if (fragmentToLoad.equals("notificacoes")) {
                bottomNavigation.setSelectedItemId(R.id.nav_notificacoes);
            }
        }
        loadFragment(new HomeGerenteFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            Log.d("NAV_ID", "Selecionado: " + id); // só pra debugar

            if (id == R.id.nav_home_gerente) {
                fragment = new HomeGerenteFragment();
            } else if (id == R.id.nav_notificacoes) {
                fragment = new NotificacoesFragment();
            }
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }

            return false;
        });
    }
    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
    private void abrirFragmentoDeNotificacoes() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NotificacoesFragment())
                .commit();
    }

    private void abrirFragmentoInicial() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeGerenteFragment())
                .commit();
    }
}