package geocodigos.geoconv.Map;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.VerPontos;
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
    public Polygon poligono;//private
    public Polyline line;
    public View view;
    private boolean fragmentVisivel;
    private Location location;
    LocationManager locationManager;
    String provider;
    private RadioGroup rgGeometria;
    private RadioButton rbPontos, rbLinhas, rbPoligono;
    private ImageButton ibPontos;
    public SupportMapFragment supportFragment;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(container==null){
            return null;
        }

        view = inflater.inflate(R.layout.map_layout, container, false);
        locationManager = (LocationManager) getActivity().
                getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        provider = locationManager.GPS_PROVIDER;
        //provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }
        rgGeometria = (RadioGroup) view.findViewById(R.id.rg_geometria);
        rgGeometria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_pontos:
                        Log.i("rb_pontos","Marcado");
                        addMarkers(true, "pontos");
                        break;
                    case R.id.rb_linha:
                        Log.i("rb_linha","Marcado");
                        addMarkers(true, "linha");
                        break;
                    case R.id.rb_poligono:
                        Log.i("rb_poligono","Marcado");
                        addMarkers(true, "poligono");
                        break;
                    default:
                        addMarkers(false, "pontos");
                        break;
                }
            }
        });
        rbPontos = (RadioButton) view.findViewById(R.id.rb_pontos);
        rbLinhas = (RadioButton) view.findViewById(R.id.rb_linha);
        rbPoligono  = (RadioButton) view.findViewById(R.id.rb_poligono);
        ibPontos = (ImageButton) view.findViewById(R.id.ib_pontos);
        ibPontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerPontos ver_pontos = new VerPontos();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_map, ver_pontos);
                transaction.addToBackStack(null);
                //transaction.remove(MapActivity.this);
                transaction.commit();
            }
        });

        supportFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));

        mapa = supportFragment.getMap();
        mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                switch(rgGeometria.getCheckedRadioButtonId()){
                    case R.id.rb_pontos:
                        addMarkers(true, "pontos");
                        break;
                    case R.id.rb_linha:
                        addMarkers(true, "linha");
                        break;
                    case R.id.rb_poligono:
                        addMarkers(true, "poligono");
                        break;
                    default:
                        addMarkers(true, "pontos");
                        break;
                }
            }
        });
        //setRetainInstance(false);
        locationManager.requestLocationUpdates(provider, 5000, 1, this);
        return view;
    }


    @Override
    public void onLocationChanged(Location location) {
        //if(fragmentVisivel) {
        String strLoc = getResources().getString(R.string.localizacao_atual);
        lat_atual = location.getLatitude();
        lon_atual = location.getLongitude();
        if (marcador != null) {
            marcador.remove();
        }
        if(lat_atual!=0  && lon_atual!=0) {
            if (mapa != null) {
                marcador = mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_current_loc))
                        .title(strLoc));
            }
        }
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
/*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
*/
    @Override
    public void onPause(){
        if(marcador!=null){
            marcador.remove();
        }
        if(mapa!=null) {
            //getFragmentManager().beginTransaction().remove(getChildFragmentManager()
              //      .findFragmentById(R.id.map)).commitAllowingStateLoss();
            mapa=null;
        }
        if(poligono!=null){
            poligono.remove();
        }
        if(line!=null){line.remove();}
        locationManager.removeUpdates(this);
        /*if(supportFragment.isResumed()){
            getFragmentManager().beginTransaction().remove(supportFragment).
                    commitAllowingStateLoss();

        }*/
        super.onPause();
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //if fragmentVisivel?
        //locationManager.removeUpdates(this);
        locationManager.requestLocationUpdates(provider, 5000, 1, this);
        /*switch(rgGeometria.getCheckedRadioButtonId()){
            case R.id.rb_pontos:
                addMarkers(true, "pontos");
                break;
            case R.id.rb_linha:
                addMarkers(true, "linha");
                break;
            case R.id.rb_poligono:
                addMarkers(true, "poligono");
                break;
            default:
                addMarkers(true, "pontos");
                break;
        }*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            addMarkers(false, "pontos");
        }
        fragmentVisivel = isVisibleToUser;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void addMarkers(boolean updateVision, String tipo) {
        int numLinhas=0, numPoligono=0;
        //if(fragmentVisivel){
        if(mapa!=null){
            double lat, lon;
            PolygonOptions poly = new PolygonOptions();
            PolylineOptions linha = new PolylineOptions();
            database = new DatabaseHelper(getActivity());
            database.getWritableDatabase();
            pontos.clear();
            pontos = database.pegarPontos();
            num_pontos=pontos.size();
            if(marcador!=null){marcador.remove();}
            if(line!=null){line.remove();}
            if(poligono!=null){poligono.remove();}
            mapa.clear();
            if(lat_atual!=0 && lon_atual!=0) {
                marcador = mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_current_loc))
                        .title(getResources().getString(R.string.localizacao_atual)));
            }
            if(pontos.size()>0){
                    for (int i = 0; i < pontos.size() ; i++) {
                        String selecionado = pontos.get(i).getSelecao();
                        if (Integer.parseInt(selecionado.trim()) == 1) {
                            String latitude = pontos.get(i).getlatitude();
                            String longitude = pontos.get(i).getLongitude();
                            String nome = pontos.get(i).getRegistro();
                            lat = Double.parseDouble(latitude);
                            lon = Double.parseDouble(longitude);
                            if (i == 0) {
                                minLat = lat;
                                maxLat = lat;
                                minLon = lon;
                                maxLon = lon;
                            }
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
                            if (tipo=="pontos") {
                                Log.i("rbPontos", "isChecked()");
                                mapa.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat, lon))
                                        .title(nome));
                            }
                            if (tipo=="linha") {
                                Log.i("rbLinhas", "isChecked()");
                                linha.add(new LatLng(lat, lon));
                                numLinhas++;
                            }
                            if (tipo=="poligono") {
                                Log.i("rbPoligono", "isChecked()");
                                poly.add(new LatLng(lat, lon));
                                numPoligono++;
                            }
                        }
                }
                if(tipo=="linha" && numLinhas>1){
                    List<LatLng> lista = linha.getPoints();
                    //linha.addAll(lista);
                    line = mapa.addPolyline(linha);
                    line.setWidth(3);
                    line.setColor(Color.BLUE);
                    line.setPoints(lista);
                }
                if(tipo=="poligono" && numPoligono>2){
                    poly.fillColor(Color.GREEN);
                    poly.strokeColor(Color.green(50));
                    poligono = mapa.addPolygon(poly);
                }
            }
            database.close();
            if(updateVision) {
                LatLngBounds visao;
                if(pontos.size()>0) {
                    visao = new LatLngBounds(
                            new LatLng(minLat - dif, minLon - dif),
                            new LatLng(maxLat + dif, maxLon + dif));
                    mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(visao, 80));
                } else {
                    if(lat_atual!=0 && lon_atual!=0) {
                        visao = new LatLngBounds(
                                new LatLng(lat_atual - dif, lon_atual - dif),
                                new LatLng(lat_atual + dif, lon_atual + dif));
                        mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(visao, 80));
                    }
                }
            }
        }
    }

}
