package geocodigos.geoconv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import java.util.Locale;
import geocodigos.geoconv.implementation.tabListener;

public class MainActivity extends FragmentActivity {

    public ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.fragment_container);
        viewPager.setAdapter(new tabListener(getSupportFragmentManager(), this));
        viewPager.setBackgroundColor(getResources().getColor(R.color.branco));
        getFragmentManager();
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

        final View viewLanguage = View.inflate(MainActivity.this,R.layout.choose_lang,null);
        final RadioButton rb1 = (RadioButton) viewLanguage.findViewById(R.id.rb_lang1);
        final RadioButton rb2 = (RadioButton) viewLanguage.findViewById(R.id.rb_lang2);
        final RadioButton rb3 = (RadioButton) viewLanguage.findViewById(R.id.rb_lang3);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        int id = item.getItemId();
        switch (id){
            case R.id.action_lang:
                alertDialog.setTitle(R.string.marcar_ponto);
                alertDialog.setView(viewLanguage);
                alertDialog.setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViewGroup parent = (ViewGroup) viewLanguage.getParent();
                                parent.removeView(viewLanguage);
                            }
                        });
                alertDialog.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Locale locale = new Locale("en");
                        if (rb1.isChecked()) {
                            locale = new Locale("br");
                        } else {
                            if (rb3.isChecked()) {
                                locale = new Locale("es");
                            } else {
                                locale = new Locale("en");
                            }
                        }
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        ViewGroup parent = (ViewGroup) viewLanguage.getParent();
                        parent.removeView(viewLanguage);
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.show();
                break;
            case R.id.action_doc:
                break;
            default:
                break;
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("view", 1);
    }

/*
    @Override
    public void onDestroy() {
        //viewPager.removeAllViews();
        super.onDestroy();
    }*/
}
