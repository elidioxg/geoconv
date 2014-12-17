package geoapp.coordinateconversor.database;

import geoapp.coordinateconversor.model.PointModel;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private ArrayList<PointModel> ponto = new ArrayList<PointModel>();
	
	public static String dbId="id";
	public static String dbName = "database3";
	public static String dbTable = "pontos";
	public static String dbRegister = "registro";
	public static String dbDescription ="descricao";
	public static String dbLatitude = "latitude";
	public static String dbLongitude = "longitude";
	public static String dbNorte = "norte";
	public static String dbLeste = "leste";
	public static String dbSetorN = "setorn";
	public static String dbSetorL="setorl";
	public static String dbAltitude="altitude";
	
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
				dbNorte+ " TEXT, "+dbLeste+" TEXT, "+dbSetorN+
				" TEXT, "+dbSetorL+" TEXT, "+dbAltitude +
				" TEXT);");		
		Log.i("SQL: ", "CREATE TABLE IF NOT EXISTS "+dbTable+
				" ("+dbId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				dbRegister+" TEXT, "+dbDescription+" TEXT, "+
				dbLatitude+" TEXT, "+dbLongitude+" TEXT, "+
				dbNorte+ " TEXT, "+dbLeste+" TEXT, "+dbSetorN+
				" TEXT, "+dbSetorL+" TEXT, "+dbAltitude +
				" TEXT);");
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
		//estou substituindo pelas constantes abaixo dbDescription = "descricao"
		contentValues.put(dbId, pointModel.id);
		contentValues.put(dbRegister, pointModel.registro);
		contentValues.put(dbDescription, pointModel.descricao);
		contentValues.put(dbLatitude, pointModel.latitude);
		contentValues.put(dbLongitude, pointModel.longitude);
		contentValues.put(dbNorte, pointModel.norte);
		contentValues.put(dbLeste, pointModel.leste);
		contentValues.put(dbSetorN, pointModel.setorN);
		contentValues.put(dbSetorL, pointModel.setorL);
		contentValues.put(dbAltitude, pointModel.altitude);
		
		db.insert(dbTable, null, contentValues);
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
		contentValues.put(dbSetorN, pointModel.setorN);
		contentValues.put(dbSetorL, pointModel.setorL);
		contentValues.put(dbAltitude, pointModel.altitude);
		
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
		
	public void removePonto(String registro) {
		
		try {
			String[] args = {registro};
			getWritableDatabase().delete(dbTable, dbRegister=" ? ", args);
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
					pointModel.setorN = cursor.getString(cursor.getColumnIndex(dbSetorN));
					pointModel.setorL = cursor.getString(cursor.getColumnIndex(dbSetorL));
					pointModel.altitude  = cursor.getString(cursor.getColumnIndex(dbAltitude));
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
				dbLatitude, dbLongitude, dbNorte, dbLeste, dbSetorN, dbSetorL,
				dbAltitude},
				"registro = ?", new String[] {registro}, null, null, null, null, null);
		
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
					pointModel.setorN = cursor.getString(cursor.getColumnIndex(dbSetorN));
					pointModel.setorL = cursor.getString(cursor.getColumnIndex(dbSetorL));
					pointModel.altitude  = cursor.getString(cursor.getColumnIndex(dbAltitude));
					
					ponto.add(pointModel);
				} while (cursor.moveToNext());
			}
		}
		
		cursor.close();
		db.close();
		return ponto;
	}
	//funcao duplicada? remover:
	public ArrayList<PointModel> pegarPontos1(String registro) {
		
		ponto.clear();
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.query(true, dbTable, new String[] {dbRegister,  dbDescription},
				dbRegister+"= ?", new String[] {registro}, null, null, null, null);
		
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					PointModel pointModel = new PointModel();
					pointModel.id=cursor.getString(cursor.getColumnIndex(dbId));
					pointModel.registro = cursor.getString(cursor.getColumnIndex(dbRegister));
					pointModel.descricao = cursor.getString(cursor.getColumnIndex(dbDescription));
					pointModel.latitude = cursor.getString(cursor.getColumnIndex(dbLatitude));
					pointModel.longitude = cursor.getString(cursor.getColumnIndex(dbLongitude));
					pointModel.norte = cursor.getString(cursor.getColumnIndex(dbNorte));
					pointModel.leste = cursor.getString(cursor.getColumnIndex(dbLeste));
					pointModel.setorN = cursor.getString(cursor.getColumnIndex(dbSetorN));
					pointModel.setorL = cursor.getString(cursor.getColumnIndex(dbSetorL));
					pointModel.altitude  = cursor.getString(cursor.getColumnIndex(dbAltitude));
					
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return ponto;
	}
}
