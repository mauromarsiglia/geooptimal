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
	private double malas;
	private double buenas;
	private double excelentes;
	
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
		this.listaMuestras.clear();
		
		if(c.moveToFirst()){
			do{
				Muestra db_item = new Muestra(c.getString(0), c.getString(1), c.getString(2), c.getString(3),
						c.getInt(4), c.getString(5), c.getString(6));
				this.listaMuestras.add(db_item);
			}while(c.moveToNext());
		}
		
		this.db.close();
		this.estadistica();
		
		return this.listaMuestras;
		
	}
	
	public void estadistica(){
		
		int malas = 0;
		int buenas = 0;
		int excelentes = 0;
		
		
		for(Muestra item : this.listaMuestras){
			if(item.getCalificacion() == Muestra.MALA){
				malas++;
			}else if(item.getCalificacion() == Muestra.BUENA){
				buenas++;
			}else{
				excelentes++;
			}
		}
		
	}
}
