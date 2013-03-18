package com.test.geo.optimal;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.test.geo.optimal.library.Muestra;
import com.test.geo.optimal.persistence.SQLiteManager;

public class Results extends Activity {

	private static final String TAG = "RESULTS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		llenarTabla();
	}
	
	private void llenarTabla(){
		
		TableLayout table = (TableLayout) findViewById(R.id.table);
		
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		List<Muestra> muestras = SQLiteManager.getInstance().getAll(this);
		Log.i("CANTIDAD", muestras.size() + "");

		for (final Muestra muestra : muestras) {

			TableRow newRow = new TableRow(this);

			newRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v.setBackgroundColor(Color.GRAY);
					
					Handler handler  = new Handler();
					handler.postDelayed(cambiarActivity(muestra,v), 1000);
					
				}
			});
			TextView day1High = new TextView(this);
			day1High.setText(muestra.getLatitude());
			day1High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day1High);

			TextView day2High = new TextView(this);
			day2High.setText(muestra.getLonguitude() + "");
			day2High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day2High);

			TextView day3High = new TextView(this);
			day3High.setText(muestra.getCalificacion() + "");
			day3High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day3High);

			TextView day4High = new TextView(this);
			day4High.setText(muestra.getPrecision() + "");
			day4High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day4High);

			TextView day5High = new TextView(this);
			day5High.setText(muestra.getNumeroSatelites() + "");
			day5High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day5High);

			TextView day6High = new TextView(this);
			day6High.setText(muestra.getProviderName());
			day6High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day6High);

			table.addView(newRow);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		
	}
	private Runnable cambiarActivity(final Muestra muestra, final View v) {
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				cambiarActivity2(muestra);
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		};
		return runnable;
	}
	private void cambiarActivity2(Muestra muestra){
		Log.i(TAG, muestra.getPrecision()+"");
		Intent intent = new Intent();
		intent.setClass(this, DetalleMuestra.class);
		intent.putExtra("latitud", muestra.getLatitude());
		intent.putExtra("longitud", muestra.getLonguitude());
		intent.putExtra("calificacion", muestra.getCalificacion());
		intent.putExtra("precision", muestra.getPrecision());
		intent.putExtra("satelites", muestra.getNumeroSatelites());
		intent.putExtra("proveedor", muestra.getProviderName());
		intent.putExtra("imagen", muestra.getPathImagen());
		startActivity(intent);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

}
