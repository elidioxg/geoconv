package geocodigos.geoconv;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.Calendar;

import geocodigos.geoconv.Conversion.ConversaoGMS;
import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    LocationManager locationManager;
    String provider;
    public String strLatitude, strLongitude, strPrecisao, strAltitude;
    private ImageButton ibMarcar;
    DatabaseHelper database;
    private TextView tvLatitude, tvLongitude, tvPrecisao, tvAltitude,
        tvSetor, tvNorte, tvLeste, tvData, tvLatgms, tvLongms;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.marcar_pontos,
                container, false);

        final View view_marcar = View.inflate(getActivity(),R.layout.adicionar_registro, null);
        final EditText etRegistro = (EditText) view_marcar.findViewById(R.id.add_registro);
        final EditText etDescricao = (EditText) view_marcar.findViewById(R.id.add_descricao);
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

        getDate date = new getDate();
        String strDate = date.returnDate();
        //Log.i("MarcarPonto.java", "strDate: "+strDate);
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
                                ArrayList<PointModel> al = new ArrayList<PointModel>();
                                int numId;
                                boolean strId;
                                String strAux;
                                database = new DatabaseHelper(getActivity());
                                database.getWritableDatabase();
                                //al = database.pegarPontos();
                                numId= al.size() + 1;
                                if(database.pegarId(String.valueOf(numId))) {
                                    do {
                                        numId++;
                                        strId = database.pegarId(String.valueOf(numId));
                                    } while (strId == true);

                                } else {

                                }
                                strAux= String.valueOf(numId);
                                Log.i("Salvando ID : ", strAux);

                                PointModel pm = new PointModel();
                                pm.setId(Integer.toString(numId));
                                if (etRegistro.getText().toString().isEmpty()) {
                                    etRegistro.setText(R.string.strRegistro+String.valueOf(numId));
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

                                Log.i("id", pm.id);
                                Log.i("Registro ", pm.registro);
                                Log.i("Latitude ", pm.latitude);
                                Log.i("Descricao ", pm.descricao);

                                getTime time = new getTime();
                                String strTime = time.returnTime();
                                Log.i("Time:", strTime);

                                getDate date = new getDate();
                                String strDate = date.returnDate();
                                Log.i("Date", strDate);

                                pm.setData(strDate);
                                pm.setHora(strTime);

                                al.add(pm);
                                database.addPoint(pm);
                                database.close();
                                etRegistro.setText("");
                                etDescricao.setText("");
                                ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                parent.removeView(view_marcar);
                                Toast.makeText(getActivity(), R.string.ponto_marcado,
                                        Toast.LENGTH_SHORT).show();

                            }
                        }).show();

                    } else {
                        Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.nao_loc, Toast.LENGTH_SHORT).show();
                }
            }

        });

        locationManager = (LocationManager) getActivity().
        getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
            Log.i("provider:", provider);
        } else {
            //AlertDialog.Builder builder;
            Toast.makeText(getActivity(),
                    R.string.nao_gps_loc,
                    Toast.LENGTH_SHORT).show();
            Log.i("provider:", provider);
        }

        return view;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = (double) (location.getLatitude());
        double longitude = (double) (location.getLongitude());
        double altitude = (double) (location.getAltitude());
        double precisao = (double) (location.getAccuracy());

        Log.i("Localização: ", latitude+" "+longitude+" "+altitude+" "+precisao);

        ConversaoGMS cg = new ConversaoGMS();
        String sLat = cg.converteGraus(latitude);
        String sLon = cg.converteGraus(longitude);
        String coordLat[] = sLat.split(" ");
        String coordLon[] = sLon.split(" ");
        String norte ="N";
        String leste="E";
        if(latitude<0){
            norte="S";
        }
        if(longitude<0){
            leste="W";
        }
        strLatitude = coordLat[0]+"\u00B0 "+coordLat[1]+"' "+coordLat[2]+"'' "+norte;
        strLongitude = coordLon[0]+"\u00B0 "+coordLon[1]+"' "+coordLon[2]+"'' "+leste;
        strPrecisao = String.valueOf(precisao);
        strAltitude = String.valueOf(altitude);
        tvLatitude.setText(String.format("%.5f", latitude));
        tvLongitude.setText(String.format("%.5f", longitude));
        tvLatgms.setText(strLatitude);
        tvLongms.setText(strLongitude);
        tvPrecisao.setText(strPrecisao);
        tvAltitude.setText(strAltitude);

        getTime time = new getTime();
        String strTime = time.returnTime();
        Log.i("Time:", strTime);

        getDate date = new getDate();
        String strDate = date.returnDate();
        Log.i("Date", strDate);

        tvData.setText(strTime+"   -   "+strDate);

        CoordinateConversion cc = new CoordinateConversion();
        String latlon = cc.latLon2UTM(latitude, longitude);
        Log.i("Convertido > utm:" , latlon);
        String coord[] = latlon.split(" ");
        tvSetor.setText(coord[0] + " " + coord[1]);
        tvNorte.setText(coord[2]);
        tvLeste.setText(coord[3]);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
