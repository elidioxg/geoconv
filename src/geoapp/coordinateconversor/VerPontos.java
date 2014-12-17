package geoapp.coordinateconversor;

import java.io.IOException;
import java.util.ArrayList;

import geoapp.coordinateconversor.database.DatabaseHelper;
import geoapp.coordinateconversor.kml.ExportarKML;
import geoapp.coordinateconversor.model.PointModel;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class VerPontos extends Fragment {
	
	DatabaseHelper database;
	ImageButton ibExportar;
	TextView tvPontos;
	private ListView listView;
	public ArrayList<PointModel> campos = new ArrayList<PointModel>();
	
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.ver_pontos, 
				container, false);
		
		ImageButton ibExportar = (ImageButton) view.findViewById(R.id.ib_exportar);
		TextView tvPontos = (TextView) view.findViewById(R.id.tv_pontos);
		listView = (ListView) view.findViewById(R.id.lv_registro);
		
		ibExportar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExportarKML exportar = new ExportarKML();
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
		
		TextView tvMarcar = (TextView) view.findViewById(R.id.tv_pontos);
		tvMarcar.setText(R.string.ver_pontos);
		database = new DatabaseHelper(getActivity());
		database.getWritableDatabase();
	
		campos.clear();
		campos = database.pegarPontos(); 
		Log.i("campos.size():", Integer.toString(campos.size()));
		if (!campos.isEmpty()) {
			for (int i =0; i<campos.size(); i++) {

				String id = campos.get(i).getId();
				String registro = campos.get(i).getRegistro();
				Log.i("campos(i).getRegistro", "campos("+i+")  Registro="+registro);
				String latitude = campos.get(i).getlatitude();
				String longitude = campos.get(i).getLongitude();
				String setorl = campos.get(i).getSetorL();
				String setorn = campos.get(i).getSetorN();
				String norte = campos.get(i).getNorte();
				String leste = campos.get(i).getLeste();
				String descricao = campos.get(i).getDescricao();

				PointModel pointModel = new PointModel();

				pointModel.setId(id);
				pointModel.setRegistro(registro);
				pointModel.setLatidude(latitude);
				pointModel.setLongitude(longitude);
				pointModel.setSetorL(setorl);
				pointModel.setSetorN(setorn);
				pointModel.setNorte(norte);
				pointModel.setLeste(leste);
				pointModel.setDescricao(descricao);
				//Log.i("id", pointModel.id.toString());
				Log.i("id:", pointModel.id);
				Log.i("campos.size()", Integer.toString(campos.size()));
				//campos.add(pointModel);
			}
		}
		tvPontos.setText("Número de registros: "+Integer.toString(campos.size()));
		listView.setAdapter(new ListAdapter(getActivity()));
		database.close();
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			
			if (convertView == null ){
				
				convertView = View.inflate(getActivity(), R.layout.linha_listview, null);
				
				viewHolder = new ViewHolder();
				viewHolder.tvRegistro = (TextView) convertView.findViewById(R.id.tv_registro);
				viewHolder.tvLatitude = (TextView) convertView.findViewById(R.id.tv_latitude);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tvRegistro.setText(campos.get(position).getRegistro().trim());
			viewHolder.tvLatitude.setText(campos.get(position).getlatitude().trim());
			
			final int temp = position;
			return convertView;
		}
		
		private class ViewHolder {
			TextView tvRegistro;
			TextView tvLatitude;
		}
		
	}

}
