package geocodigos.geoconv.Registro;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.R;
import geocodigos.geoconv.model.PointModel;

public class VerRegistro extends Activity implements View.OnTouchListener{

    private DatabaseHelper database;
    private ArrayList<PointModel> arrayList = new ArrayList<PointModel>();
    private EditText etLatitude, etLongitude, etPrecisao, etAltitude, etSetor,
        etNorte, etLeste, etRegistro, etDescricao, etHora, etData;
    private ImageButton ibAnterior, ibProximo;

    private int Id, total;

    @Override
    public void onBackPressed() {
        Log.i("Botao Voltar "," Fechando Activity");
        finish();
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
        float x1=0,x2=0;
        int mov = touchEvent.getActionMasked();
        switch (mov){
            case MotionEvent.ACTION_DOWN: {
                x1 = touchEvent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = touchEvent.getX();
                if(x1<x2){
                    Log.i("ACTION_DOWN","direita");
                    //esq -> dir
                }
                if(x1>x2){
                    Log.i("ACTION_DOWN","esquerda");
                   // dir esq
                }
                break;
            }
        }
        return super.onTouchEvent(touchEvent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_registro);

        Bundle args = getIntent().getExtras();
        Id = (int) args.get("id");
        total = (int) args.get("total_registros");

        ibAnterior = (ImageButton) findViewById(R.id.ib_anterior);
        ibProximo = (ImageButton) findViewById(R.id.ib_proximo);

        ibAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id > 0) {
                    Id--;
                    preencheCampos(Id);
                }
            }
        });

        ibProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Id < (total-1)) {
                    Id++;
                    preencheCampos(Id);
                }
            }
        });

        etLatitude = (EditText) findViewById(R.id.etlatitude);
        etLongitude = (EditText)findViewById(R.id.etlongitude);
        etRegistro = (EditText) findViewById(R.id.etregistro);
        etDescricao = (EditText)findViewById(R.id.etdescricao);
        etPrecisao = (EditText) findViewById(R.id.etPrecisao);
        etAltitude = (EditText) findViewById(R.id.etAltitude);
        etHora = (EditText) findViewById(R.id.etHora);
        etData = (EditText) findViewById(R.id.etData);
        etSetor = (EditText)findViewById(R.id.etSetor);
        etNorte = (EditText)findViewById(R.id.etNorte);
        etLeste = (EditText)findViewById(R.id.etLeste);

        preencheCampos(Id);

    }

    public void preencheCampos(int numId) {
        database = new DatabaseHelper(this);
        database.getWritableDatabase();

        arrayList = database.pegarPontos();

        etLatitude.setText(arrayList.get(Integer.valueOf(numId)).getlatitude());
        etLongitude.setText(arrayList.get(Integer.valueOf(numId)).getLongitude());
        etRegistro.setText(arrayList.get(Integer.valueOf(numId)).getRegistro());
        etDescricao.setText(arrayList.get(Integer.valueOf(numId)).getDescricao());
        etData.setText(arrayList.get(Integer.valueOf(numId)).getData());
        etHora.setText(arrayList.get(Integer.valueOf(numId)).getHora());
        etPrecisao.setText(arrayList.get(Integer.valueOf(numId)).getPrecisao());
        etAltitude.setText(arrayList.get(Integer.valueOf(numId)).getAltitude());
        etSetor.setText(arrayList.get(Integer.valueOf(numId)).getSetor());
        etNorte.setText(arrayList.get(Integer.valueOf(numId)).getNorte());
        etLeste.setText(arrayList.get(Integer.valueOf(numId)).getLeste());

        database.close();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x1 = 0, x2 = 0;
        final int dist_min=15;
        int mov = event.getActionMasked();
        switch (mov){
            case MotionEvent.ACTION_DOWN: {
                x1 = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = event.getX();
                if(x1<x2){
                    if(Math.abs(x2-x1)>dist_min){

                    }
                    Log.i("ACTION_DOWN","direita");
                    //esq -> dir
                }
                if(x1>x2){
                    if(Math.abs(x1-x2)>dist_min){}
                    Log.i("ACTION_DOWN","esquerda");
                    // dir esq
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}

