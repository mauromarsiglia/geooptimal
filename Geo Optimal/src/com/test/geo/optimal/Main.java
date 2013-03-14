package com.test.geo.optimal;


import android.os.Bundle;
import android.view.Menu;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;


public class Main extends MapActivity{

	private MapView mapa = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

        mapa = (MapView)findViewById(R.id.mapa);
 

        mapa.setBuiltInZoomControls(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
