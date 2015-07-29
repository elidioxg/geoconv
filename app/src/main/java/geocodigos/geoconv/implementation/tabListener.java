package geocodigos.geoconv.implementation;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import geocodigos.geoconv.ConverterCoordenadas;
import geocodigos.geoconv.Map.MapActivity;
import geocodigos.geoconv.MarcarPontos;
import geocodigos.geoconv.R;
import geocodigos.geoconv.VerPontos;

public class tabListener extends FragmentPagerAdapter {

    final int PAGE_COUNT=4;

    //private String tabNames[] = new String[] {"Converter","Marcar Ponto", "Pontos Marcados", "Mapa"};
    public String titulos[];
    public tabListener(FragmentManager fm, Context context) {
        super(fm);
        Resources res = context.getResources();
        titulos = res.getStringArray(R.array.androidstrings);
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
        return titulos[position];
    }

}
