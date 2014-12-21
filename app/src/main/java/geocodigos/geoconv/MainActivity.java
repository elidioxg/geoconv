package geocodigos.geoconv;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.implementation.tabListener;


public class MainActivity extends Activity {

    ActionBar.Tab tabConverter, tabMarcar, tabPontos;
    Fragment fragConverter = new ConverterCoordenadas();
    Fragment fragMarcar = new MarcarPontos();
    Fragment fragPontos = new VerPontos();

    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new PlaceholderFragment())
                    .commit();
        }

        ActionBar actionbar = getActionBar();actionbar.setNavigationMode(
                ActionBar.NAVIGATION_MODE_TABS);
        tabConverter = actionbar.newTab().setText(R.string.converter_coord);
        tabMarcar = actionbar.newTab().setText(R.string.marcar_ponto);
        tabPontos = actionbar.newTab().setText(R.string.ver_pontos);

        tabConverter.setTabListener(new tabListener(fragConverter));
        tabMarcar.setTabListener(new tabListener(fragMarcar));
        tabConverter.setTabListener(new tabListener(fragConverter));

        actionbar.addTab(tabConverter);
        actionbar.addTab(tabMarcar);
        actionbar.addTab(tabPontos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }
    }
}
