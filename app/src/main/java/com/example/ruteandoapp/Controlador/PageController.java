package com.example.ruteandoapp.Controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageController extends FragmentPagerAdapter {
    int numoftabs;

    public PageController(@NonNull FragmentManager fm, int behaviour) {
        super(fm);
        this.numoftabs = behaviour;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Progreso();
            case 1:
                return new Retos();
            case 2:
                return new Ranking();
            case 3:
                return new Salir();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
