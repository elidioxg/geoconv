package geocodigos.geoconv.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import geocodigos.geoconv.MainActivity;
import geocodigos.geoconv.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Mapa extends Fragment {
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private static Double latitude, longitude;
    private static GoogleMap mapa;
    private static View view;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.map_layout, container, false);
        latitude = 26.78;
        longitude = 72.56;
        mostraMapa();

        return view;
    }

    public void mostraMapa() {
        if(mapa==null){
            mapa= ( (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        } else {
            mMapa();
        }
    }

    public static void mMapa() {
        mapa.setMyLocationEnabled(true);
        mapa.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mapa != null)
            mMapa();

        if (mapa == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapa = ((SupportMapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mapa != null)
                mMapa();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mapa!=null) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map)).commit();
        mapa=null;
        }
    }
}