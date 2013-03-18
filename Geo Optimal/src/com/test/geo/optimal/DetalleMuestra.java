package com.test.geo.optimal;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetalleMuestra extends Activity {

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
		calificacion.setText("Calificacion: "+bundle.getInt("calificacion"));
		error.setText("Precision: "+bundle.getInt("precision"));
		satelites.setText("# de satelites: "+bundle.getInt("satelites"));
		proveedor.setText("Proveedor : "+bundle.getString("proveedor"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muestra, menu);
		return true;
	}

}
