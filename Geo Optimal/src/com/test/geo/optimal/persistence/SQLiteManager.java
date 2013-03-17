/**
 * 
 */
package com.test.geo.optimal.persistence;

import java.util.List;

import com.test.geo.optimal.library.Muestra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author LUSTER
 *
 */
public class SQLiteManager {
	
	private TableManager manager;
	private SQLiteDatabase db;
	
	
	/**
	 * Singleton
	 */
	private SQLiteManager instance;
	
	public SQLiteManager getInstance(){
		if(instance==null){
			instance = new SQLiteManager();
		}
		return instance;
	}
	
	private SQLiteManager(){
		 
	}
	
	/**
	 * 
	 * @param muestra
	 * @param context
	 */
	public void saveMuestra(Muestra muestra, Context context){
		
		this.manager = new TableManager(context, "muestreo", null, 1);
		this.db = this.manager.getWritableDatabase();
        this.db.execSQL("INSERT INTO muestra (lat, long, calificacion, " +
			"descripcion, path_imagen) VALUES('"+muestra.getLatitude()+"', " +
					"'"+muestra.getLonguitude()+"', '"+muestra.getDescripcion()+"', '"+muestra.getPathImagen()+"');");
        this.db.close();
        
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<Muestra> getAll(Context context){
		
		this.manager = new TableManager(context, "muestreo", null, 1);
		this.db = this.manager.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM muestra;",null);
		List<Muestra> muestras = new java.util.LinkedList<Muestra>();
		
		if(c.moveToFirst()){
			do{
				Muestra db_item = new Muestra(c.getString(0), c.getString(1), 
						Integer.parseInt(c.getString(2)), c.getString(3), c.getString(4));
				muestras.add(db_item);
			}while(c.moveToNext());
		}
		
		this.db.close();
		return muestras;
		
	}

}
