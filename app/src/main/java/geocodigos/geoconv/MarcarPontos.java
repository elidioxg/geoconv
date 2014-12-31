package geocodigos.geoconv;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    LocationManager locationManager;
    String provider;
    public String strLatitude, strLongitude, strPrecisao, strAltitude;
    ImageButton ibMarcar;
    DatabaseHelper database;
    public TextView tvLatitude, tvLongitude, tvPrecisao, tvAltitude,//private
        tvData;
    EditText etRegistro, etDescricao;
    public ArrayList<PointModel> al;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.marcar_pontos,
                container, false);

        etRegistro = (EditText) view.findViewById(R.id.et_registro);
        etDescricao = (EditText) view.findViewById(R.id.et_descricao);
        tvLatitude = (TextView) view.findViewById(R.id.tv_latitude);
        tvLongitude = (TextView) view.findViewById(R.id.tv_longitude);
        tvPrecisao = (TextView) view.findViewById(R.id.in_precisao);
        tvAltitude = (TextView) view.findViewById(R.id.in_altitude);
        tvData = (TextView) view.findViewById(R.id.tv_data);

        Calendar calendar = Calendar.getInstance();
        int iAno = calendar.get(Calendar.YEAR);
        int iMes = calendar.get(Calendar.MONTH);
        int iDia = calendar.get(Calendar.DAY_OF_MONTH);
        String strData =
                String.valueOf(iAno)+"-"+String.valueOf(iMes)+
                        "-"+String.valueOf(iDia);
        //colocar para atualizar data automaticamente
        String sData = String.valueOf(strData);
        tvData.setText(sData);

        ImageButton ibMarcar = (ImageButton) view.findViewById(R.id.ib_marcar);
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
                        al = database.pegarPontos();

                        numId= al.size() + 1;
                        if(database.pegarId(String.valueOf(numId))) {
                            do {
                                numId++;
                                strId = database.pegarId(String.valueOf(numId));
                            } while (strId == true);

                        } else {

                        }
                        strAux= String.valueOf(numId);
                        Log.i("Salvando com ID (numId) : ", String.valueOf(numId));

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
                        //pm.setNorte();
                        //pm.setLeste();
                        //pm.setSetorN();
                        //pm.setSetorL();

                        Calendar c = Calendar.getInstance();
                        int iAno = c.get(Calendar.YEAR);
                        int iMes = c.get(Calendar.MONTH);
                        int iDia = c.get(Calendar.DAY_OF_MONTH);
                        String sqlData =
                                String.valueOf(iAno)+"-"+String.valueOf(iMes)+
                                        "-"+String.valueOf(iDia);
                        int iHora = c.get(Calendar.HOUR_OF_DAY);
                        int iMin = c.get(Calendar.MINUTE);
                        int iSeg = c.get(Calendar.SECOND);
                        String sqlHora = String.valueOf(iHora)+":"+
                                String.valueOf(iMin)+":"+String.valueOf(iSeg);
                        pm.setData(sqlData);
                        pm.setHora(sqlHora);
                        Log.i("Data" , sqlData);
                        Log.i("Hora", sqlHora);

                        al.add(pm);
                        database.addPoint(pm);

                        Log.i("id", pm.id);
                        Log.i("Registro ", pm.registro);
                        Log.i("Latitude ", pm.latitude);
                        Log.i("Descricao ", pm.descricao);
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
