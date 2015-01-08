package geocodigos.geoconv.Registro;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.model.PointModel;

public class VerRegistro extends Fragment {

    private DatabaseHelper database;
    private ArrayList<PointModel> arrayList = new ArrayList<PointModel>();
    private EditText etLatitude, etLongitude, etPrecisao, etAltitude, etSetor,
        etNorte, etLeste, etRegistro, etDescricao, etHora, etData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strId = getArguments().getString("id");
        View view = inflater.inflate(R.layout.ver_registro, container,
                false);

        etLatitude = (EditText) view.findViewById(R.id.etlatitude);
        etLongitude = (EditText) view.findViewById(R.id.etlongitude);
        etRegistro = (EditText) view.findViewById(R.id.etregistro);
        etDescricao = (EditText) view.findViewById(R.id.etdescricao);
        etPrecisao = (EditText) view.findViewById(R.id.etPrecisao);
        etAltitude = (EditText) view.findViewById(R.id.etAltitude);
        etHora = (EditText) view.findViewById(R.id.etHora);
        etData = (EditText) view.findViewById(R.id.etData);
        etSetor = (EditText) view.findViewById(R.id.etSetor);
        etNorte = (EditText) view.findViewById(R.id.etNorte);
        etLeste = (EditText) view.findViewById(R.id.etLeste);

        database = new DatabaseHelper(getActivity());
        database.getWritableDatabase();

        arrayList = database.pegarPontos();

        etLatitude.setText(arrayList.get(Integer.valueOf(strId)).getlatitude());
        etLongitude.setText(arrayList.get(Integer.valueOf(strId)).getLongitude());
        etRegistro.setText(arrayList.get(Integer.valueOf(strId)).getRegistro());
        etDescricao.setText(arrayList.get(Integer.valueOf(strId)).getDescricao());
        etData.setText(arrayList.get(Integer.valueOf(strId)).getData());
        etHora.setText(arrayList.get(Integer.valueOf(strId)).getHora());
        etPrecisao.setText(arrayList.get(Integer.valueOf(strId)).getPrecisao());
        etAltitude.setText(arrayList.get(Integer.valueOf(strId)).getAltitude());
        //etSetor.setText(arrayList.get(Integer.valueOf(strId)).getSetorL());
        etNorte.setText(arrayList.get(Integer.valueOf(strId)).getNorte());
        etLeste.setText(arrayList.get(Integer.valueOf(strId)).getLeste());

        database.close();
        return view;
    }
    //adicionar touch listener para passar os pontos
}
