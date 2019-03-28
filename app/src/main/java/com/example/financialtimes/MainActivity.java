package com.example.financialtimes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Navigation controller
    private BottomNavigationView.OnNavigationItemSelectedListener nav_handler = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment currentFrag = null;

            switch(menuItem.getItemId()){
                case R.id.nav_cursValutar:
                    currentFrag = new cursFragment();
                    break;
                case R.id.nav_crypto:
                    currentFrag = new cryptoFragment();
                    break;
                case R.id.nav_stock:
                    currentFrag = new bursaFragment();
                    break;
            }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, currentFrag).commit();
        return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment defaultFragment = new cursFragment();

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(nav_handler);

        if(savedInstanceState == null){
            FragmentManager frag_handler = getSupportFragmentManager();
            FragmentTransaction defaultFragTransaction = frag_handler.beginTransaction();
            defaultFragTransaction.replace(R.id.frag_container, defaultFragment).commit();
        }
    }
}
