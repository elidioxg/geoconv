    package geocodigos.geoconv;

    import android.content.Intent;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentTransaction;
    import android.content.Context;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.TextView;

    import java.io.IOException;
    import java.util.ArrayList;
    import geocodigos.geoconv.Database.DatabaseHelper;
    import geocodigos.geoconv.Map.Mapa;
    import geocodigos.geoconv.Registro.VerRegistro;
    import geocodigos.geoconv.kml.ExportarKML;
    import geocodigos.geoconv.model.PointModel;

    public class VerPontos extends Fragment {

        DatabaseHelper database;
        private ImageButton ibExportar, ibMapa, ibExcluir;
        private TextView tvRegistro, tvData, tvHora;
        private ListView listView;
        public ArrayList<PointModel> campos = new ArrayList<PointModel>();

        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ver_pontos, container, false);

            //TextView tvMarcar = (TextView) view.findViewById(R.id.tv_pontos);
            ImageButton ibExportar = (ImageButton) view.findViewById(R.id.ib_exportar);
            ImageButton ibExcluir = (ImageButton) view.findViewById(R.id.ib_excluir);
            TextView tvPontos = (TextView) view.findViewById(R.id.tv_pontos);
            listView = (ListView) view.findViewById(R.id.lv_registro);

            database = new DatabaseHelper(getActivity());
            database.getWritableDatabase();

            campos.clear();
            campos = database.pegarPontos();
            Log.i("campos.size():", Integer.toString(campos.size()));
            if (!campos.isEmpty()) {
                for (int i = campos.size(); i < 0 ; i--) {

                    String id = campos.get(i).getId();
                    Log.i("id: ", id);
                    String registro = campos.get(i).getRegistro();
                    //Log.i("campos(i).getRegistro", "campos("+i+")  Registro="+registro);
                    String latitude = campos.get(i).getlatitude();
                    String longitude = campos.get(i).getLongitude();
                    //String setorl = campos.get(i).getSetorL();
                    //String setorn = campos.get(i).getSetorN();
                    String norte = campos.get(i).getNorte();
                    String leste = campos.get(i).getLeste();
                    String descricao = campos.get(i).getDescricao();
                    String precisao = campos.get(i).getPrecisao();
                    String altitude = campos.get(i).getAltitude();
                    String hora = campos.get(i).getHora();
                    String data = campos.get(i).getData();

                    PointModel pointModel = new PointModel();

                    pointModel.setId(id);
                    pointModel.setRegistro(registro);
                    pointModel.setLatidude(latitude);
                    pointModel.setLongitude(longitude);
                    //pointModel.setSetorL(setorl);
                    //pointModel.setSetorN(setorn);
                    pointModel.setNorte(norte);
                    pointModel.setLeste(leste);
                    pointModel.setDescricao(descricao);
                    pointModel.setAltitude(altitude);
                    pointModel.setPrecisao(precisao);
                    pointModel.setHora(hora);
                    pointModel.setData(data);

                    //Log.i("id.toString", pointModel.id.toString());
                    //Log.i("id:", pointModel.id);
                    Log.i("campos.size()", Integer.toString(campos.size()));
                    campos.add(pointModel);
                }
            }
            database.close();
            tvPontos.setText("Número de registros: " + Integer.toString(campos.size()));

            ibExportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ExportarKML exportar = new ExportarKML();
                    //passar os pontos em ExportarKML atraves de Bundle e intent
                    //Bundle bundle = new Bundle();
                    try {
                        String string = (String) ExportarKML.CreateXMLString();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            });

            ibExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            listView.setAdapter(new ListAdapter(getActivity()));

            return view;
        }

        private class ListAdapter extends BaseAdapter {

            LayoutInflater inflater;
            ViewHolder viewHolder;

            public ListAdapter(Context c) {
                inflater = LayoutInflater.from(c);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return campos.size();
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            private class ViewHolder{
                TextView tvRegistro;
                TextView tvHora;
                TextView tvData;
                TextView tvDescricao;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub


                if (convertView == null ){

                    convertView = View.inflate(getActivity(), R.layout.linha_listview, null);

                    viewHolder = new ViewHolder();
                    viewHolder.tvRegistro = (TextView) convertView.findViewById(R.id.tvregistro);
                    viewHolder.tvData = (TextView) convertView.findViewById(R.id.tvdata);
                    viewHolder.tvHora = (TextView) convertView.findViewById(R.id.tvhora);
                    viewHolder.tvDescricao = (TextView) convertView.findViewById(R.id.tv_descricao);

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("onClick - position", String.valueOf(position));

                            Bundle bundle = new Bundle();
                            bundle.putInt("id", position);
                            bundle.putInt("total_registros", campos.size());
                            VerRegistro verRegistro = new VerRegistro();
                            Intent intent = new Intent(getActivity(), VerRegistro.class);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }
                    });
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.tvRegistro.setText(campos.get(position).getRegistro().trim());
                viewHolder.tvData.setText(campos.get(position).getData().trim());
                viewHolder.tvHora.setText(campos.get(position).getHora().trim());
                viewHolder.tvDescricao.setText(campos.get(position).getDescricao().trim());

                //final int temp = position;

                return convertView;
            }

        }
    }
