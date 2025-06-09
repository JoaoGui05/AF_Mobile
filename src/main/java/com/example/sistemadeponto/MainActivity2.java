package com.example.sistemadeponto;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        bottomNavigation = findViewById(R.id.bottom_navigation);
        /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeGerenteFragment())
                .commit(); */

        String fragmentToLoad = getIntent().getStringExtra("fragment_to_load");
        if (fragmentToLoad != null && fragmentToLoad.equals("inicio")) {
            bottomNavigation.setSelectedItemId(R.id.nav_inicio); // ← ID do item do menu
        }

        // Fragmento inicial
        loadFragment(new InicioFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            Log.d("NAV_ID", "Selecionado: " + id); // só pra debugar

            if (id == R.id.nav_inicio) {
                fragment = new InicioFragment();
            } else if (id == R.id.nav_registro) {
                fragment = new RegistroFragment();
            } else if (id == R.id.nav_rotas) {
                fragment = new RotasFragment();
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

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}