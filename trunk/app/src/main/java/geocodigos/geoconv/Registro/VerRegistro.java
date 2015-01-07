package geocodigos.geoconv.Registro;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import geocodigos.geoconv.R;

public class VerRegistro extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strId = getArguments().getString("id");
        View view = inflater.inflate(R.layout.ver_registro, container,
                false);

        return view;
    }
    //adicionar touch listener para passar os pontos
}
