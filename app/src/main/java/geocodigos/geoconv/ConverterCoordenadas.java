package geocodigos.geoconv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import geocodigos.geoconv.Conversion.ConversaoGMS;
import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class ConverterCoordenadas extends android.support.v4.app.Fragment {
    DatabaseHelper database;
    private EditText etLat, etLon, etSet1, etSet2, etNor, etLes, etLatGrau,
            etLatMin, etLatSeg, etLonGrau, etLonMin, etLonSeg;
    private ImageButton ibUtmLatLon, ibLatLonUtm, ibGraus;
    private RadioButton rbN, rbS, rbW, rbE;
    private String lat, lon , set, nor, les;

    @Override
    public void onPause(){
        super.onPause();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.attach(ConverterCoordenadas.this);
        ft.commit();
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.conv_coord,
                container, false);

        final View coord_conv = View.inflate(getActivity(), R.layout.coord_conv, null);
        final TextView tvLat = (TextView) coord_conv.findViewById(R.id.textviewLat);
        final TextView tvLon = (TextView) coord_conv.findViewById(R.id.textviewLon);
        final TextView tvSet = (TextView) coord_conv.findViewById(R.id.textviewSetor);
        final TextView tvNor = (TextView) coord_conv.findViewById(R.id.textviewNorte);
        final TextView tvLes = (TextView) coord_conv.findViewById(R.id.textviewLeste);

        etLat = (EditText) view.findViewById(R.id.etlat);
        etLon = (EditText) view.findViewById(R.id.etlong);
        etSet1 = (EditText) view.findViewById(R.id.etNum);
        etSet2 = (EditText) view.findViewById(R.id.etLetra);
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
        rbN = (RadioButton) view.findViewById(R.id.rb_N);
        rbS = (RadioButton) view.findViewById(R.id.rb_S);
        rbE = (RadioButton) view.findViewById(R.id.rb_E);
        rbW = (RadioButton) view.findViewById(R.id.rb_W);

        etSet1.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etSet1.requestFocus();
                }
                return false;
            }
        });
        etSet2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etSet2.requestFocus();
                }
                return false;
            }
        });
        etLon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ibLatLonUtm.callOnClick();
                }
                return false;
            }
        });
        etLes.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ibUtmLatLon.callOnClick();
                }
                return false;
            }
        });
        etLatGrau.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLatGrau.requestFocus();
                }
                return false;
            }
        });
        etLatMin.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLatMin.requestFocus();
                }
                return false;
            }
        });
        etLatSeg.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLatSeg.requestFocus();
                }
                return false;
            }
        });
        etLonGrau.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLonGrau.requestFocus();
                }
                return false;
            }
        });
        etLonMin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if(event.getAction()==KeyEvent.ACTION_DOWN &&
                        keyCode==KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLonMin.requestFocus();
                }
                return false;
            }
        });
        etLonSeg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
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

                if (etSet1.getText().toString().trim().isEmpty() ||
                        etSet2.getText().toString().trim().isEmpty()) {
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
                            set = String.valueOf(etSet1.getText().toString())
                                    +" "+etSet2.getText().toString();
                            ConversaoGMS conversao = new ConversaoGMS();
                            switch (conversao.validacao(set,
                                    etNor.getText().toString(),
                                    etLes.getText().toString())){
                                case 0:
                                    etNor.requestFocus();
                                    nor=etNor.getText().toString();
                                    les=etLes.getText().toString();
                                    String strUtm = set + " " +
                                            etNor.getText().toString() + " " +
                                            etLes.getText().toString();
                                    CoordinateConversion cc = new CoordinateConversion();
                                    double[] latlon = cc.utm2LatLon(strUtm);
                                    lat=String.format("%.5f", latlon[0]);
                                    lon=String.format("%.5f", latlon[1]);

                                    ArrayList<String> array = new ArrayList<String>();
                                    array =  conversao.conversaoGraus(latlon[0], latlon[1]);
                                    String latgms = array.get(0).toString()+
                                            "\u00B0 "+array.get(1).toString()+"' "
                                            +array.get(2).toString()+"''";
                                    String longms = array.get(3).toString()+"\u00B0 "+
                                            array.get(4).toString()+"' "

                                            +array.get(5).toString()+"''";
                                    String strNorte="N", strLeste="E";
                                    if(latlon[0]<0){ strNorte="S";}
                                    if (latlon[1] < 0) {
                                        strLeste = "W";
                                    }
                                    tvLat.setText(String.format("%.5f       %s %s", latlon[0], strNorte,
                                            latgms));
                                    tvLon.setText(String.format("%.5f       %s %s", latlon[1], strLeste,
                                            longms));
                                    tvSet.setText(set);
                                    tvNor.setText(etNor.getText().toString());
                                    tvLes.setText(etLes.getText().toString());

                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setView(coord_conv);
                                    alert.setCancelable(false);
                                    alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                        @Override
                                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                                ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                                parent.removeView(coord_conv);
                                            }
                                            return false;
                                        }
                                    });
                                    alert.setNegativeButton(R.string.fechar, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                            parent.removeView(coord_conv);
                                        }
                                    });
                                    alert.setPositiveButton(R.string.marcar_ponto, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                            parent.removeView(coord_conv);
                                            addPoint();
                                        }
                                    });
                                    alert.setTitle(R.string.coordenadas_convertidas).show();
                                    break;
                                case 1:
                                    Toast.makeText(getActivity(), R.string.quadrante_formato,
                                            Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    Toast.makeText(getActivity(), R.string.norte_formato,
                                            Toast.LENGTH_LONG).show();
                                    break;
                                case 3:
                                    Toast.makeText(getActivity(),
                                            R.string.leste_formato,
                                            Toast.LENGTH_LONG).show();
                                    break;
                            }
                            etSet1.requestFocus();
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
                        ConversaoGMS conversao = new ConversaoGMS();
                        switch (conversao.validacao(strLat,strLon)){
                            case 0:
                                etLat.requestFocus();
                                CoordinateConversion cc = new CoordinateConversion();
                                String latlon = cc.latLon2UTM(strLat, strLon);
                                String coord[] = latlon.split(" ");
                                lat=etLat.getText().toString();
                                lon=etLon.getText().toString();
                                set=coord[0] + " " + coord[1];
                                nor=coord[2];
                                les=coord[3];
                                ArrayList<String> array = new ArrayList<String>();
                                array = conversao.conversaoGraus(strLat, strLon);
                                String latgms = array.get(0).toString()+
                                        "\u00B0 "+array.get(1).toString()+"' "
                                        +array.get(2).toString()+"''";
                                String longms = array.get(3).toString()+"\u00B0 "+
                                        array.get(4).toString()+"' "

                                        +array.get(5).toString()+"''";
                                String strNorte="N", strLeste="E";
                                if(strLat<0){ strNorte="S";}
                                if (strLon < 0) {
                                    strLeste = "W";
                                }

                                tvSet.setText(coord[0] + " " + coord[1]);
                                tvNor.setText(coord[2]);
                                tvLes.setText(coord[3]);

                                double doubleLat = Double.parseDouble(etLat.getText().toString());
                                double doubleLon = Double.parseDouble(etLon.getText().toString());
                                tvLat.setText(String.format("%.5f       %s %s", doubleLat,
                                        strNorte, latgms));
                                tvLon.setText(String.format("%.5f       %s %s", doubleLon,
                                        strLeste, longms));

                                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                ad.setView(coord_conv);
                                ad.setTitle(R.string.coordenadas_convertidas);
                                ad.setNegativeButton(R.string.fechar, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                        parent.removeView(coord_conv);
                                    }
                                });
                                ad.setPositiveButton(R.string.marcar_ponto, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                        parent.removeView(coord_conv);
                                        addPoint();
                                    }
                                });
                                ad.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                            ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                            parent.removeView(coord_conv);
                                        }
                                        return false;
                                    }
                                });
                                ad.show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), R.string.latminmax,
                                        Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(getActivity(), R.string.lonminmax,
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                        etLat.requestFocus();
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
                ConversaoGMS cg = new ConversaoGMS();
                switch (cg.validacao(strLatGrau, strLatMin, strLatSeg, strLonGrau,
                        strLatMin, strLonSeg)){
                    case 0:
                        etLatGrau.requestFocus();
                        double grau = Double.parseDouble(strLatGrau);
                        double min = Double.parseDouble(strLatMin);
                        double seg = Double.parseDouble(strLatSeg);
                        boolean sinal = true;
                        String strNorte = "N";
                        if(rbS.isChecked()){
                            sinal = false; strNorte="S";
                        }
                        String latitude = cg.grausConverte(sinal,grau, min, seg);

                        grau = Double.parseDouble(strLonGrau);
                        min = Double.parseDouble(strLonMin);
                        seg = Double.parseDouble(strLonSeg);
                        String strLeste="E";
                        if(rbW.isChecked()){
                            sinal = false; strLeste="W";
                        } else {sinal = true;}
                        String longitude = cg.grausConverte(sinal,grau, min, seg);

                        CoordinateConversion cc = new CoordinateConversion();
                        String utm = cc.latLon2UTM(Double.parseDouble(latitude),
                                Double.parseDouble(longitude));
                        String utms[] = utm.split(" ");
                        lat=latitude;
                        lon=longitude;
                        set=utms[0] + " " + utms[1];
                        nor=utms[2];
                        les=utms[3];
                        tvSet.setText(set);
                        tvNor.setText(nor);
                        tvLes.setText(les);
                        tvLat.setText(latitude);
                        tvLon.setText(longitude);

                        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                        ad.setView(coord_conv);
                        ad.setTitle(R.string.coordenadas_convertidas);
                        ad.setNegativeButton(R.string.fechar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                parent.removeView(coord_conv);
                            }
                        });
                        ad.setPositiveButton(R.string.marcar_ponto, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                parent.removeView(coord_conv);
                                addPoint();
                            }
                        });
                        ad.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    ViewGroup parent = (ViewGroup) coord_conv.getParent();
                                    parent.removeView(coord_conv);
                                }
                                return false;
                            }
                        });
                        ad.show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),R.string.latminmax,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), R.string.lonminmax, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), R.string.minminmax, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), R.string.minminmax, Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getActivity(), R.string.segminmax, Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getActivity(), R.string.segminmax, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                etLatGrau.requestFocus();
            }
        });

        return view;
    }

    public void addPoint(){
        final View view_marcar = View.inflate(getActivity(), R.layout.adicionar_registro,
                null);
        final EditText etDescricao  = (EditText) view_marcar.findViewById(R.id.add_descricao);
        final EditText etRegistro = (EditText) view_marcar.findViewById(R.id.add_registro);
        final TextView tvlat = (TextView) view_marcar.findViewById(R.id.tv_lat);
        final TextView tvlon = (TextView) view_marcar.findViewById(R.id.tv_lon);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        etRegistro.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN ||
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
                if (event.getAction() == KeyEvent.ACTION_DOWN ||
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
                return false;
            }
        });
        alertDialog.setTitle(R.string.marcar_ponto);
        alertDialog.setView(view_marcar);
        //alertDialog.setCancelable(true);
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

                        }
                        strAux = String.valueOf(numId);
                        PointModel pm = new PointModel();
                        pm.setId(strAux);

                        if (etRegistro.getText().toString().trim().isEmpty()) {
                            etRegistro.setText(getResources().getString(R.string.strRegistro) + strAux);
                        }
                        pm.setRegistro(etRegistro.getText().toString());
                        pm.setDescricao(etDescricao.getText().toString());
                        pm.setLatidude(lat);
                        pm.setLongitude(lon);
                        pm.setSetor(set);
                        pm.setNorte(nor);
                        pm.setLeste(les);
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
                        /*tvlat.setText("Lat: " + pm.latitude);
                        tvlon.setText("Lon: " + pm.longitude);
                        etRegistro.setText("");
                        etDescricao.setText("");*/
                        ViewGroup parent = (ViewGroup) view_marcar.getParent();
                        parent.removeView(view_marcar);
                        imm = (InputMethodManager) getActivity().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                        Toast.makeText(getActivity(), R.string.ponto_marcado,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ViewGroup parent = (ViewGroup) view_marcar.getParent();
                    parent.removeView(view_marcar);
                    dialog.dismiss();
                }
                return false;
            }
        });
        alertDialog.show();
    }

}