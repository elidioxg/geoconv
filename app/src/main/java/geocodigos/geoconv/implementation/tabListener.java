package geocodigos.geoconv.implementation;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import geocodigos.geoconv.ConverterCoordenadas;
import geocodigos.geoconv.Map.MapActivity;
import geocodigos.geoconv.MarcarPontos;
import geocodigos.geoconv.R;
import geocodigos.geoconv.VerPontos;

public class tabListener extends FragmentStatePagerAdapter {

    final int PAGE_COUNT=3;
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
                MarcarPontos marcar = new MarcarPontos();
                return (Fragment) marcar;
            case 1:
                MapActivity mapa_ = new MapActivity();
                return (Fragment) mapa_;
            case 2:
                ConverterCoordenadas converter = new ConverterCoordenadas();
                return (Fragment) converter;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }

}
