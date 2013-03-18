package com.test.geo.optimal;

import com.test.geo.optimal.controller.Metadatos;
import com.test.geo.optimal.persistence.SQLiteManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Estadisticas extends Activity {

	private static TextView muestras_malas;
	private static TextView muestras_buenas;
	private static TextView muestras_excelentes;
	
	private static TextView porcentaje_malas;
	private static TextView porcentaje_buenas;
	private static TextView porcentaje_excelentes;
	
	private static TextView precision_malas;
	private static TextView precision_buenas;
	private static TextView precision_excelentes;
	
	private static TextView imprecision_promedios;
	
	private static TextView porcentaje_precision_malas;
	private static TextView porcentaje_precision_buenas;
	private static TextView porcentaje_precision_excelentes;
	
	private static TextView satelites_muestras_malas;
	private static TextView satelites_muestras_buenas;
	private static TextView satelites_muestras_excelentes;
	
	private static TextView total_satelites;
	
	private static TextView porcentaje_satelites_malas;
	private static TextView porcentaje_satelites_buenas;
	private static TextView porcentaje_satelites_excelentes;
	
	private static TextView proveedor_gps_malas;
	private static TextView proveedor_gps_buenas;
	private static TextView proveedor_gps_excelentes;
	
	private static TextView proveedor_network_malas;
	private static TextView proveedor_network_buenas;
	private static TextView proveedor_network_excelentes;
	
	private static TextView uso_gps;
	private static TextView uso_network;
	
	private static TextView porcentaje_gps_malas;
	private static TextView porcentaje_gps_buenas;
	private static TextView porcentaje_gps_excelentes;
	
	private static TextView porcentaje_network_malas;
	private static TextView porcentaje_network_buenas;
	private static TextView porcentaje_network_excelentes;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadisticas);
		
		SQLiteManager.getInstance().getAll(this);
		
		muestras_malas = (TextView) findViewById(R.id.muestras_malas);
		muestras_malas.setText(getString(R.string.cantidad_muestras_malas) +": "+Metadatos.getInstance().getCount_malas());
		
		muestras_buenas = (TextView) findViewById(R.id.muestras_buenas);
		muestras_buenas.setText(getString(R.string.cantidad_muestras_buenas) +": "+Metadatos.getInstance().getCount_buenas());
		
		muestras_excelentes= (TextView) findViewById(R.id.muestras_excelentes);
		muestras_excelentes.setText(getString(R.string.cantidad_muestras_excelentes) +": "+Metadatos.getInstance().getCount_excelentes());
		
		porcentaje_malas= (TextView) findViewById(R.id.porcentaje_malas);
		porcentaje_malas.setText(getString(R.string.porcentaje_muestras_malas) +": "+Metadatos.getInstance().getMalas());
		
		porcentaje_buenas= (TextView) findViewById(R.id.porcentaje_buenas);
		porcentaje_buenas.setText(getString(R.string.porcentaje_muestras_buenas) +": "+Metadatos.getInstance().getBuenas());
		
		porcentaje_excelentes= (TextView) findViewById(R.id.porcentaje_excelentes);
		porcentaje_excelentes.setText(getString(R.string.porcentaje_muestras_excelentes) +": "+Metadatos.getInstance().getExcelentes());
		
		precision_malas= (TextView) findViewById(R.id.precision_malas);
		precision_malas.setText(getString(R.string.precision_muestras_malas) +": "+Metadatos.getInstance().getCount_error_malas());
		
		precision_buenas= (TextView) findViewById(R.id.precision_buenas);
		precision_buenas.setText(getString(R.string.precision_muestras_buenas) +": "+Metadatos.getInstance().getCount_error_buenas());
		
		precision_excelentes= (TextView) findViewById(R.id.precision_excelentes);
		precision_excelentes.setText(getString(R.string.precision_muestras_excelentes) +": "+Metadatos.getInstance().getCount_error_excelentes());
		
		imprecision_promedios= (TextView) findViewById(R.id.imprecision_promedios);
		imprecision_promedios.setText(getString(R.string.total_imprecision_promedios) +": "+Metadatos.getInstance().getTotal_error());
		
		porcentaje_precision_malas= (TextView) findViewById(R.id.porcentaje_precision_malas);
		porcentaje_precision_malas.setText(getString(R.string.porcentaje_precision_malas) +": "+Metadatos.getInstance().getProm_error_malas()) ;
		
		porcentaje_precision_buenas= (TextView) findViewById(R.id.porcentaje_precision_buenas);
		porcentaje_precision_buenas.setText(getString(R.string.porcentaje_precision_buenas) +": "+Metadatos.getInstance().getProm_error_buenas()) ;
		
		porcentaje_precision_excelentes= (TextView) findViewById(R.id.porcentaje_precision_excelentes);
		porcentaje_precision_excelentes.setText(getString(R.string.porcentaje_precision_excelente) +": "+Metadatos.getInstance().getProm_error_excelentes()) ;
		
		satelites_muestras_malas= (TextView) findViewById(R.id.satelites_muestras_malas);
		satelites_muestras_malas.setText(getString(R.string.cantidad_satelites_muestras_malas) +": "+Metadatos.getInstance().getCount_malas_satelites()) ;
		
		satelites_muestras_buenas= (TextView) findViewById(R.id.satelites_muestras_buenas);
		satelites_muestras_buenas.setText(getString(R.string.cantidad_satelites_muestras_buenas) +": "+Metadatos.getInstance().getCount_buenas_satelites()) ;
		
		satelites_muestras_excelentes= (TextView) findViewById(R.id.satelites_muestras_excelentes);
		satelites_muestras_excelentes.setText(getString(R.string.cantidad_satelites_muestras_excelentes) +": "+Metadatos.getInstance().getCount_excelentes_satelites()) ;
		

		total_satelites= (TextView) findViewById(R.id.total_satelites);
		total_satelites.setText(getString(R.string.total_satelites_muestas) +": "+Metadatos.getInstance().getTotal_satelites()) ;
		
		porcentaje_satelites_malas= (TextView) findViewById(R.id.porcentaje_satelites_malas);
		porcentaje_satelites_malas.setText(getString(R.string.porcentaje_satelites_muestras_malas) +": "+Metadatos.getInstance().getProm_malas_satelites()) ;
		
		porcentaje_satelites_buenas= (TextView) findViewById(R.id.porcentaje_satelites_buenas);
		porcentaje_satelites_buenas.setText(getString(R.string.porcentaje_satelites_muestras_buenas) +": "+Metadatos.getInstance().getProm_buenas_satelites()) ;
		
		porcentaje_satelites_excelentes= (TextView) findViewById(R.id.porcentaje_satelites_excelentes);
		porcentaje_satelites_excelentes.setText(getString(R.string.porcentaje_satelites_muestras_excelentes) +": "+Metadatos.getInstance().getProm_excelentes_satelites()) ;
		
		proveedor_gps_malas= (TextView) findViewById(R.id.proveedor_gps_malas);
		proveedor_gps_malas.setText(getString(R.string.proveedor_gps_muestas_malas) +": "+Metadatos.getInstance().getCount_malas_gps()) ;
		
		proveedor_gps_buenas= (TextView) findViewById(R.id.proveedor_gps_buenas);
		proveedor_gps_buenas.setText(getString(R.string.proveedor_gps_muestras_buenas) +": "+Metadatos.getInstance().getCount_buenas_gps()) ;
		
		proveedor_gps_excelentes= (TextView) findViewById(R.id.proveedor_gps_excelentes);
		proveedor_gps_excelentes.setText(getString(R.string.proveedor_gps_muestras_excelentes) +": "+Metadatos.getInstance().getCount_excelentes_gps()) ;
		
		proveedor_network_malas= (TextView) findViewById(R.id.proveedor_network_malas);
		proveedor_network_malas.setText(getString(R.string.proveedor_newtork_muestras_malas)+": "+Metadatos.getInstance().getCount_malas_net()) ;
		
		proveedor_network_buenas= (TextView) findViewById(R.id.proveedor_network_buenas);
		proveedor_network_buenas.setText(getString(R.string.proveedor_network_muestras_buenas) +": "+Metadatos.getInstance().getCount_buenas_net()) ;
		
		proveedor_network_excelentes= (TextView) findViewById(R.id.proveedor_network_excelentes);
		proveedor_network_excelentes.setText(getString(R.string.proveedor_network_muestras_excelentes) +": "+Metadatos.getInstance().getCount_excelentes_net()) ;
		
		uso_gps= (TextView) findViewById(R.id.uso_gps);
		uso_gps.setText(getString(R.string.cantidad_gps) +": "+Metadatos.getInstance().getCount_total_veces_gps()) ;
		
		uso_network= (TextView) findViewById(R.id.uso_network);
		uso_network.setText(getString(R.string.cantidad_network) +": "+Metadatos.getInstance().getCount_total_veces_net()) ;
		
		porcentaje_gps_malas= (TextView) findViewById(R.id.porcentaje_gps_malas);
		porcentaje_gps_malas.setText(getString(R.string.porcentaje_gps_muestras_malas) +": "+Metadatos.getInstance().getProm_malas_gps()) ;
		
		porcentaje_gps_buenas= (TextView) findViewById(R.id.porcentaje_gps_buenas);
		porcentaje_gps_buenas.setText(getString(R.string.porcentaje_gps_muestras_buenas) +": "+Metadatos.getInstance().getProm_buenas_gps()) ;
		
		porcentaje_gps_excelentes= (TextView) findViewById(R.id.porcentaje_gps_excelentes);
		porcentaje_gps_excelentes.setText(getString(R.string.porcentaje_gps_muestras_excelentes) +": "+Metadatos.getInstance().getProm_excelentes_gps()) ;
		
		porcentaje_network_malas= (TextView) findViewById(R.id.porcentaje_network_malas);
		porcentaje_network_malas.setText(getString(R.string.porcentaje_network_muestras_malas) +": "+Metadatos.getInstance().getProm_malas_net()) ;
		
		porcentaje_network_buenas= (TextView) findViewById(R.id.porcentaje_network_buenas);
		porcentaje_network_buenas.setText(getString(R.string.porcentaje_network_muestras_buenas) +": "+Metadatos.getInstance().getProm_buenas_net()) ;
		
		porcentaje_network_excelentes= (TextView) findViewById(R.id.porcentaje_network_excelentes);
		porcentaje_network_excelentes.setText(getString(R.string.porcentaje_network_muestras_excelentes) +": "+Metadatos.getInstance().getProm_excelentes_net()) ;
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estadisticas, menu);
		return true;
	}

}
