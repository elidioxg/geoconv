package geocodigos.geoconv.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Conversion.DMSConversion;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.Dialogs.DialogAddPoint;
import geocodigos.geoconv.R;
import geocodigos.geoconv.Utils.CoordinatesArray;
import geocodigos.geoconv.VerPontos;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class MapActivity extends Fragment implements LocationListener {
    private String strFormat = "%.5f";
    private int requests = 1000;
    private static GoogleMap mapa;
    private DatabaseHelper database;
    private double minLat, maxLat, minLon, maxLon;
    private int num_pontos = 0;
    private ArrayList<PointModel> pontos = new ArrayList<PointModel>();
    private double lat_atual=0, lon_atual=0;
    private final double dif = 0.2;
    private Marker marcador;
    private Polygon poligono;
    private  Polyline line;
    private View view;
    private boolean fragmentVisivel;
    private Location location;
    private LocationManager locationManager;
    private String provider;
    private RadioGroup rgGeometria;
    private RadioButton rbPontos, rbLinhas, rbPoligono;
    private ImageButton ibPontos;
    private ToggleButton tbAddMarker;
    public SupportMapFragment supportFragment;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_layout, container, false);

        locationManager = (LocationManager) getActivity().
                getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.GPS_PROVIDER;
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }
        rgGeometria = (RadioGroup) view.findViewById(R.id.rg_geometria);
        rgGeometria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setGeometry(false);
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
                transaction.commit();
            }
        });
        //setRetainInstance(false);
        locationManager.requestLocationUpdates(provider, requests, 1, this);
        initializeMap();
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        //if(fragmentVisivel) {
        String strLoc = getResources().getString(R.string.localizacao_atual);
        lat_atual = location.getLatitude();
        lon_atual = location.getLongitude();
        if(lat_atual!=0  && lon_atual!=0) {
            if (marcador != null) {
                marcador.remove();
            }
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

    private void initializeMap() {
        supportFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));

        mapa = supportFragment.getMap();
        mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                setGeometry(true);
            }
        });
        mapa.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //TODO colocar o nome do registro como unique no banco de dados
            }
        });
        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                Marker marker = mapa.addMarker(new MarkerOptions().position(latLng));
                String strN = "N";
                String strE = "E";
                if(latLng.latitude<0){ strN= "S";}
                if(latLng.longitude<0) { strE = "W";}

                PointModel pm = new PointModel();
                DMSConversion conversion = new DMSConversion();
                ArrayList<String> array = conversion.DegreesConversion(latLng.latitude,
                        latLng.longitude);
                CoordinatesArray formater = new CoordinatesArray();
                pm.setLatDms(formater.formatCoordinateToDMS(strN, array.get(0), array.get(1),
                        array.get(2)));
                pm.setLonDms(formater.formatCoordinateToDMS(strE, array.get(3), array.get(4),
                        array.get(5)));
                pm.setLatidude(String.format(strFormat, latLng.latitude));
                pm.setLongitude(String.format(strFormat, latLng.longitude));
                getDate date = new getDate();
                pm.setData(date.returnDate());
                getTime time = new getTime();
                pm.setHora(time.returnTime());
                CoordinateConversion cc = new CoordinateConversion();
                String utm = cc.latLon2UTM(latLng.latitude, latLng.longitude);
                String[]  strUtm = utm.split(" ");
                pm.setSetor(strUtm[0] + " " + strUtm[1]);
                pm.setLeste(strUtm[2]);
                pm.setNorte(strUtm[3]);
                pm.setPrecisao("");
                pm.setAltitude("");

                DialogAddPoint dialog = new DialogAddPoint(getActivity(), pm);
                AlertDialog.Builder alert = dialog.createAlertAdd(view);
                alert.show();
            }

            });

    }

    private void setGeometry(boolean updateVision){
        switch (rgGeometria.getCheckedRadioButtonId()){
            case R.id.rb_pontos:
                addMarkers(updateVision, "pontos");
                break;
            case R.id.rb_linha:
                addMarkers(updateVision, "linha");
                break;
            case R.id.rb_poligono:
                addMarkers(updateVision, "poligono");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(marcador!=null){
            marcador.remove();
        }
        if(mapa!=null) {
            mapa=null;
        }
        if(poligono!=null){
            poligono.remove();
        }
        if(line!=null){line.remove();}
        locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        locationManager.requestLocationUpdates(provider, requests, 1, this);
        initializeMap();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            setGeometry(false);
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
                Marker marker = mapa.addMarker(new MarkerOptions().position(new LatLng(lat_atual, lon_atual))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_current_loc))
                        .title(getResources().getString(R.string.localizacao_atual)));
            }
            if(pontos.size()>0){
                    for (int i = 0; i < pontos.size() ; i++) {
                        String selecionado = pontos.get(i).getSelecao();
                        String latitude = pontos.get(i).getlatitude();
                        String longitude = pontos.get(i).getLongitude();
                        String nome = pontos.get(i).getRegistro();
                        latitude = latitude.replace(",",".");
                        longitude = longitude.replace(",",".");

                        lat = (Double)Double.parseDouble(latitude);
                        lon = (Double)Double.parseDouble(longitude);
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
                            Marker marker = mapa.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lon))
                                    .title(nome));
                        }
                        if (tipo=="linha") {
                            linha.add(new LatLng(lat, lon));
                            numLinhas++;
                        }
                        if (tipo=="poligono") {
                            poly.add(new LatLng(lat, lon));
                            numPoligono++;
                        }
                    }
                if(tipo=="linha" && numLinhas>1){
                    List<LatLng> lista = linha.getPoints();
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
