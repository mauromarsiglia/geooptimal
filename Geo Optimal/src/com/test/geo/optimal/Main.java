package com.test.geo.optimal;


import java.security.Provider;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



public class Main extends MapActivity{

	private MapView mapa = null;
	private static double longitud_actual;
	private static double latitud_actual;
	private static double precision_actual=999999999;
	
	private static String TAG="MAIN";
	private static TextView longitud;
	private static TextView latitud;
	private static TextView precision;
	private static TextView satelites;
	private LocationManager locManager;
	private LocationListener locListener;
	MyOverlay itemizedOverlay;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
        mapa = (MapView)findViewById(R.id.mapa);

        mapa.setBuiltInZoomControls(true);
        
        longitud = (TextView) findViewById(R.id.longitud);
        latitud = (TextView) findViewById(R.id.latitud);
        precision = (TextView) findViewById(R.id.precision);
        this.satelites = (TextView) findViewById(R.id.satelites);
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        itemizedOverlay = new MyOverlay(drawable, this);
        localizar();
	}
	
	private void localizar()
    {

    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	
    	Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
    	
    	mostrarPosicion(loc);
    	
    	
    	locListener = new LocationListener() {
    		
	    	public void onLocationChanged(Location location) {
	    		mostrarPosicion(location);
	    	}
	    	public void onProviderDisabled(String provider){
	    		
	    	}
	    	public void onProviderEnabled(String provider){
	    		
	    	}
	    	public void onStatusChanged(String provider, int status, Bundle extras){
	    		Log.i("", "Provider Status: " + status);
	    	}
    	};
    	
    	locManager.requestLocationUpdates(
    			LocationManager.GPS_PROVIDER, 10000, 0, locListener);
    	
    }
     
    private void mostrarPosicion(Location loc) {
    	
    	if(loc != null)
    	{
	    		longitud_actual= loc.getLongitude();
	    		latitud_actual=loc.getLatitude();
	    		precision_actual=loc.getAccuracy();
	    		mostrarPosicion();
	    		
	    		latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
	    		longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
	    		precision.setText("Precision: " + String.valueOf(loc.getAccuracy()) +" m");
	    		Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
    	}else
    	{
    		latitud.setText("Latitud: (sin_datos)");
    		longitud.setText("Longitud: (sin_datos)");
    		
    	}
    }
    
    private void mostrarPosicion(){
    	
    	List<Overlay> mapOverlays = mapa.getOverlays();
    	mapOverlays.clear();
    	
 		GeoPoint point = new GeoPoint((int)(latitud_actual* 1E6), (int)(longitud_actual* 1E6));
 		
 		OverlayItem overlayitem = new OverlayItem(point, "Hola",
 				"!Precision!" + precision.getText());
  
 		itemizedOverlay.addOverlay(overlayitem);
 		mapOverlays.add(itemizedOverlay);
 		
 		
 		MapController mapController = mapa.getController();
 		
 		mapController.animateTo(point);
 		mapController.setZoom(20);
 		mapa.invalidate();
 		
 		GpsStatus gpsstatus = this.locManager.getGpsStatus(null);
    	Iterable<GpsSatellite> satelitess = gpsstatus.getSatellites();
    	
    	int nSatelites = 0;
    	
    	for(GpsSatellite gpssatelites : satelitess){
    		nSatelites++;
    	}
    	
    	Log.i(TAG, "nSatelites: "+nSatelites);
    	this.satelites.setText(""+nSatelites);
 		
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
