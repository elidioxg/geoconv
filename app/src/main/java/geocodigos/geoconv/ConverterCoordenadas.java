package geocodigos.geoconv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import geocodigos.geoconv.Conversion.CoordinateConversion;
import geocodigos.geoconv.Conversion.DMSConversion;
import geocodigos.geoconv.Conversion.ValidateConversion;
import geocodigos.geoconv.Database.DatabaseHelper;;
import geocodigos.geoconv.Dialogs.DialogAddPoint;
import geocodigos.geoconv.Utils.CoordinatesArray;
import geocodigos.geoconv.implementation.getDate;
import geocodigos.geoconv.implementation.getTime;
import geocodigos.geoconv.model.PointModel;

public class ConverterCoordenadas extends android.support.v4.app.Fragment {
    private DatabaseHelper database;
    private EditText etLat, etLon, etSet1, etSet2, etNor, etLes, etLatGrau,
            etLatMin, etLatSeg, etLonGrau, etLonMin, etLonSeg;
    private Button ibUtmLatLon, ibLatLonUtm, ibGraus;
    private RadioButton rbN, rbS, rbW, rbE;
    private String lat, lon , set, nor, les;
    private View view;
    private String strFormat = "%.5f";

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conversion_fragment,
                container, false);

        /*AdView ad = (AdView) view.findViewById(R.id.adView2);
        ad.setAdSize(AdSize.SMART_BANNER);
        ad.setAdUnitId(String.valueOf(R.string.ad2));
        AdRequest ar =
                new AdRequest.Builder().addTestDevice("DC230321FB9742079A931AC2BB5B27A5").build();
        ad.loadAd(ar);
        */

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

        ibUtmLatLon = (Button) view.findViewById(R.id.ib_utmlatlon);
        ibLatLonUtm = (Button) view.findViewById(R.id.ib_latlonutm);
        ibGraus = (Button) view.findViewById(R.id.ib_graus);
        rbN = (RadioButton) view.findViewById(R.id.rb_N);
        rbS = (RadioButton) view.findViewById(R.id.rb_S);
        rbE = (RadioButton) view.findViewById(R.id.rb_E);
        rbW = (RadioButton) view.findViewById(R.id.rb_W);

        etSet1.setOnKeyListener(new View.OnKeyListener() {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLatMin.requestFocus();
                }
                return false;
            }
        });
        etLatMin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLatSeg.requestFocus();
                }
                return false;
            }
        });
        etLatSeg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLonGrau.requestFocus();
                }
                return false;
            }
        });
        etLonGrau.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLonMin.requestFocus();
                }
                return false;
            }
        });
        etLonMin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final InputMethodManager imm;
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    etLonSeg.requestFocus();
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
                ValidateConversion validate = new ValidateConversion();
                int error = validate.validate(etSet1.getText().toString() + " " + etSet2.getText().toString(),
                        etNor.getText().toString(), etLes.getText().toString());
                switch (error) {
                    case 0:
                        set = String.valueOf(etSet1.getText().toString())
                                + " " + etSet2.getText().toString();

                        etNor.requestFocus();
                        nor = etNor.getText().toString();
                        les = etLes.getText().toString();
                        String strUtm = set + " " +
                                etLes.getText().toString() + " " +
                                etNor.getText().toString();
                        CoordinateConversion cc = new CoordinateConversion();
                        double[] latlon = cc.utm2LatLon(strUtm);

                        lat = String.format(strFormat, latlon[0]);
                        lon = String.format(strFormat, latlon[1]);
                        lat = lat.replace(",", ".");
                        lon = lon.replace(",", ".");

                        String strNorte = "N", strLeste = "E";
                        if (latlon[1] < 0) {
                            strNorte = "S";
                        }
                        if (latlon[0] < 0) {
                            strLeste = "W";
                        }

                        DMSConversion dms = new DMSConversion();
                        ArrayList<String> array = dms.DegreesConversion(latlon[0], latlon[1]);

                        CoordinatesArray format = new CoordinatesArray();
                        String latgms = format.formatCoordinateToDMS(strNorte,
                                array.get(0).toString(), array.get(1).toString(),
                                array.get(2).toString());

                        String longms = format.formatCoordinateToDMS(strLeste,
                                array.get(3).toString(), array.get(4).toString(),
                                array.get(5).toString());

                        PointModel pm = new PointModel();
                        pm.setLatidude(lat);
                        pm.setLongitude(lon);
                        pm.setSetor(set);
                        pm.setNorte(nor);
                        pm.setLeste(les);
                        pm.setLatDms(latgms);
                        pm.setLonDms(longms);
                        getDate date = new getDate();
                        pm.setData(date.returnDate());
                        getTime time = new getTime();
                        pm.setHora(time.returnTime());
                        pm.setAltitude("");
                        pm.setPrecisao("");

                        DialogAddPoint dialog = new DialogAddPoint(getActivity(), pm);
                        AlertDialog.Builder alert = dialog.createAlertAdd(view);
                        alert.show();
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
        });

        ibLatLonUtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double strLat = Double.parseDouble(etLat.getText().toString());
                double strLon = Double.parseDouble(etLon.getText().toString());
                ValidateConversion vc = new ValidateConversion();
                switch (vc.validate(strLat, strLon)) {
                    case 0:
                        etLat.requestFocus();
                        CoordinateConversion cc = new CoordinateConversion();
                        String latlon = cc.latLon2UTM(strLat, strLon);
                        latlon = latlon.replace(",", ".");
                        String coord[] = latlon.split(" ");
                        lat = etLat.getText().toString();
                        lon = etLon.getText().toString();
                        set = coord[0] + " " + coord[1];
                        nor = coord[2];
                        les = coord[3];
                        ArrayList<String> array = new ArrayList<String>();
                        DMSConversion dms = new DMSConversion();
                        String strNorte = "N", strLeste = "E";
                        if (strLat < 0) {
                            strNorte = "S";
                        }
                        if (strLon < 0) {
                            strLeste = "W";
                        }
                        array = dms.DegreesConversion(strLat, strLon);
                        CoordinatesArray format = new CoordinatesArray();
                        String latgms = format.formatCoordinateToDMS(strNorte,
                                array.get(0).toString(), array.get(1).toString(),
                                array.get(2).toString());

                        String longms = format.formatCoordinateToDMS(strLeste,
                                array.get(3).toString(), array.get(4).toString(),
                                array.get(5).toString());

                        PointModel pm = new PointModel();
                        pm.setLatidude(lat);
                        pm.setLongitude(lon);
                        pm.setSetor(set);
                        pm.setNorte(nor);
                        pm.setLeste(les);
                        pm.setLatDms(latgms);
                        pm.setLonDms(longms);
                        getDate date = new getDate();
                        pm.setData(date.returnDate());
                        getTime time = new getTime();
                        pm.setHora(time.returnTime());
                        pm.setAltitude("");
                        pm.setPrecisao("");

                        DialogAddPoint dialog = new DialogAddPoint(getActivity(), pm);
                        AlertDialog.Builder alert = dialog.createAlertAdd(view);
                        alert.show();
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

                if (strLatGrau.trim().isEmpty()) {
                    strLatGrau = "0";
                }
                if (strLatMin.trim().isEmpty()) {
                    strLatMin = "0";
                }
                if (strLatSeg.trim().isEmpty()) {
                    strLatSeg = "0";
                }
                if (strLonGrau.trim().isEmpty()) {
                    strLonGrau = "0";
                }
                if (strLonMin.trim().isEmpty()) {
                    strLonMin = "0";
                }
                if (strLonSeg.trim().isEmpty()) {
                    strLonSeg = "0";
                }
                ValidateConversion vc = new ValidateConversion();
                switch (vc.validate(strLatGrau, strLatMin, strLatSeg, strLonGrau,
                        strLatMin, strLonSeg)) {
                    case 0:
                        etLatGrau.requestFocus();
                        double grau = Double.parseDouble(strLatGrau);
                        double min = Double.parseDouble(strLatMin);
                        double seg = Double.parseDouble(strLatSeg);
                        boolean sinal = true;
                        String strNorte = "N";
                        if (rbS.isChecked()) {
                            sinal = false;
                            strNorte = "S";
                        }
                        DMSConversion dms = new DMSConversion();
                        String latitude = dms.convertToDegrees(sinal, grau, min, seg);

                        grau = Double.parseDouble(strLonGrau);
                        min = Double.parseDouble(strLonMin);
                        seg = Double.parseDouble(strLonSeg);
                        String strLeste = "E";
                        if (rbW.isChecked()) {
                            sinal = false;
                            strLeste = "W";
                        } else {
                            sinal = true;
                        }
                        String longitude = dms.convertToDegrees(sinal, grau, min, seg);
                        latitude = latitude.replace(",", ".");
                        longitude = longitude.replace(",", ".");

                        CoordinateConversion cc = new CoordinateConversion();
                        String utm = cc.latLon2UTM(Double.parseDouble(latitude),
                                Double.parseDouble(longitude));
                        String utms[] = utm.split(" ");
                        lat = latitude;
                        lon = longitude;
                        set = utms[0] + " " + utms[1];
                        nor = utms[2];
                        les = utms[3];

                        CoordinatesArray format = new CoordinatesArray();
                        String latgms = format.formatCoordinateToDMS(strNorte, strLatGrau,
                                strLatMin, strLatSeg);
                        String longms = format.formatCoordinateToDMS(strLeste, strLonGrau,
                                strLonMin, strLonSeg);

                        PointModel pm = new PointModel();
                        pm.setLatidude(lat);
                        pm.setLongitude(lon);
                        pm.setSetor(set);
                        pm.setNorte(nor);
                        pm.setLeste(les);
                        pm.setLatDms(latgms);
                        pm.setLonDms(longms);
                        getDate date = new getDate();
                        pm.setData(date.returnDate());
                        getTime time = new getTime();
                        pm.setHora(time.returnTime());
                        pm.setAltitude("");
                        pm.setPrecisao("");

                        DialogAddPoint dialog = new DialogAddPoint(getActivity(), pm);
                        AlertDialog.Builder alert = dialog.createAlertAdd(view);
                        alert.show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), R.string.latminmax, Toast.LENGTH_SHORT).show();
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
}