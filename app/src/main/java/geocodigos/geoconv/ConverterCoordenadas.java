package geocodigos.geoconv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
    private EditText etLat, etLon, etSet, etNor, etLes, etLatGrau,
            etLatMin, etLatSeg, etLonGrau, etLonMin, etLonSeg;
    private ImageButton ibUtmLatLon, ibLatLonUtm, ibGraus,ibAddPoint;
    private RadioButton rbN, rbS, rbW, rbE;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.conv_coord,
                container, false);

        final View view_marcar = View.inflate(getActivity(), R.layout.adicionar_registro,
                null);
        final EditText etDescricao  = (EditText) view_marcar.findViewById(R.id.add_descricao);
        final EditText etRegistro = (EditText) view_marcar.findViewById(R.id.add_registro);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

        etLat = (EditText) view.findViewById(R.id.etlat);
        etLon = (EditText) view.findViewById(R.id.etlong);
        etSet = (EditText) view.findViewById(R.id.etquadrante);
        etNor = (EditText) view.findViewById(R.id.etnorte);
        etLes = (EditText) view.findViewById(R.id.etleste);
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

        rbN = (RadioButton) view.findViewById(R.id.rb_N);
        rbS = (RadioButton) view.findViewById(R.id.rb_S);
        rbE = (RadioButton) view.findViewById(R.id.rb_E);
        rbW = (RadioButton) view.findViewById(R.id.rb_W);

        rbN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
            }
        });

        etLon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER){
                    imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLat.requestFocus();
                    ibLatLonUtm.callOnClick();
                }
                return false;
            }
        });
        etLes.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER){
                    imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etNor.requestFocus();
                    ibUtmLatLon.callOnClick();
                }
                return false;
            }
        });
        /*etLatGrau.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    //imm = (InputMethodManager) getActivity().getSystemService(
                      //      Context.INPUT_METHOD_SERVICE);
                    etLatMin.requestFocus();
                }
                return false;
            }
        });*/
        etLonSeg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN&&
                        keyCode==KeyEvent.KEYCODE_ENTER){
                    imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ibGraus.callOnClick();
                }
                return false;
            }
        });

        ibUtmLatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSet.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.quadrante_formato,
                            Toast.LENGTH_LONG).show();
                } else {
                    if (etNor.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), R.string.norte_formato,
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (etLes.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getActivity(), R.string.leste_formato,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if (validacao(etSet.getText().toString(),
                                    etNor.getText().toString(),
                                    etLes.getText().toString())) {
                                String strUtm = etSet.getText().toString() + " " +
                                        etNor.getText().toString() + " " +
                                        etLes.getText().toString();
                                CoordinateConversion cc = new CoordinateConversion();
                                double[] latlon = cc.utm2LatLon(strUtm);
                                Log.i("Convertido > lat", String.format("%.5f", latlon[0]));
                                Log.i("Convertido > lon", String.format("%.5f", latlon[1]));
                                etLat.setText(String.format("%.5f", latlon[0]));
                                etLon.setText(String.format("%.5f", latlon[1]));

                                conversaoGraus(latlon[0], latlon[1]);
                                etNor.requestFocus();
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
                    Toast.makeText(getActivity(), R.string.latminmax,
                            Toast.LENGTH_LONG).show();
                } else {
                    if(etLon.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), R.string.lonminmax,
                                Toast.LENGTH_LONG).show();
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
                        etLat.requestFocus();
                        Toast.makeText(getActivity(), R.string.coordenadas_convertidas,
                                Toast.LENGTH_LONG).show();}
                    }
                }
            }
        });
        ibGraus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strLatGrau = etLatGrau.getText().toString();
                String strLatMin = etLatMin.getText().toString();
                String strLatSeg = etLatSeg.getText().toString();
                String strLonGrau = etLonGrau.getText().toString();
                String strLonMin = etLonMin.getText().toString();
                String strLonSeg = etLonSeg.getText().toString();
                //colocar procedimentos abaixo na função validação
                if(strLatGrau.trim().isEmpty()){
                    strLatGrau="0";
                }
                Log.i("strLatGrau = ", strLatGrau);
                if(strLatMin.trim().isEmpty()){
                    strLatMin="0";
                }
                if(strLatSeg.trim().isEmpty()){
                    strLatSeg="0";
                }
                if(strLonGrau.trim().isEmpty()){
                    strLonGrau="0";
                }
                if(strLonMin.trim().isEmpty()){
                    strLonMin="0";
                }
                if(strLonSeg.trim().isEmpty()){
                    strLonSeg="0";
                }
                if (validacao(strLatGrau, strLatMin, strLatSeg, strLonGrau,
                        strLatMin, strLonSeg)){
                double grau = Double.parseDouble(strLatGrau);
                double min = Double.parseDouble(strLatMin);
                double seg = Double.parseDouble(strLatSeg);
                ConversaoGMS cg = new ConversaoGMS();
                String latitude = cg.grausConverte(grau, min, seg);
                grau = Double.parseDouble(strLonGrau);
                min = Double.parseDouble(strLonMin);
                seg = Double.parseDouble(strLonSeg);
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
                etLatGrau.requestFocus();
                Toast.makeText(getActivity(), R.string.coordenadas_convertidas,
                        Toast.LENGTH_LONG).show();
            }
        });

        ibAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etLat.getText().toString().isEmpty()) {
                    if(!etLon.getText().toString().isEmpty()){
                        alertDialog.setTitle(R.string.marcar_ponto);
                        alertDialog.setView(view_marcar);
                        //alertDialog.setCancelable(true);//ver pra que serve isso

                        alertDialog.setNegativeButton(R.string.cancelar,
                                new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                parent.removeView(view_marcar);
                            }
                        });

                        alertDialog.setPositiveButton(R.string.strMarcar,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final InputMethodManager imm;
                                int numId;
                                boolean strId;
                                String strAux;
                                ArrayList<PointModel> al = new ArrayList<PointModel>();
                                al.clear();
                                database = new DatabaseHelper(getActivity());
                                database.getWritableDatabase();
                                al = database.pegarPontos();

                                numId = al.size() + 1;
                                if (database.pegarId(String.valueOf(numId))) {
                                    do {
                                        numId++;
                                        strId = database.pegarId(String.valueOf(numId));
                                    } while (strId == true);

                                } else {

                                }

                                strAux = String.valueOf(numId);
                                Log.i("Salvando com ID : ", strAux);
                                PointModel pm = new PointModel();
                                pm.setId(strAux);

                                if (etRegistro.getText().toString().trim().isEmpty()) {
                                    etRegistro.setText("Point " + strAux);
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
                                imm = (InputMethodManager) getActivity().getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                //substituir por frame layout mostrando hora data coords
                                etRegistro.setText("");
                                etDescricao.setText("");
                                ViewGroup parent = (ViewGroup) view_marcar.getParent();
                                parent.removeView(view_marcar);
                                Toast.makeText(getActivity(), R.string.ponto_marcado,
                                        Toast.LENGTH_SHORT);
                            }
                        });
                    } else {
                    Toast.makeText(getActivity(),
                            R.string.valor_longitude,
                            Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.show();
                } else {
                    Toast.makeText(getActivity(),
                            R.string.valor_latitude,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private void conversaoGraus(double lat, double lon) {
        if(lat<0){
            rbS.setChecked(true);
        }
        if(lon<0){
            rbW.setChecked(true);
        }
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
        if (latitude <= -90.0 || latitude >= 90.0) {
            Toast.makeText(getActivity(),R.string.latminmax,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(longitude <= -180.0 || longitude >= 180.0)
        {
            Toast.makeText(getActivity(), R.string.lonminmax,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //Colocar procedimentso validacao em ConversaoGMS
    private boolean validacao (String setor, String norte, String leste){

        boolean arg=true;
        Log.i("setor.length",String.valueOf(setor.length()));

        if(setor.length()==4){

        }
        if (setor.length() <4 || setor.length()>4) {
            Toast.makeText(getActivity(), R.string.quadrante_formato,
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        if(Double.parseDouble(norte) <0 || Double.parseDouble(norte) >10000000){
            Toast.makeText(getActivity(), R.string.norte_formato,
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        if(Double.parseDouble(leste) <160000 || Double.parseDouble(leste) >10000000){//834000
            Toast.makeText(getActivity(),
                    R.string.leste_formato,
                    Toast.LENGTH_LONG).show();
            arg=false;
        }
        return arg;
    }

    private boolean validacao(String latgrau, String latmin, String latseg,
                      String longrau, String lonmin, String lonseg ){
        boolean arg=true;
        if(Double.parseDouble(latgrau) >90 || Double.parseDouble(latgrau) <-90){
            arg=false;
            Toast.makeText(getActivity(),R.string.latminmax,Toast.LENGTH_SHORT).show();
        }
        if(arg==true) {
            if (Double.parseDouble(longrau) > 180 || Double.parseDouble(longrau) < -180) {
                arg = false;
                Toast.makeText(getActivity(), R.string.lonminmax, Toast.LENGTH_SHORT).show();
            }
        }
        if(arg==true) {
            if (Double.parseDouble(latmin) >= 60 || Double.parseDouble(latmin) < 0) {
                arg = false;
                Toast.makeText(getActivity(), R.string.minminmax, Toast.LENGTH_SHORT).show();
            }
        }
        if(arg==true) {
            if (Double.parseDouble(lonmin) >= 60 || Double.parseDouble(lonmin) < 0) {
                arg = false;
                Toast.makeText(getActivity(), R.string.minminmax, Toast.LENGTH_SHORT).show();
            }
        }
        if(arg==true) {
            if (Double.parseDouble(latseg) >= 60 || Double.parseDouble(latseg) < 0) {
                arg = false;
                Toast.makeText(getActivity(), R.string.segminmax, Toast.LENGTH_SHORT).show();
            }
        }
        if(arg==true) {
            if (Double.parseDouble(lonseg) >= 60 || Double.parseDouble(lonseg) < 0) {
                arg = false;
                Toast.makeText(getActivity(), R.string.segminmax, Toast.LENGTH_SHORT).show();
            }
        }
        return arg;
    }

}