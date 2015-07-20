package geocodigos.geoconv;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;
import android.support.v4.app.Fragment;
import static android.widget.Toast.*;


public class ConverterCoordenadas extends android.support.v4.app.Fragment {
    DatabaseHelper database;
    private EditText etLat, etLon, etSet, etNor, etLes, etRegistro,
        etDescricao;
    private ImageButton ibUtmLatLon, ibLatLonUtm, ibAddPoint;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.conv_coord,
                container, false);
        etLat = (EditText) view.findViewById(R.id.etlat);
        etLon = (EditText) view.findViewById(R.id.etlong);
        etSet = (EditText) view.findViewById(R.id.etquadrante);
        etNor = (EditText) view.findViewById(R.id.etnorte);
        etLes = (EditText) view.findViewById(R.id.etleste);
        etDescricao  = (EditText) view.findViewById(R.id.edittextDescricao);
        etRegistro = (EditText) view.findViewById(R.id.edittextRegistro);
        ibUtmLatLon = (ImageButton) view.findViewById(R.id.ib_utmlatlon);
        ibLatLonUtm = (ImageButton) view.findViewById(R.id.ib_latlonutm);
        ibAddPoint = (ImageButton) view.findViewById(R.id.ib_marcar);

        ibUtmLatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etSet.getText().toString().trim().isEmpty()){

                } else {
                    if(etNor.getText().toString().trim().isEmpty()) {

                    } else {
                        if(etLes.getText().toString().trim().isEmpty()) {

                        } else {
                            String strUtm = etSet.getText().toString() +" "+
                                    etNor.getText().toString() + " "+
                                    etLes.getText().toString();
                            CoordinateConversion cc = new CoordinateConversion();

                            double[] latlon = cc.utm2LatLon(strUtm);
                            Log.i("Convertido > lat", String.valueOf(latlon[0]));
                            Log.i("Convertido > lon", String.valueOf(latlon[1]));

                            etLat.setText(String.valueOf(latlon[0]));
                            etLon.setText(String.valueOf(latlon[1]));
                        }
                    }
                }
            }
        });

        ibLatLonUtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etLat.getText().toString().isEmpty()) {

                } else {
                    if(etLon.getText().toString().isEmpty()) {

                    } else {
                        double strLat = Float.valueOf(etLat.getText().toString());
                        double strLon = Float.valueOf(etLon.getText().toString());

                        CoordinateConversion cc = new CoordinateConversion();
                        String latlon = cc.latLon2UTM(strLat, strLon);
                        Log.i("Convertido > utm:" , latlon);

                        String coord[] = latlon.split(" ");
                        etSet.setText(coord[0]+" "+coord[1]);
                        etNor.setText(coord[2]);
                        etLes.setText(coord[3]);
                    }
                }
            }
        });

        ibAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numId;
                boolean strId;
                String strAux;
                ArrayList<PointModel> al = new ArrayList<PointModel>();
                al.clear();
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
                Log.i("Salvando com ID (numId) : ", strAux);

                PointModel pm = new PointModel();
                pm.setId(Integer.toString(numId));
                if (etRegistro.getText().toString().isEmpty()) {
                    etRegistro.setText("Registro "+String.valueOf(numId));
                }
                pm.setRegistro(etRegistro.getText().toString());
                pm.setLatidude(etLat.getText().toString());
                pm.setLongitude(etLon.getText().toString());
                pm.setDescricao(etDescricao.getText().toString());
                pm.setSetor(etSet.getText().toString());

                //pm.setPrecisao(tvPrecisao.getText().toString());
                //pm.setNorte();
                //pm.setLeste();
                //pm.setSetorN();
                //pm.setSetorL();

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
            }
        });

        return view;
    }
}
