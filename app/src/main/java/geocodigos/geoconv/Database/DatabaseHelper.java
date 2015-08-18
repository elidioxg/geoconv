    package geocodigos.geoconv.Database;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import java.util.ArrayList;

    import geocodigos.geoconv.model.PointModel;

    public class DatabaseHelper extends SQLiteOpenHelper {
        private ArrayList<PointModel> ponto = new ArrayList<PointModel>();
        public static String dbId="id";
        public static String dbName = "database5";
        public static String dbTable = "pontos";
        public static String dbRegister = "registro";
        public static String dbDescription ="descricao";
        public static String dbLatitude = "latitude";
        public static String dbLongitude = "longitude";
        public static String dbNorte = "norte";
        public static String dbLeste = "leste";
        public static String dbSetor = "setor";
        public static String dbAltitude="altitude";
        public static String dbPrecisao="precisao";
        public static String dbData="data";
        public static String dbHora="hora";
        public static String dbSel = "selecionado";

        Context c;

        public DatabaseHelper(Context context) {
            super(context, dbName, null, 33); //33?
            // TODO Auto-generated constructor stub
            c = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL("CREATE TABLE IF NOT EXISTS "+dbTable+
                    " ("+dbId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    dbRegister+" TEXT, "+dbDescription+" TEXT, "+
                    dbLatitude+" TEXT, "+dbLongitude+" TEXT, "+
                    dbNorte+ " TEXT, "+dbLeste+" TEXT, "+ dbSetor +
                    " TEXT, "+dbAltitude +
                    " TEXT, "+dbHora+" TEXT, "+dbData+" TEXT, "+
                    dbPrecisao+" TEXT, "+dbSel+" TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+dbTable+" ; ");
            onCreate(db);
        }

        public void addPoint (PointModel pointModel) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbId, pointModel.id);
            contentValues.put(dbRegister, pointModel.registro);
            contentValues.put(dbDescription, pointModel.descricao);
            contentValues.put(dbLatitude, pointModel.latitude);
            contentValues.put(dbLongitude, pointModel.longitude);
            contentValues.put(dbNorte, pointModel.norte);
            contentValues.put(dbLeste, pointModel.leste);
            contentValues.put(dbSetor, pointModel.setor);
            contentValues.put(dbAltitude, pointModel.altitude);

            contentValues.put(dbData, pointModel.data);
            contentValues.put(dbHora, pointModel.hora);

            contentValues.put(dbPrecisao, pointModel.precisao);
            contentValues.put(dbSel, pointModel.selecionado);
            Log.i("dbSel: ", pointModel.selecionado);

            db.insert(dbTable, null, contentValues);
            db.close();
        }

        public void updateSelecao(PointModel pointModel){
            Log.i("id: ", pointModel.id);
            Log.i("Selecionado: ", pointModel.selecionado);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbSel, pointModel.selecionado);
            db.update(dbTable, contentValues, "id="+pointModel.id, null);
            db.close();
        }

        public void updatePoint(PointModel pointModel) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(dbId, pointModel.id);
            contentValues.put(dbRegister, pointModel.registro);
            contentValues.put(dbDescription, pointModel.descricao);
            contentValues.put(dbLatitude, pointModel.latitude);
            contentValues.put(dbLongitude, pointModel.longitude);
            contentValues.put(dbNorte, pointModel.norte);
            contentValues.put(dbLeste, pointModel.leste);
            contentValues.put(dbSetor, pointModel.setor);
            contentValues.put(dbAltitude, pointModel.altitude);

            contentValues.put(dbData, pointModel.data);
            contentValues.put(dbHora, pointModel.hora);

            contentValues.put(dbSel, pointModel.selecionado);
            Log.i("Registro Atualizado:", pointModel.registro);
            //obs: pode ter mais um ponto com o mesmo nome de registro
            db.update(dbTable, contentValues, "registro="+pointModel.registro, null);
            db.close();

        }

        public void deleteAllFields() {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM "+dbTable+" ;");
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void removePonto(String id) {

            try {
                String[] args = {id};
                getWritableDatabase().delete(dbTable, dbId=" ? ", args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ArrayList<PointModel> pegarPontos() {

            ponto.clear();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+dbTable+";", null);

            if (cursor.getCount() != 0 ) {
                if (cursor.moveToFirst()) {
                    do {
                        PointModel pointModel = new PointModel();
                        pointModel.id = cursor.getString(cursor.getColumnIndex(dbId));
                        pointModel.registro = cursor.getString(cursor.getColumnIndex(dbRegister));
                        pointModel.descricao = cursor.getString(cursor.getColumnIndex(dbDescription));
                        pointModel.latitude = cursor.getString(cursor.getColumnIndex(dbLatitude));
                        pointModel.longitude = cursor.getString(cursor.getColumnIndex(dbLongitude));
                        pointModel.norte = cursor.getString(cursor.getColumnIndex(dbNorte));
                        pointModel.leste = cursor.getString(cursor.getColumnIndex(dbLeste));
                        pointModel.setor = cursor.getString(cursor.getColumnIndex(dbSetor));
                        pointModel.altitude  = cursor.getString(cursor.getColumnIndex(dbAltitude));
                        pointModel.hora = cursor.getString(cursor.getColumnIndex(dbHora));
                        pointModel.data = cursor.getString(cursor.getColumnIndex(dbData));
                        pointModel.selecionado=cursor.getString(cursor.getColumnIndex(dbSel));
                        Log.i("id", pointModel.id.toString());
                        ponto.add(pointModel);
                    } while (cursor.moveToNext());
                }

            }
            cursor.close();
            db.close();
            return ponto;
        }

        public ArrayList<PointModel> pegarPontos(String registro) {

            ponto.clear();
            SQLiteDatabase db = this.getWritableDatabase();     //substituido pelas constantes
            Cursor cursor = db.query(true, dbTable, new String[] { dbRegister,  dbDescription,
                            dbLatitude, dbLongitude, dbNorte, dbLeste, dbSetor,
                            dbAltitude, dbHora, dbData, dbSel},
                    "registro = ? ", new String[] {registro}, null, null, null, null, null);

            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        PointModel pointModel = new PointModel();
                        pointModel.id=cursor.getString(cursor.getColumnIndex(dbId));
                        pointModel.registro= cursor.getString(cursor.getColumnIndex(dbRegister));
                        pointModel.descricao= cursor.getString(cursor.getColumnIndex(dbDescription));
                        pointModel.latitude = cursor.getString(cursor.getColumnIndex(dbLatitude));
                        pointModel.longitude = cursor.getString(cursor.getColumnIndex(dbLongitude));
                        pointModel.norte = cursor.getString(cursor.getColumnIndex(dbNorte));
                        pointModel.leste = cursor.getString(cursor.getColumnIndex(dbLeste));
                        pointModel.setor = cursor.getString(cursor.getColumnIndex(dbSetor));
                        pointModel.altitude  = cursor.getString(cursor.getColumnIndex(dbAltitude));
                        pointModel.data = cursor.getString(cursor.getColumnIndex(dbData));
                        pointModel.hora = cursor.getString(cursor.getColumnIndex(dbHora));
                        pointModel.selecionado = cursor.getString(cursor.getColumnIndex(dbSel));

                        ponto.add(pointModel);
                    } while (cursor.moveToNext());
                }
            }

            cursor.close();
            db.close();
            return ponto;
        }

        public boolean pegarId(String strId) {
            //verifica se já existe o id, ja que id é unique
            boolean existe = false;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(true, dbTable, new String[] { dbId },
                    " id = ? ", new String[] {strId}, null, null, null, null, null);
            if(cursor.getCount() > 0 ) {
                existe = true;
            }
            return existe;
        }

    }