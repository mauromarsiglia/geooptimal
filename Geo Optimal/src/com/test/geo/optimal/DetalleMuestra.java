package com.test.geo.optimal;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleMuestra extends Activity {

	private static final String TAG="DETALLEMUESTA";
	private static TextView longitud;
	private static TextView latitud;
	private static TextView calificacion;
	private static TextView error;
	private static TextView satelites;
	private static TextView proveedor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muestra);
		Bundle bundle = getIntent().getExtras();
		
		longitud = (TextView) findViewById(R.id.longitud_muestra);
		latitud= (TextView) findViewById(R.id.latitud_muestra);
		calificacion= (TextView) findViewById(R.id.calificacion_muestra);
		error= (TextView) findViewById(R.id.error_muestra);
		satelites= (TextView) findViewById(R.id.satelites_muestra);
		proveedor= (TextView) findViewById(R.id.proveedor_muestra);
		
		longitud.setText("Longitud: "+bundle.getString("longitud"));
		latitud.setText("Latitud: "+bundle.getString("latitud"));
		calificacion.setText("Calificacion: "+bundle.getString("calificacion"));
		error.setText("Precision: "+bundle.getString("precision"));
		satelites.setText("# de satelites: "+bundle.getString("satelites"));
		proveedor.setText("Proveedor : "+bundle.getString("proveedor"));
		Log.i(TAG,bundle.getString("imagen"));
		abrirImagen(bundle.getString("imagen"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muestra, menu);
		return true;
	}
	
	private void abrirImagen(String nombre){

       
        //Bitmap b = imagen;
		if(!nombre.equals("")){
	       // File sd = Environment.getExternalStorageDirectory();
	        String the_path = Environment.getExternalStorageDirectory()
	        			+ File.separator + "prohibidoparquear";
	
	      	String the_file = the_path + File.separator +  nombre +".jpg";
	
	        File f = new File(the_file);
	          
	        if(f.exists()){
	
	            Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
	
	            ImageView myImage = (ImageView) findViewById(R.id.imagen_muestra);
	            myImage.setImageBitmap(myBitmap);
	
	        }
		}
          
	}

}
