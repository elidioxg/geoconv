    package geocodigos.geoconv;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.content.Context;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.inputmethod.InputMethodManager;
    import android.widget.BaseAdapter;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.RadioButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;
    import geocodigos.geoconv.Database.DatabaseHelper;
    import geocodigos.geoconv.Registro.VerRegistro;
    import geocodigos.geoconv.kml.ExportarKML;
    import geocodigos.geoconv.model.PointModel;
    import geocodigos.geoconv.net.bgreco.DirectoryPicker;

    public class VerPontos extends Fragment implements FragmentManager.OnBackStackChangedListener {

        DatabaseHelper database;
        private ImageButton ibExportar, ibMapa, ibExcluir;
        private TextView tvRegistro, tvData, tvHora, tvPontos;
        private ListView listView;

        public ArrayList<PointModel> campos = new ArrayList<PointModel>();

        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.ver_pontos, container, false);

            final View Kml = View.inflate(getActivity(), R.layout.kml_export, null);
            final EditText etCamada = (EditText) Kml.findViewById(R.id.etCamada);
            final RadioButton rbPontos = (RadioButton) Kml.findViewById(R.id.rbponto);
            final RadioButton rbLinha = (RadioButton) Kml.findViewById(R.id.rblinha);
            final RadioButton rbPoligono = (RadioButton) Kml.findViewById(R.id.rbpoligono);

            //TextView tvMarcar = (TextView) view.findViewById(R.id.tv_pontos);
            ImageButton ibExportar = (ImageButton) view.findViewById(R.id.ib_exportar);
            ImageButton ibExcluir = (ImageButton) view.findViewById(R.id.ib_excluir);
            tvPontos = (TextView) view.findViewById(R.id.tv_pontos);
            listView = (ListView) view.findViewById(R.id.lv_registro);

            refreshPoints();

            ibExportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FileOutputStream out;
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                    alerta.setTitle(R.string.exportar_kml);
                    //alerta.setMessage("teste");
                    alerta.setCancelable(false);
                    alerta.setView(Kml);
                    alerta.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ViewGroup parent = (ViewGroup) Kml.getParent();
                            parent.removeView(Kml);
                        }
                    });
                    alerta.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            final InputMethodManager imm;
                            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                    keyCode == KeyEvent.KEYCODE_ENTER) {
                                imm = (InputMethodManager) getActivity().getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            }
                            return false;
                        }
                    });
                    alerta.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener(
                    ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!etCamada.getText().toString().isEmpty()) {
                                int opcao = -1;
                                String nome_camada="";
                                nome_camada = etCamada.getText().toString().trim();
                                ExportarKML exportar = new ExportarKML(getActivity());
                                if (rbPontos.isChecked()) {
                                    opcao = 0;
                                }
                                if (rbLinha.isChecked()) {
                                    opcao = 1;
                                }
                                if (rbPoligono.isChecked()) {
                                    opcao = 2;
                                }

                                //ViewGroup parent = (ViewGroup) Kml.getParent();
                                //parent.removeView(Kml);
                                try {
                                    String param_camada = (String) exportar.criarCamada(nome_camada, opcao);
                                    Intent intent = new Intent(getActivity(), DirectoryPicker.class);
                                    intent.putExtra("nome_camada", nome_camada);
                                    intent.putExtra("param", param_camada);
                                    startActivityForResult(intent, DirectoryPicker.PICK_DIRECTORY);
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
                                Toast.makeText(getActivity(),R.string.arquivo_salvo,
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(),R.string.no_camada,
                                        Toast.LENGTH_SHORT).show();
                            }
                            ViewGroup parent = (ViewGroup) Kml.getParent();
                            parent.removeView(Kml);
                        }
                    }).show();
                }
            });

            ibExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(campos.size()>0) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                        alerta.setTitle(R.string.apagar_registros);
                        //alerta.setMessage("teste");
                        alerta.setCancelable(false);
                        //alerta.setView();
                        alerta.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alerta.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener(
                        ) {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database = new DatabaseHelper(getActivity());
                                database.getWritableDatabase();
                                campos.clear();
                                campos = database.pegarPontos();
                                int campos_size = campos.size();
                                for (int i = 0; i < campos_size; i++) {
                                    if (Integer.parseInt(campos.get(i).getSelecao()) == 1) {
                                        database.removePonto(campos.get(i).getId());
                                        //listView.removeViewsInLayout(i, 1);
                                    }
                                }
                                database.close();
                                refreshPoints();
                                listView.setAdapter(new ListAdapter(getActivity()));
                                synchronized(listView) {
                                    listView.notifyAll();
                                }

                            }
                        }).show();
                    } else {
                        Toast.makeText(getActivity(),R.string.sem_pontos,
                                Toast.LENGTH_SHORT);
                    }
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
            tvPontos.setText("Número de registros: " + Integer.toString(campos.size()));
        }

        @Override
        public void onDestroyView(){
            super.onDestroyView();
            Log.i("VerPontos", "onDestroy");
        }

        @Override
        public void onDetach(){
            super.onDetach();
            Log.i("VerPontos", "onDetach");
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser){
            super.setUserVisibleHint(isVisibleToUser);
            if(isVisibleToUser){
                refreshPoints();
                listView.setAdapter(new ListAdapter(getActivity()));
                synchronized(listView) {
                    listView.notifyAll();
                }
            }
        }
    }