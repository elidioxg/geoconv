package geocodigos.geoconv;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Conversion.DMSConversion;
import geocodigos.geoconv.Dialogs.DialogAddPoint;
import geocodigos.geoconv.Utils.CoordinatesArray;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    private String strFormat = "%.5f";
    private int requests = 3000;
    private int min_distance=1;
    private Location location;
    private LocationManager locationManager;
    private String provider;
    private String strLatitude, strLongitude, strPrecisao, strAltitude, strDate, strTime;
    private ImageButton ibMarcar;
    private TextView tvLatitude, tvLongitude, tvPrecisao, tvAltitude,
        tvSetor, tvNorte, tvLeste, tvData, tvLatgms, tvLongms, tvGpsStatus;

    private static boolean fragmentVisivel;
    private double latitude, longitude, altitude, precisao;

    private View view;

    private final String keyLatitude = "lat";
    private final String keyLongitude = "lon";
    private final String keySector = "sec";
    private final String keyNorth = "north";
    private final String keyEast = "east";
    private final String keyPrecision ="prec";
    private final String keyAltitude = "alt";
    private final String keyDate = "date";
    private final String keyLatGms = "latgms";
    private final String keyLonGms = "longms";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(keyLatitude, tvLatitude.getText().toString());
        outState.putString(keyLongitude, tvLongitude.getText().toString());
        outState.putString(keySector, tvSetor.getText().toString());
        outState.putString(keyNorth, tvNorte.getText().toString());
        outState.putString(keyEast, tvLeste.getText().toString());
        outState.putString(keyPrecision, tvPrecisao.getText().toString());
        outState.putString(keyAltitude, tvAltitude.getText().toString());
        outState.putString(keyDate, tvData.getText().toString());
        outState.putString(keyLatGms, tvLatgms.getText().toString());
        outState.putString(keyLonGms, tvLongms.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_gps,
                container, false);

        AdView av = (AdView) view.findViewById(R.id.adView1);
        AdRequest ar = new AdRequest.Builder().build();
                //.addTestDevice("DC230321FB9742079A931AC2BB5B27A5").build();
        av.loadAd(ar);

        tvLatitude = (TextView) view.findViewById(R.id.in_latitude);
        tvLongitude = (TextView) view.findViewById(R.id.in_longitude);
        tvLatgms = (TextView) view.findViewById(R.id.in_lat_gms);
        tvLongms = (TextView) view.findViewById(R.id.in_lon_gms);
        tvPrecisao = (TextView) view.findViewById(R.id.in_precisao);
        tvAltitude = (TextView) view.findViewById(R.id.in_altitude);
        tvData = (TextView) view.findViewById(R.id.tv_data);

        tvSetor = (TextView) view.findViewById(R.id.in_quadrante);
        tvNorte = (TextView) view.findViewById(R.id.in_norte);
        tvLeste = (TextView) view.findViewById(R.id.in_leste);
        tvGpsStatus = (TextView) view.findViewById(R.id.in_status);

        ibMarcar = (ImageButton) view.findViewById(R.id.ibmarcar);

        ibMarcar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!tvLatitude.getText().toString().isEmpty()) {

                    if (!tvLongitude.getText().toString().isEmpty()) {
                        PointModel pm = new PointModel();
                        pm.setLatidude(tvLatitude.getText().toString());
                        pm.setLongitude(tvLongitude.getText().toString());
                        pm.setAltitude(tvAltitude.getText().toString());
                        pm.setPrecisao(tvPrecisao.getText().toString());
                        pm.setNorte(tvNorte.getText().toString());
                        pm.setLeste(tvLeste.getText().toString());
                        pm.setSetor(tvSetor.getText().toString());
                        getTime time = new getTime();
                        String strTime = time.returnTime();
                        getDate date = new getDate();
                        String strDate = date.returnDate();
                        pm.setData(strDate);
                        pm.setHora(strTime);


                        DialogAddPoint dialog = new DialogAddPoint(getActivity(), pm);
                        AlertDialog.Builder alert = dialog.createAlertAdd(view);
                        alert.show();

                    } else {
                        Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                }
            }

        });
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(keyLatitude)){
                String str = savedInstanceState.getString(keyLatitude);
                tvLatitude.setText(str);
            }
            if(savedInstanceState.containsKey(keyLongitude)){
                String str = savedInstanceState.getString(keyLongitude);
                tvLongitude.setText(str);
            }
            if(savedInstanceState.containsKey(keySector)){
                String str = savedInstanceState.getString(keySector);
                tvSetor.setText(str);
            }
            if(savedInstanceState.containsKey(keyNorth)){
                String str = savedInstanceState.getString(keyNorth);
                tvNorte.setText(str);
            }
            if(savedInstanceState.containsKey(keyEast)){
                String str = savedInstanceState.getString(keyEast);
                tvLeste.setText(str);
            }
            if(savedInstanceState.containsKey(keyDate)){
                String str = savedInstanceState.getString(keyDate);
                tvData.setText(str);
            }
            if(savedInstanceState.containsKey(keyAltitude)){
                String str = savedInstanceState.getString(keyAltitude);
                tvAltitude.setText(str);
            }
            if(savedInstanceState.containsKey(keyPrecision)){
                String str = savedInstanceState.getString(keyPrecision);
                tvPrecisao.setText(str);
            }
            if(savedInstanceState.containsKey(keyLatGms)){
                String str = savedInstanceState.getString(keyLatGms);
                tvLatgms.setText(str);
            }
            if(savedInstanceState.containsKey(keyLonGms)){
                String str = savedInstanceState.getString(keyLonGms);
                tvLongms.setText(str);
            }

        }
        gpsStatus();
        return view;
    }

    public void gpsStatus() {
        locationManager = (LocationManager) getActivity().
                getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.GPS_PROVIDER;
        //locationManager.removeUpdates(this);
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            tvGpsStatus.setText(R.string.req_loc);
            tvGpsStatus.setTextColor(getResources().getColor(R.color.verde));
        } else {
            tvGpsStatus.setText(R.string.gps_desativado);
            tvGpsStatus.setTextColor(getResources().getColor(R.color.vermelho));
        }
        locationManager.requestLocationUpdates(provider, requests, min_distance, this);
    }

    public void preencheCampos(){
        if(latitude!=0 && longitude!=0) {
            DMSConversion dms = new DMSConversion();
            String sLat = dms.convertFromDegrees(latitude);
            String sLon = dms.convertFromDegrees(longitude);
            String coordLat[] = sLat.split(" ");
            String coordLon[] = sLon.split(" ");
            String norte = "N ";
            String leste = "E ";
            if (latitude < 0) {
                norte = "S ";
            }
            if (longitude < 0) {
                leste = "W ";
            }
            CoordinatesArray formater = new CoordinatesArray();
            strLatitude = formater.formatCoordinateToDMS(norte, coordLat[0], coordLat[1],coordLat[2]);
            strLongitude =formater.formatCoordinateToDMS(leste, coordLat[3], coordLat[4],coordLat[5]);
            strPrecisao = String.format("%.1f", precisao);
            strAltitude = String.format("%.1f", altitude);
            tvLatitude.setText(String.format(strFormat, latitude));
            tvLongitude.setText(String.format(strFormat, longitude));
            tvLatgms.setText(strLatitude);
            tvLongms.setText(strLongitude);
            tvPrecisao.setText(strPrecisao+" m");
            tvAltitude.setText(strAltitude+" m");
            tvData.setText(strTime + "     -     " + strDate);

            CoordinateConversion cc = new CoordinateConversion();
            String latlon = cc.latLon2UTM(latitude, longitude);
            String coord[] = latlon.split(" ");
            tvSetor.setText(coord[0] + " " + coord[1]);
            tvLeste.setText(coord[2]);
            tvNorte.setText(coord[3]);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        locationManager.requestLocationUpdates(provider, requests, min_distance, this);
        preencheCampos();
        gpsStatus();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = (double) (location.getLatitude());
        longitude = (double) (location.getLongitude());
        altitude = (double) (location.getAltitude());
        precisao = (double) (location.getAccuracy());
        getTime time = new getTime();
        strTime = time.returnTime();
        getDate date = new getDate();
        strDate = date.returnDate();
        if (fragmentVisivel) {
            preencheCampos();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        fragmentVisivel = isVisibleToUser;
        //if(isVisibleToUser){preencheCampos();}
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        tvGpsStatus.setText(R.string.req_loc);
        tvGpsStatus.setTextColor(getResources().getColor(R.color.verde));
    }

    @Override
    public void onProviderDisabled(String s) {
        tvGpsStatus.setText(R.string.gps_desativado);
        tvGpsStatus.setTextColor(getResources().getColor(R.color.vermelho));
    }

}
