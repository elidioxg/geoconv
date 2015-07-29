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

import geocodigos.geoconv.Conversion.ConversaoGMS;
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
        etDescricao, etLatGrau, etLatMin, etLatSeg, etLonGrau, etLonMin,
        etLonSeg;
    private ImageButton ibUtmLatLon, ibLatLonUtm, ibGraus,ibAddPoint;

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
        etLatGrau = (EditText) view.findViewById(R.id.etLatgraus);
        etLatMin = (EditText) view.findViewById(R.id.etLatminutos);
        etLatSeg = (EditText) view.findViewById(R.id.etLatsegundos);
        etLonGrau = (EditText) view.findViewById(R.id.etLongraus);
        etLonMin = (EditText) view.findViewById(R.id.etLonminutos);
        etLonSeg = (EditText) view.findViewById(R.id.etLonsegundos);

        ibUtmLatLon = (ImageButton) view.findViewById(R.id.ib_utmlatlon);
        ibLatLonUtm = (ImageButton) view.findViewById(R.id.ib_latlonutm);
        ibGraus = (ImageButton) view.findViewById(R.id.ib_graus);
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
                            if (validacao(etSet.getText().toString(),
                                    etNor.getText().toString(),
                                    etLes.getText().toString())) {
                                String strUtm = etSet.getText().toString() + " " +
                                        etNor.getText().toString() + " " +
                                        etLes.getText().toString();
                                CoordinateConversion cc = new CoordinateConversion();
//mostrar apenas 5 casas decimais
                                double[] latlon = cc.utm2LatLon(strUtm);
                                Log.i("Convertido > lat", String.valueOf(latlon[0]));
                                Log.i("Convertido > lon", String.valueOf(latlon[1]));
                                etLat.setText(String.valueOf(latlon[0]));
                                etLon.setText(String.valueOf(latlon[1]));

                                conversaoGraus(latlon[0], latlon[1]);

                                Toast.makeText(getActivity(), R.string.coordenadas_convertidas,
                                        Toast.LENGTH_LONG).show();
                            }
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
                        //substituir por Double.parseDouble()
                        double strLat = Float.valueOf(etLat.getText().toString());
                        double strLon = Float.valueOf(etLon.getText().toString());
                        if (validacao(strLat, strLon)){
                        CoordinateConversion cc = new CoordinateConversion();
                        String latlon = cc.latLon2UTM(strLat, strLon);
                        Log.i("Convertido > utm:", latlon);
                        String coord[] = latlon.split(" ");
                        etSet.setText(coord[0] + " " + coord[1]);
                        etNor.setText(coord[2]);
                        etLes.setText(coord[3]);
                        conversaoGraus(strLat, strLon);

                        Toast.makeText(getActivity(), R.string.coordenadas_convertidas,
                                Toast.LENGTH_LONG).show();}
                    }
                }
            }
        });
        ibGraus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacao(etLatGrau.getText().toString(), etLatMin.getText().toString(),
                        etLatSeg.getText().toString(), etLonGrau.getText().toString(),
                        etLonMin.getText().toString(), etLonSeg.getText().toString())){
                double grau = Double.parseDouble(etLatGrau.getText().toString());
                double min = Double.parseDouble(etLatMin.getText().toString());
                double seg = Double.parseDouble(etLatSeg.getText().toString());
                ConversaoGMS cg = new ConversaoGMS();
                String latitude = cg.grausConverte(grau, min, seg);
                grau = Double.parseDouble(etLonGrau.getText().toString());
                min = Double.parseDouble(etLonMin.getText().toString());
                seg = Double.parseDouble(etLonSeg.getText().toString());
                String longitude = cg.grausConverte(grau, min, seg);
                etLat.setText(latitude);
                etLon.setText(longitude);
                CoordinateConversion cc = new CoordinateConversion();
                String utm = cc.latLon2UTM(Double.parseDouble(latitude),
                        Double.parseDouble(longitude));
                String utms[] = utm.split(" ");
                etSet.setText(utms[0]+" "+utms[1]);
                etNor.setText(utms[2]);
                etLes.setText(utms[3]);
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
                Log.i("Salvando com ID : ", strAux);

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

                pm.setNorte(etNor.getText().toString());
                pm.setLeste(etLes.getText().toString());
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
            }
        });

        return view;
    }
    private void conversaoGraus(double lat, double lon) {
        ConversaoGMS cg = new ConversaoGMS();
        String strLat = cg.converteGraus(lat);
        String strLon = cg.converteGraus(lon);
        String coordLat[] = strLat.split(" ");
        etLatGrau.setText(coordLat[0]);
        etLatMin.setText(coordLat[1]);
        etLatSeg.setText(coordLat[2]);
        String coordLon[] = strLon.split(" ");
        etLonGrau.setText(coordLon[0]);
        etLonMin.setText(coordLon[1]);
        etLonSeg.setText(coordLon[2]);
    }
    private boolean validacao(double latitude, double longitude){
        boolean arg = true;
        if (latitude < -90.0 || latitude > 90.0) {
            Toast.makeText(getActivity(), "Latitude deve ter os valores entre -90 e 90",
                    Toast.LENGTH_LONG).show();
            arg = false;
        }
        if(longitude < -180.0 || longitude >= 180.0)
        {
            Toast.makeText(getActivity(), "Longitude deve ser entre -180 e 179.9",
                    Toast.LENGTH_LONG).show();
            arg = false;
        }
        return arg;
    }
    private boolean validacao (String setor, String norte, String leste){
        boolean arg=true;
        Log.i("setor.length",String.valueOf(setor.length()));
        if (setor.length() ==3){

        }
        if(setor.length()==4){

        }
        if (setor.length() <3 || setor.length()>4) {
            Toast.makeText(getActivity(), "Quadrante tem que ser forma 'Numero Letra'. Exemplo: 24 L",
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        if(Double.parseDouble(norte) <0 || Double.parseDouble(norte) >10000){
            Toast.makeText(getActivity(), "Valor da coordenada Norte deve ser entre 0 e 10000",
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        if(Double.parseDouble(leste) <160000 || Double.parseDouble(leste) >834000){
            Toast.makeText(getActivity(), "Valores das Coordenadas Leste devem ser entre 160000 e 834000",
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        return arg;
    }

    private boolean validacao(String latgrau, String latmin, String latseg,
                      String longrau, String lonmin, String lonseg ){
        return true;
    }

}
