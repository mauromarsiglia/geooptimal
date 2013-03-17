/**
 * 
 */
package com.test.geo.optimal.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @author LUSTER
 *
 */
public class TableManager extends SQLiteOpenHelper {
	
	public TableManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	private String sqlCreate = "CREATE TABLE muestra (lat TEXT, long TEXT, calificacion INTEGER, " +
			"descripcion TEXT, path_imagen TEXT);";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		db.execSQL("DROP TABLE IF EXISTS muestra");
		db.execSQL(sqlCreate);
	}

}