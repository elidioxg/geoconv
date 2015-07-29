    package geocodigos.geoconv;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentTransaction;
    import android.content.Context;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.RadioButton;
    import android.widget.TextView;

    import java.io.IOException;
    import java.io.OutputStreamWriter;
    import java.util.ArrayList;
    import geocodigos.geoconv.Database.DatabaseHelper;
    import geocodigos.geoconv.Map.Mapa;
    import geocodigos.geoconv.Registro.VerRegistro;
    import geocodigos.geoconv.kml.ExportarKML;
    import geocodigos.geoconv.model.PointModel;

    public class VerPontos extends Fragment implements FragmentManager.OnBackStackChangedListener {

        DatabaseHelper database;
        private ImageButton ibExportar, ibMapa, ibExcluir;
        private TextView tvRegistro, tvData, tvHora, tvPontos;
        private ListView listView;

        public ArrayList<PointModel> campos = new ArrayList<PointModel>();

        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ver_pontos, container, false);

            final View Kml = View.inflate(getActivity(), R.layout.kml_export, null);

            final RadioButton rbPontos = (RadioButton) view.findViewById(R.id.rbponto);
            final RadioButton rbLinha = (RadioButton) view.findViewById(R.id.rblinha);
            final RadioButton rbPoligono = (RadioButton) view.findViewById(R.id.rbpoligono);

            //TextView tvMarcar = (TextView) view.findViewById(R.id.tv_pontos);
            ImageButton ibExportar = (ImageButton) view.findViewById(R.id.ib_exportar);
            ImageButton ibExcluir = (ImageButton) view.findViewById(R.id.ib_excluir);
            tvPontos = (TextView) view.findViewById(R.id.tv_pontos);
            listView = (ListView) view.findViewById(R.id.lv_registro);

            refreshPoints();

            ibExportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                    alerta.setTitle(R.string.exportar_kml);
                    //alerta.setMessage("teste");
                    alerta.setCancelable(false);
                    alerta.setView(Kml);
                    alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener(

                    ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int opcao=-1;
                            ExportarKML exportar = new ExportarKML();
                            if(rbPontos.isChecked()){
                                opcao=0;
                            }
                            if(rbLinha.isChecked()){
                                opcao=1;
                            }
                            if(rbPoligono.isChecked()){
                                opcao=2;
                            }
                            try {
                                String string = (String) exportar.criarCamada(opcao);
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
                    }).show();
                }

            });

            ibExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                    alerta.setTitle(R.string.apagar_registros);
                    //alerta.setMessage("teste");
                    alerta.setCancelable(false);
                    //alerta.setView();
                    alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener(
                    ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database = new DatabaseHelper(getActivity());
                            database.getWritableDatabase();
                            campos.clear();
                            campos = database.pegarPontos();
                            for(int i=1; i<campos.size();i++){
                                if(Integer.parseInt(campos.get(i).getSelecao())==1){
                                    database.removePonto(campos.get(i).getId());
                                }
                            }
                            database.close();
                            listView.getAdapter().notify();

                            }
                    }).show();

                }
            });

            listView.setAdapter(new ListAdapter(getActivity()));

            return view;
        }

        @Override
        public void onBackStackChanged() {
//            refreshPoints();
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
                CheckBox cbSelecionado;
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
                    viewHolder.cbSelecionado=(CheckBox) convertView.findViewById(R.id.cb_ponto);
                    viewHolder.cbSelecionado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            PointModel pm = new PointModel();
                            pm.setId(campos.get(position).getId());
                            database = new DatabaseHelper(getActivity());
                            database.getWritableDatabase();
                            campos.clear();
                            campos = database.pegarPontos();
                            if(isChecked){
                                pm.setSelecao("1");
                            } else {
                                pm.setSelecao("0");
                            }
                            database.updateSelecao(pm);
                            database.close();
                        }
                    });

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
                Log.i("Id:", campos.get(position).getId());
                Log.i("Selecionado", campos.get(position).getSelecao());
                if(Integer.parseInt(campos.get(position).getSelecao().trim())==1){
                    viewHolder.cbSelecionado.setChecked(true);
                } else {
                    viewHolder.cbSelecionado.setChecked(false);
                }
                //final int temp = position;
                return convertView;
            }

        }

        private void refreshPoints(){
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
                    String setor = campos.get(i).getSetor();
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
                    pointModel.setSetor(setor);
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
            //tvpontos nao aparece
            tvPontos.setText("Número de registros: " + Integer.toString(campos.size()));

        }
    }
