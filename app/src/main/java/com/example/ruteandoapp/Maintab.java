package com.example.ruteandoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.ruteandoapp.Controlador.PageController;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

public class Maintab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TabLayout tabLayout;
        ViewPager viewPager;
        TabItem tab1,tab2,tab3, tab4;
        PageController pageAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintab);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        tab1 = findViewById(R.id.progreso);
        tab2 = findViewById(R.id.retos);
        tab3 = findViewById(R.id.salir);
        tab4 = findViewById(R.id.ranking);

        pageAdapter = new PageController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pageAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    pageAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==2){
                    pageAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==3){
                    pageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}