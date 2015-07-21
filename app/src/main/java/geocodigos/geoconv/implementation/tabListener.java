package geocodigos.geoconv.implementation;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import geocodigos.geoconv.ConverterCoordenadas;
import geocodigos.geoconv.Map.MapActivity;
import geocodigos.geoconv.Map.Mapa;
import geocodigos.geoconv.MarcarPontos;
import geocodigos.geoconv.VerPontos;

public class tabListener extends FragmentPagerAdapter {

    final int PAGE_COUNT=4;

    private String tabNames[] = new String[] {"Converter","Marcar Ponto", "Pontos Marcados", "Mapa"};
    Context context;

    public tabListener(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                ConverterCoordenadas converter = new ConverterCoordenadas();
                return converter;
            case 1:
                MarcarPontos marcar = new MarcarPontos();
                return marcar;
            case 2:
                VerPontos ver = new VerPontos();
                return ver;
            case 3:
                MapActivity mapa_ = new MapActivity();
                return mapa_;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

}
