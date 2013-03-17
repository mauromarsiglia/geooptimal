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
	private List<Muestra> listaMuestras;
	
	/**
	 * Singleton
	 */
	private static SQLiteManager instance;
	
	public static SQLiteManager getInstance(){
		if(instance==null){
			instance = new SQLiteManager();
		}
		return instance;
	}
	
	private SQLiteManager(){
		 this.listaMuestras = new java.util.LinkedList<Muestra>();
	}
	
	/**
	 * 
	 * @param muestra
	 * @param context
	 */
	public void saveMuestra(Muestra muestra, Context context){
		
		this.manager = new TableManager(context, "muestreo", null, 2);
		this.db = this.manager.getWritableDatabase();
        this.db.execSQL("INSERT INTO muestra (latitude, longuitude, precision, providerName, numeroSatelites, " +
			"calificacion, descripcion, path_imagen) VALUES('"+muestra.getLatitude()+"', " +
					"'"+muestra.getLonguitude()+"', "+muestra.getPrecision()+", '"+muestra.getProviderName()+"', " +
					muestra.getNumeroSatelites()+", "+muestra.getCalificacion()+", '"+muestra.getDescripcion()+"', " +
					"'"+muestra.getPathImagen()+"');");
        this.db.close();
        
	
	
	}
	
	public void deleteDB(Context context){
		
		this.manager = new TableManager(context, "muestreo", null, 1);
		this.db = this.manager.getWritableDatabase();
        this.db.execSQL("DELETE FROM muestra;");
        this.db.close();
	
	}
	
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<Muestra> getAll(Context context){
		
		this.manager = new TableManager(context, "muestreo", null, 2);
		this.db = this.manager.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM muestra;",null);
		this.listaMuestras.clear();
		
		if(c.moveToFirst()){
			do{
				Muestra db_item = new Muestra(c.getString(0), c.getString(1), c.getInt(2), c.getString(3),
						c.getInt(4), c.getInt(5), c.getString(6), c.getString(7));
				this.listaMuestras.add(db_item);
			}while(c.moveToNext());
		}
		
		this.db.close();
		com.test.geo.optimal.controller.Metadatos.getInstance().saveEstadistica(this.listaMuestras);
		
		return this.listaMuestras;
		
	}
	
}
