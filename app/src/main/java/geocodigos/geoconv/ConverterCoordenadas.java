package geocodigos.geoconv;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import geocodigos.geoconv.R;


public class ConverterCoordenadas extends Fragment {
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.converter_coordenadas,
                container, false);
        TextView tvConverter = (TextView) view.findViewById(R.id.tv_converter);
        tvConverter.setText(R.string.converter_coord);
        return view;
    }
}
