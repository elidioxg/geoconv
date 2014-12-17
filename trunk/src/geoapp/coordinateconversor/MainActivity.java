package geoapp.coordinateconversor;

import geoapp.coordinateconversor.database.DatabaseHelper;
import geoapp.coordinateconversor.implementation.tabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	ActionBar.Tab tabConverter, tabMarcar, tabPontos;
	Fragment fragmentConverter = new ConverterCoordenadas();
	Fragment fragmentMarcar = new MarcarPonto();
	Fragment fragmentPontos = new VerPontos();
	
	DatabaseHelper database;
	
    	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        retirado as tres linhas abaixo, para nao deixar app lento
//        database = new DatabaseHelper(getApplicationContext());
//        database.getWritableDatabase();
//        database.close();

      //if hassystemfeature
        //metodo1:
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        provider  = locationManager.getBestProvider(criteria, false);
//        Location location = locationManager.getLastKnownLocation(provider);
//        
//        if (location != null) {
//        	System.out.println("Provider: "+provider+ "foi selecionado.");
//        	onLocationChanged(location);
//        } else {
//        	//dizer nos textview que esta desativado
//        	//mostrar dialogo se quer ativar
//        	//se for ativar:
//        	
//        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        	startActivity(intent);
//        	
//        }
//        
//        
        //metodo2:
//        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        
//        if (enabled) {
//        	LocationManager locationManager = (LocationManager) 
//            		getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//            		0, 0, this);        	
//        } else {
//        	//mostrar dialogo perguntando se quer ativar
//        	//se sim, fazer isso:
//        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        	startActivity(intent);
//        }
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        tabConverter = actionBar.newTab().setText(R.string.converter_coord);
        tabMarcar = actionBar.newTab().setText(R.string.marcar_ponto);
        tabPontos = actionBar.newTab().setText(R.string.ver_pontos);
        
        tabConverter.setTabListener(new tabListener(fragmentConverter));
        tabMarcar.setTabListener(new tabListener(fragmentMarcar));
        tabPontos.setTabListener(new tabListener(fragmentPontos));
        
        actionBar.addTab(tabConverter);
        actionBar.addTab(tabMarcar);
        actionBar.addTab(tabPontos);
        
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub    	
		super.onResume();
//		locationManager.requestLocationUpdates(provider, 400, 1, this);
		//ver se o gps esta ativado e perguntar se esta ativado
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		locationManager.removeUpdates(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//        	
//        case R.id.item_converter:
//        	Intent intentConverter = new Intent(this, ConverterCoordenadas.class);
//        	startActivity(intentConverter);
//        	break;
//        
//        case R.id.item_marcar:
//        	Intent intentMarcar = new Intent(this, MarcarPonto.class);
//        	startActivity(intentMarcar);
//        	break;
//        	
//        case R.id.item_pontos:
//        	Intent intentPontos = new Intent(this, VerPontos.class);
//        	startActivity(intentPontos);
//        	break;
//        	
//        default:
//        	break;
//        }
    	
        return super.onOptionsItemSelected(item);
    }
}
