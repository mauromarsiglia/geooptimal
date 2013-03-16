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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	private Location currentBestLocation;
	
	private static String TAG="MAIN";
	private TextView longitud;
	private TextView latitud;
	private TextView precision;
	private TextView satelites;
	private Button Localizar;
	
	private LocationManager locManager;
	private LocationListener locListener;
	
	MyOverlay itemizedOverlay;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
        mapa = (MapView)findViewById(R.id.mapa);

        mapa.setBuiltInZoomControls(true);
        
        this.longitud = (TextView) findViewById(R.id.longitud);
        this.latitud = (TextView) findViewById(R.id.latitud);
        this.precision = (TextView) findViewById(R.id.precision);
        this.satelites = (TextView) findViewById(R.id.satelites);
        this.Localizar = (Button) findViewById(R.id.localizar);
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        itemizedOverlay = new MyOverlay(drawable, this);

        obtenerUbicacion();
        
        Localizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mostrarUbicacionMapa();
			}
		});
        
        //localizar();
	}
	
	private void obtenerUbicacion(){

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		Log.i(TAG,"ABCD");
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {

		    	if(isBetterLocation(location, Main.this.currentBestLocation)){
		    		longitud_actual= location.getLongitude();
			    	latitud_actual = location.getLatitude();
		    	}else{
		    		longitud_actual= Main.this.currentBestLocation.getLongitude();
			    	latitud_actual = Main.this.currentBestLocation.getLatitude();
		    	}
		    	
		    	Log.i(TAG, longitud_actual+","+latitud_actual);
		    	
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };


		//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	public void mostrarUbicacionMapa(){
		List<Overlay> mapOverlays = mapa.getOverlays();
    	
		Log.i(TAG, longitud_actual+","+latitud_actual);
 		GeoPoint point = new GeoPoint((int)(latitud_actual* 1E6), (int)(longitud_actual* 1E6));
 		
 		OverlayItem overlayitem = new OverlayItem(point, "Hola",
 				"!Precision!");
  
 		itemizedOverlay.addOverlay(overlayitem);
 		mapOverlays.add(itemizedOverlay);
 		
 		
 		MapController mapController = mapa.getController();
 		
 		mapController.animateTo(point);
 	//	mapController.setZoom(20);

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
	
	
	/*
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
    			LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    	
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
    */
    
	
	private static final int THIRTY_SECONDS = 1000 * 30;

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > THIRTY_SECONDS;
	    boolean isSignificantlyOlder = timeDelta < -THIRTY_SECONDS;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
		
	
	

}
