package geocodigos.geoconv;

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

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.model.PointModel;

public class MarcarPontos extends Fragment implements LocationListener {
    LocationManager locationManager;
    String provider;
    public String strLatitude, strLongitude, strPrecisao;
    ImageButton ibMarcar;
    DatabaseHelper database;
    public TextView tvLatitude, tvLongitude, tvPrecisao;
        EditText etRegistro;
        public ArrayList<PointModel> al;

        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.marcar_pontos,
                    container, false);

            etRegistro = (EditText) view.findViewById(R.id.et_registro);
            tvLatitude = (TextView) view.findViewById(R.id.tv_latitude);
            tvLongitude = (TextView) view.findViewById(R.id.tv_longitude);

            ImageButton ibMarcar = (ImageButton) view.findViewById(R.id.ib_marcar);
            ibMarcar.setOnClickListener(new View.OnClickListener() {
                private boolean aux;
                int numId;
                @Override
                public void onClick(View v) {

                    if (!etRegistro.getText().toString().isEmpty()) {

                        ArrayList<PointModel> al = new ArrayList<PointModel>();
                        al.clear();

                        if (!tvLatitude.getText().toString().isEmpty()) {

                            if (!tvLongitude.getText().toString().isEmpty()) {

                                database = new DatabaseHelper(getActivity());
                                database.getWritableDatabase();
                                al = database.pegarPontos();

                                //procedimento para ver qual id esta disponivel
                                //esse procedimento pode ser substituido por uma funcao
                                //em DatabaseHelper com Select id From table
                                numId=0;
                                aux=false;
                                do {
                                    for (int i=0; i< al.size(); i++){

                                        String strId = Integer.toString(i);
                                        String strDb = al.get(i).getId();
                                        Log.i("strDb: ", strDb);
                                        Log.i("strId: ", strId);
                                        if(strId != strDb) {
                                            numId=i+1;
                                            Log.i("numId:", Integer.toString(numId));
                                            aux=true;
                                        }
                                    }
                                } while (aux==false);
                                ////

                                PointModel pm = new PointModel();
                                pm.setId(Integer.toString(numId));
                                pm.setRegistro(etRegistro.getText().toString());
                                pm.setLatidude(tvLatitude.getText().toString());
                                pm.setLongitude(tvLongitude.getText().toString());
                                Log.i("MarcarPonto: id:", Integer.toString(al.size()+1));
                                al.add(pm);

                                database.addPoint(pm);

                                Log.i("id", pm.id);
                                Log.i("Registro ", pm.registro);
                                Log.i("Latitude ", pm.latitude);
                                //database.close();

                            } else {
                                Toast.makeText(getActivity(), "Não foi possível obter a "+
                                        "localização atual", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Não foi possível obter a "+
                                    "localização atual", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Adicione o nome do registro",
                                Toast.LENGTH_SHORT).show();
                    }

                }

        });

        locationManager = (LocationManager) getActivity().
        getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //provider nao funciona, sempre retorna null
        provider  = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider: "+provider+ "foi selecionado.");
            onLocationChanged(location);
            Log.i("provider:", provider);
        } else {
            //dizer nos textview que esta desativado
            //mostrar dialogo se quer ativar
            //se for ativar:
            Log.i("provider:", provider);
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
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

        Log.i("Localização: ", latitude+" "+longitude+" "+altitude);

        strLatitude = String.valueOf(latitude);
        strLongitude = String.valueOf(longitude);
        strPrecisao = String.valueOf(precisao);
        tvLatitude.setText(strLatitude);
        tvLongitude.setText(strLongitude);
        tvPrecisao.setText(strPrecisao);


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
