package geocodigos.geoconv.Map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.model.PointModel;

public class MapActivity extends Fragment implements LocationListener {
    private static GoogleMap mapa;
    DatabaseHelper database;
    double minLat, maxLat, minLon, maxLon;
    int num_pontos = 0;
    ArrayList<PointModel> pontos = new ArrayList<PointModel>();
    double lat_atual=0, lon_atual=0;
    final double dif = 0.2;
    public Marker marcador;
    public View view;
    private boolean fragmentVisivel;
    LocationManager locationManager;
    String provider;
    public SupportMapFragment supportFragment;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(container==null){
            return null;
        }
        locationManager = (LocationManager) getActivity().
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        view = inflater.inflate(R.layout.map_layout, container, false);

        supportFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));

        mapa = supportFragment.getMap();
        mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds visao;
                if (num_pontos > 0) {
                    visao = new LatLngBounds(
                            new LatLng(lat_atual - dif, lon_atual - dif),
                            new LatLng(lat_atual + dif, lon_atual + dif));

                } else {
                    visao = new LatLngBounds(
                            new LatLng(lat_atual - dif, lon_atual - dif),
                            new LatLng(lat_atual + dif, lon_atual + dif));
                    Log.i("lat_atual-dif: ", String.valueOf(lat_atual - dif));
                    Log.i("lat_atual+dif: ", String.valueOf(lat_atual + dif));
                }
                if (mapa != null) {
                    mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(visao, 80));
                    addMarkers();
                }
            }
        });

        if (location != null) {
            onLocationChanged(location);
            Log.i("provider:", provider);
        }
        setRetainInstance(false);
        return view;
    }

    private void addMarkers() {
        //if(fragmentVisivel){
        if(mapa!=null){
            double lat, lon;
            database = new DatabaseHelper(getActivity());
            database.getWritableDatabase();
            pontos.clear();
            pontos = database.pegarPontos();
            Log.i("pontos.size():", Integer.toString(pontos.size()));
            num_pontos=pontos.size();
            mapa.clear();
            marcador = mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_mapmarker))
                    .title("Localização Atual"));
            minLat=lat_atual;
            maxLat=lat_atual;
            minLon=lon_atual;
            maxLon=lon_atual;
            if(!pontos.isEmpty()){
                for (int i = 0; i < pontos.size() ; i++) {
                    String selecionado = pontos.get(i).getSelecao();
                    Log.i("selecionado = ", selecionado);
                    if(Integer.parseInt(selecionado.trim())==1) {
                        String latitude = pontos.get(i).getlatitude();
                        String longitude = pontos.get(i).getLongitude();
                        String nome = pontos.get(i).getRegistro();
                        lat = Double.parseDouble(latitude);
                        lon = Double.parseDouble(longitude);
                        if (minLat > lat) {
                            minLat = lat;
                        }
                        if (maxLat < lat) {
                            maxLat = lat;
                        }
                        if (minLon > lon) {
                            minLon = lon;
                        }
                        if (maxLon < lon) {
                            maxLon = lon;
                        }
                        Log.i("Latitude", latitude);
                        Log.i("Longitude", String.valueOf(lon));
                        mapa.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lon))
                                .title(nome));
                    }
                }
            }
            database.close();
            LatLngBounds visao;
            visao = new LatLngBounds(
                    new LatLng(minLat-dif, minLon-dif),
                    new LatLng(maxLat+dif, maxLon+dif));
            //if( mapa!= null) {
                mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(visao, 80));
            //}
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //if(fragmentVisivel) {
            String strLoc = getResources().getString(R.string.localizacao_atual);
            lat_atual = location.getLatitude();
            lon_atual = location.getLongitude();
            Log.i("lon_atual: ", String.valueOf(lon_atual));
            if (marcador != null) {
                marcador.remove();
            }
            if(mapa!=null){
                marcador = mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_mapmarker))
                        .title(strLoc));
            }
        //}
        //marcador.setPosition(new LatLng(lat_atual, lon_atual));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(marcador!=null){
            marcador.remove();
        }
        if(mapa!=null) {
            getFragmentManager().beginTransaction().remove(getChildFragmentManager()
                    .findFragmentById(R.id.map)).commit();
            mapa=null;
            //mapa.clear();
        }
  //      supportFragment.onDetach();
        //supportFragment.onDestroy();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //if fragmentVisivel?
        locationManager.requestLocationUpdates(provider, 1000, 1, this);
        //addMarkers();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        fragmentVisivel = isVisibleToUser;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        //setTargetFragment(supportFragment, 0);
        setTargetFragment(null, -1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
            /*if (marcador != null) {
                marcador.remove();
            }
            if (mapa != null) {
                getFragmentManager().beginTransaction().remove(getChildFragmentManager()
                        .findFragmentById(R.id.map)).commit();
                //mapa=null;
                mapa.clear();
            }
            //supportFragment.onDestroy();
*/
        /*try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
    }
}
