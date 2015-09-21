package geocodigos.geoconv;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import geocodigos.geoconv.Conversion.ConversaoGMS;
import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    private int requests = 10000;
    private int min_distance=20;
    private Location location;
    private LocationManager locationManager;
    private String provider;
    public String strLatitude, strLongitude, strPrecisao, strAltitude, strDate, strTime;
    private ImageButton ibMarcar;
    private DatabaseHelper database;
    private TextView tvLatitude, tvLongitude, tvPrecisao, tvAltitude,
        tvSetor, tvNorte, tvLeste, tvData, tvLatgms, tvLongms, tvGpsStatus;

    private View view;
    private static boolean fragmentVisivel;
    private double latitude, longitude, altitude, precisao;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.marcar_pontos,
                container, false);

        final View view_marcar = View.inflate(getActivity(),R.layout.adicionar_registro, null);
        final EditText etRegistro = (EditText) view_marcar.findViewById(R.id.add_registro);
        final EditText etDescricao = (EditText) view_marcar.findViewById(R.id.add_descricao);
        final TextView tvlat = (TextView) view_marcar.findViewById(R.id.tv_lat);
        final TextView tvlon = (TextView) view_marcar.findViewById(R.id.tv_lon);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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

        etRegistro.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
                return false;
            }
        });
        etDescricao.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
                return false;
            }
        });

        ibMarcar = (ImageButton) view.findViewById(R.id.ibmarcar);

        ibMarcar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!tvLatitude.getText().toString().isEmpty()) {

                    if (!tvLongitude.getText().toString().isEmpty()) {
                        alert.setTitle(R.string.marcar_ponto);
                        alert.setView(view_marcar);
                        alert.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                parent.removeView(view_marcar);
                            }
                        });
                        alert.setPositiveButton(R.string.strMarcar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                InputMethodManager imm;
                                ArrayList<PointModel> al = new ArrayList<PointModel>();
                                int numId;
                                boolean strId;
                                String strAux;
                                database = new DatabaseHelper(getActivity());
                                database.getWritableDatabase();
                                //al = database.pegarPontos();
                                numId = al.size() + 1;
                                if (database.pegarId(String.valueOf(numId))) {
                                    do {
                                        numId++;
                                        strId = database.pegarId(String.valueOf(numId));
                                    } while (strId == true);

                                } else {

                                }
                                strAux = String.valueOf(numId);

                                PointModel pm = new PointModel();
                                pm.setId(strAux);
                                if (etRegistro.getText().toString().isEmpty()) {
                                    etRegistro.setText(getResources().getString(
                                            R.string.strRegistro) + strAux);
                                }
                                pm.setRegistro(etRegistro.getText().toString());
                                pm.setLatidude(tvLatitude.getText().toString());
                                pm.setLongitude(tvLongitude.getText().toString());
                                pm.setDescricao(etDescricao.getText().toString());

                                pm.setAltitude(tvAltitude.getText().toString());
                                pm.setPrecisao(tvPrecisao.getText().toString());
                                pm.setNorte(tvNorte.getText().toString());
                                pm.setLeste(tvLeste.getText().toString());
                                pm.setSetor(tvSetor.getText().toString());
                                pm.setSelecao("1");

                                getTime time = new getTime();
                                String strTime = time.returnTime();
                                getDate date = new getDate();
                                String strDate = date.returnDate();

                                pm.setData(strDate);
                                pm.setHora(strTime);

                                al.add(pm);
                                database.addPoint(pm);
                                database.close();
                                etRegistro.setText("");
                                etDescricao.setText("");
                                tvlat.setText("Lat: " + tvLatitude.getText().toString());
                                tvlon.setText("Lon: " + tvLongitude.getText().toString());
                                imm = (InputMethodManager) getActivity().getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                parent.removeView(view_marcar);
                                Toast.makeText(getActivity(), R.string.ponto_marcado,
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                    parent.removeView(view_marcar);
                                }
                                return false;
                            }
                        });
                        alert.show();

                    } else {
                        Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                }
            }

        });

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
            ConversaoGMS cg = new ConversaoGMS();
            String sLat = cg.converteGraus(latitude);
            String sLon = cg.converteGraus(longitude);
            String coordLat[] = sLat.split(" ");
            String coordLon[] = sLon.split(" ");
            String norte = "N";
            String leste = "E";
            if (latitude < 0) {
                norte = "S";
            }
            if (longitude < 0) {
                leste = "W";
            }
            strLatitude = coordLat[0] + "\u00B0 " + coordLat[1] + "' " + coordLat[2] + "'' " + norte;
            strLongitude = coordLon[0] + "\u00B0 " + coordLon[1] + "' " + coordLon[2] + "'' " + leste;
            strPrecisao = String.format("%.1f", precisao);
            strAltitude = String.format("%.1f", altitude);
            tvLatitude.setText(String.format("%.5f", latitude));
            tvLongitude.setText(String.format("%.5f", longitude));
            tvLatgms.setText(strLatitude);
            tvLongms.setText(strLongitude);
            tvPrecisao.setText(strPrecisao);
            tvAltitude.setText(strAltitude);
            tvData.setText(strTime + "   -   " + strDate);

            CoordinateConversion cc = new CoordinateConversion();
            String latlon = cc.latLon2UTM(latitude, longitude);
            String coord[] = latlon.split(" ");
            tvSetor.setText(coord[0] + " " + coord[1]);
            tvNorte.setText(coord[2]);
            tvLeste.setText(coord[3]);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
        /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();*/
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.attach(MarcarPontos.this);
        ft.commit();*/
        locationManager.requestLocationUpdates(provider, requests, min_distance, this);
        //preencheCampos();
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
