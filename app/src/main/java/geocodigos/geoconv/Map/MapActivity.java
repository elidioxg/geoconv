package geocodigos.geoconv.Map;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import geocodigos.geoconv.R;

public class MapActivity extends Fragment implements GoogleMap.OnMapLoadedCallback{
    private static GoogleMap mapa;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);


        SupportMapFragment fm = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));

        mapa = fm.getMap();

        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(10.5, 5.1))
                .title("Hello world"));

        return view;
    }

    @Override
    public void onMapLoaded() {

        latitude = 26.78;
        longitude = 72.56;
        mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Hello world"));
    }

}
