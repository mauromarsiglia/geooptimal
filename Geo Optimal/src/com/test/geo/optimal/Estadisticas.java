package com.test.geo.optimal;

import com.test.geo.optimal.controller.Metadatos;
import com.test.geo.optimal.persistence.SQLiteManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Estadisticas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas);
		
		SQLiteManager.getInstance().getAll(this);
		
		TableLayout tableLayout = (TableLayout) findViewById(R.id.table_estadistica);
		tableLayout.setStretchAllColumns(true);
		tableLayout.setShrinkAllColumns(true);
		
		//header
		TableRow newRow = new TableRow(this);
		TextView text = new TextView(this);
		text.setText(getString(R.string.desc_column));
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		newRow.addView(text);
		newRow.addView(getTextView( R.string.value_column ));
		tableLayout.addView(newRow);
		
		//Cantidad de muestra segun calificacion
		tableLayout.addView(createRow(R.string.cantidad_muestras_malas, Metadatos.getInstance().getCount_malas()+""));
		tableLayout.addView(createRow(R.string.cantidad_muestras_buenas, Metadatos.getInstance().getCount_buenas()+""));
		tableLayout.addView(createRow(R.string.cantidad_muestras_excelentes, Metadatos.getInstance().getCount_excelentes()+""));
		
		//Promedio de muestras por categoria
		tableLayout.addView(createRow(R.string.porcentaje_muestras_malas, Metadatos.getInstance().getMalas()+"%"));
		tableLayout.addView(createRow(R.string.porcentaje_muestras_buenas, Metadatos.getInstance().getBuenas()+"%"));
		tableLayout.addView(createRow(R.string.porcentaje_muestras_excelentes, Metadatos.getInstance().getExcelentes()+"%"));
		
		
		//Total precisión de las muestras segun la clasificacion
		tableLayout.addView(createRow(R.string.precision_muestras_malas, Metadatos.getInstance().getCount_error_malas()+""));
		tableLayout.addView(createRow(R.string.precision_muestras_buenas, Metadatos.getInstance().getCount_error_buenas()+""));
		tableLayout.addView(createRow(R.string.precision_muestras_excelentes, Metadatos.getInstance().getCount_error_excelentes()+""));
		
		//Total Acumulado de error
		tableLayout.addView(createRow(R.string.total_imprecision_promedios, Metadatos.getInstance().getTotal_error()+""));
		
		//Promedio de precisión segun la calificacion
		tableLayout.addView(createRow(R.string.porcentaje_precision_malas, Metadatos.getInstance().getProm_error_malas()+""));
		tableLayout.addView(createRow(R.string.porcentaje_precision_buenas, Metadatos.getInstance().getProm_error_buenas()+""));
		tableLayout.addView(createRow(R.string.porcentaje_precision_excelente, Metadatos.getInstance().getProm_error_excelentes()+""));
		
		//Promedio de satelites usados
		tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_malas, Metadatos.getInstance().getProm_malas_satelites()+""));
		tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_buenas, Metadatos.getInstance().getProm_buenas_satelites()+""));
		tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_satelites()+""));
		
		//total de veces que se uso cada proveedor
		tableLayout.addView(createRow(R.string.cantidad_gps, Metadatos.getInstance().getCount_total_veces_gps()+""));
		tableLayout.addView(createRow(R.string.cantidad_network, Metadatos.getInstance().getCount_total_veces_net()+""));
		
		//Resumen uso de gps segun calificacion
		//gps
		tableLayout.addView(createRow(R.string.proveedor_gps_muestas_malas, Metadatos.getInstance().getCount_malas_gps()+""));
		tableLayout.addView(createRow(R.string.proveedor_gps_muestras_buenas, Metadatos.getInstance().getCount_buenas_gps()+""));
		tableLayout.addView(createRow(R.string.proveedor_gps_muestras_excelentes, Metadatos.getInstance().getCount_excelentes_gps()+""));
		
		//network
		tableLayout.addView(createRow(R.string.proveedor_newtork_muestras_malas, Metadatos.getInstance().getCount_malas_net()+""));
		tableLayout.addView(createRow(R.string.proveedor_network_muestras_buenas, Metadatos.getInstance().getCount_buenas_net()+""));
		tableLayout.addView(createRow(R.string.proveedor_network_muestras_excelentes, Metadatos.getInstance().getCount_excelentes_net()+""));
		
		//Promedio de uso de proveedor por calificacion
		//gps
		tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_malas, Metadatos.getInstance().getProm_malas_gps()+""));
		tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_buenas, Metadatos.getInstance().getProm_buenas_gps()+""));
		tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_gps()+""));
		
		//network
		tableLayout.addView(createRow(R.string.porcentaje_network_muestras_malas, Metadatos.getInstance().getProm_malas_net()+""));
		tableLayout.addView(createRow(R.string.porcentaje_network_muestras_buenas, Metadatos.getInstance().getProm_buenas_net()+""));
		tableLayout.addView(createRow(R.string.porcentaje_network_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_net()+""));
		
/*
		
		satelites_muestras_malas= (TextView) findViewById(R.id.satelites_muestras_malas);
		satelites_muestras_malas.setText(getString(R.string.cantidad_satelites_muestras_malas) +": "+Metadatos.getInstance().getCount_malas_satelites()) ;
		
		satelites_muestras_buenas= (TextView) findViewById(R.id.satelites_muestras_buenas);
		satelites_muestras_buenas.setText(getString(R.string.cantidad_satelites_muestras_buenas) +": "+Metadatos.getInstance().getCount_buenas_satelites()) ;
		
		satelites_muestras_excelentes= (TextView) findViewById(R.id.satelites_muestras_excelentes);
		satelites_muestras_excelentes.setText(getString(R.string.cantidad_satelites_muestras_excelentes) +": "+Metadatos.getInstance().getCount_excelentes_satelites()) ;
		

		total_satelites= (TextView) findViewById(R.id.total_satelites);
		total_satelites.setText(getString(R.string.total_satelites_muestas) +": "+Metadatos.getInstance().getTotal_satelites()) ;*/
		
		
	}
	
	private TextView getTextView(int string_id){
		
		TextView text = new TextView(this);
		text.setText(getString(string_id));
		text.setGravity(Gravity.LEFT);
		
		return text;
		
	}
	
	private TextView getTextView(String content){
		
		TextView text = new TextView(this);
		text.setText(content);
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		
		return text;
		
	}
	
	private TableRow createRow(int string_id, String content){
		
		TableRow newRow = new TableRow(this);
		newRow.addView(getTextView(string_id));
		newRow.addView(getTextView( content ));
		
		return newRow;
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estadisticas, menu);
		return true;
	}

}
