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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    LocationManager locationManager;
    String provider;
    public String strLatitude, strLongitude, strPrecisao, strAltitude;
    private ImageButton ibMarcar, ibExcluir;
    DatabaseHelper database;
    private TextView tvLatitude, tvLongitude, tvPrecisao, tvAltitude,
        tvSetor, tvNorte, tvLeste;
    EditText etRegistro, etDescricao;
    public ArrayList<PointModel> al;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.marcar_pontos,
                container, false);

        etRegistro = (EditText) view.findViewById(R.id.et_registro);
        etDescricao = (EditText) view.findViewById(R.id.et_descricao);
        tvLatitude = (TextView) view.findViewById(R.id.in_latitude);
        tvLongitude = (TextView) view.findViewById(R.id.in_longitude);
        tvPrecisao = (TextView) view.findViewById(R.id.in_precisao);
        tvAltitude = (TextView) view.findViewById(R.id.in_altitude);

        tvSetor = (TextView) view.findViewById(R.id.in_quadrante);
        tvNorte = (TextView) view.findViewById(R.id.in_norte);
        tvLeste = (TextView) view.findViewById(R.id.in_leste);

        getDate date = new getDate();
        String strDate = date.returnDate();
        //Log.i("MarcarPonto.java", "strDate: "+strDate);

        ibExcluir = (ImageButton) view.findViewById(R.id.ib_excluir);

        ibMarcar = (ImageButton) view.findViewById(R.id.ib_marcar);

        ibMarcar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("onCLick", "Botao Adicionar _____");
                boolean strId;
                String strAux;
                int numId;

                ArrayList<PointModel> al = new ArrayList<PointModel>();
                al.clear();

                if (!tvLatitude.getText().toString().isEmpty()) {

                    if (!tvLongitude.getText().toString().isEmpty()) {

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
                        Log.i("Salvando com ID (numId) : ", strAux);

                        PointModel pm = new PointModel();
                        pm.setId(Integer.toString(numId));
                        if (etRegistro.getText().toString().isEmpty()) {
                            etRegistro.setText("Registro "+String.valueOf(numId));
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

                    } else {
                        Toast.makeText(getActivity(), "Não foi possível obter a "+
                                "localização atual", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Não foi possível obter a "+
                            "localização atual", Toast.LENGTH_SHORT).show();
                }
        Log.i("onClick", "Fim do onclickListener_______");
            }

        });

        locationManager = (LocationManager) getActivity().
        getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //provider nao funciona, sempre retorna null
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider: "+provider+ " foi selecionado.");
            onLocationChanged(location);
            Log.i("provider:", provider);
        } else {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("GPS desativado");
            builder.setMessage("Deseja ativar?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
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

        //colocar procedimentos para converter coordenadas aqui

        strLatitude = String.valueOf(latitude);
        strLongitude = String.valueOf(longitude);
        strPrecisao = String.valueOf(precisao);
        strAltitude = String.valueOf(altitude);
        tvLatitude.setText(strLatitude);
        tvLongitude.setText(strLongitude);
        tvPrecisao.setText(strPrecisao);
        tvAltitude.setText(strAltitude);

        double strLat = Float.valueOf(tvLatitude.getText().toString());
        double strLon = Float.valueOf(tvLongitude.getText().toString());
        CoordinateConversion cc = new CoordinateConversion();
        String latlon = cc.latLon2UTM(strLat, strLon);
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
