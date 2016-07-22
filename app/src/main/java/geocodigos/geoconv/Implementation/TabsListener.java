package geocodigos.geoconv.Implementation;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import geocodigos.geoconv.Fragments.ConvertCoordinates;
import geocodigos.geoconv.Map.MapActivity;
import geocodigos.geoconv.Fragments.GpsData;
import geocodigos.geoconv.R;

public class TabsListener extends FragmentStatePagerAdapter {

    final int PAGE_COUNT=3;
    public String titulos[];
    public TabsListener(FragmentManager fm, Context context) {
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
                GpsData marcar = new GpsData();
                return (Fragment) marcar;
            case 1:
                MapActivity mapa_ = new MapActivity();
                return (Fragment) mapa_;
            case 2:
                ConvertCoordinates converter = new ConvertCoordinates();
                return (Fragment) converter;
            default:
                break;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }

}
