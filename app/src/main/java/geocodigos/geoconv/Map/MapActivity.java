package geocodigos.geoconv.Map;

import android.app.FragmentManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.model.PointModel;

public class MapActivity extends Fragment implements LocationListener{
    private static GoogleMap mapa;
    DatabaseHelper database;
    double minLat, maxLat, minLon, maxLon;
    int num_pontos = 0;
    ArrayList<PointModel> pontos = new ArrayList<PointModel>();
    double lat_atual=0, lon_atual=0;
    final double dif = 0.2;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);

        SupportMapFragment supportFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));

        mapa = supportFragment.getMap();
        mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds visao;
                if(num_pontos>0) {
                    visao = new LatLngBounds(
                            new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));
                    Log.i("maxLat", String.valueOf(maxLat));

                }else{
                    visao = new LatLngBounds(
                            new LatLng(lat_atual-dif, lon_atual-dif),
                                new LatLng(lat_atual+dif, lon_atual+dif));
                    Log.i("lat_atual: ", String.valueOf(lat_atual));

                }
                mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(visao, -10));
            }
        });
        addMarkers();
        return view;
    }

    private void addMarkers() {
        double lat, lon;
        database = new DatabaseHelper(getActivity());
        database.getWritableDatabase();
        pontos.clear();
        pontos = database.pegarPontos();
        Log.i("pontos.size():", Integer.toString(pontos.size()));
        num_pontos=pontos.size();
        mapa.clear();
        mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_mapmarker))
                .title("Localização Atual"));
        minLat=lat_atual;
        maxLat=lat_atual;
        minLon=lon_atual;
        maxLon=lon_atual;
        if(!pontos.isEmpty()){
            for (int i = 0; i < pontos.size() ; i++) {
                String latitude = pontos.get(i).getlatitude();
                String longitude = pontos.get(i).getLongitude();
                String nome = pontos.get(i).getRegistro();
                lat = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);
                if(minLat>lat) {minLat=lat;}
                if(maxLat<lat) {maxLat=lat;}
                if(minLon>lon) {minLon=lon;}
                if(maxLon<lon) {maxLon=lon;}
                Log.i("Latitude", latitude);
                Log.i("Longitude", String.valueOf(lon));
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(nome));
            }
        }
        database.close();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat_atual = (double) (location.getLatitude());
        lon_atual = (double) (location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
