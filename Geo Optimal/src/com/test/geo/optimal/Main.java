package com.test.geo.optimal;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.test.geo.optimal.library.Muestra;
import com.test.geo.optimal.persistence.SQLiteManager;



public class Main extends MapActivity{

	private MapView mapa = null;
	private static double longitud_actual;
	private static double latitud_actual;
	private static double precision_actual;
	private static int numero_satelites;
	
	private static String proveedor;
	private Location currentBestLocation;
	
	private static String TAG="MAIN";
	private TextView longitud;
	private TextView latitud;
	private TextView precision;
	private TextView satelites;
	private EditText descripcion;
	private ImageButton Localizar;
	private Button mala;
	private Button buena;
	private Button excelente;
	private Button resultados;
	private Button estadisticas;
	
	private LinearLayout votar;
	private LinearLayout contendor_descripcion;
	
	private LocationManager locManager;
	private LocationListener locListener;
	
	MyOverlay itemizedOverlay;
	private View viewToBeCaptured;
	

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
        this.Localizar = (ImageButton) findViewById(R.id.localizar);
        this.mala= (Button) findViewById(R.id.mala);
        this.buena= (Button) findViewById(R.id.buena);
        this.excelente= (Button) findViewById(R.id.excelente);
        this.descripcion= (EditText) findViewById(R.id.descripcion);
        this.resultados = (Button) findViewById(R.id.resultados);
        this.estadisticas= (Button) findViewById(R.id.estadisticas);
        
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.push_pin);
        itemizedOverlay = new MyOverlay(drawable, this);

        obtenerUbicacion();
        
        Localizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mostrarUbicacionMapa();
				votar=(LinearLayout)findViewById(R.id.votar);
				votar.setVisibility(View.VISIBLE);
				contendor_descripcion=(LinearLayout)findViewById(R.id.conteneror_descripcion);
				contendor_descripcion.setVisibility(View.VISIBLE);
			}
		});
        
        mala.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				agregarMuestra(Muestra.MALA);
			}
		});
        
        buena.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				agregarMuestra(Muestra.BUENA);
			}
		});
        
        excelente.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				agregarMuestra(Muestra.EXCELENTE);
			}
		});
        
        resultados.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verResultados();
			}
		});	
        
        estadisticas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verEstadisticas();
			}
		});
        
        //localizar();
	}
	
	private void verResultados(){
		Intent intent = new Intent(this,Results.class);
		startActivity(intent);
	}
	
	private void verEstadisticas(){
		Intent intent = new Intent(this,Estadisticas.class);
		startActivity(intent);
	}
	
	private void agregarMuestra(int calificacion){
		boolean guardar=true;
		String mensaje="";
		
		
		String des  =descripcion.getText().toString();
		if(des.trim().length()<4){
			guardar=false;
			mensaje="Por favor agrege descripcion";
		}
		
		if(latitud_actual==0.0){
			guardar=false;
			mensaje="No se ha podido obtener ubicacion";
		}
		
		if(guardar){
			mensaje="Guardado exitosamente";
			String path_imagen=guardarImagen();		
			Muestra muestra = new Muestra(latitud_actual+"",longitud_actual+"",precision_actual,proveedor,numero_satelites,calificacion,des,path_imagen);
			SQLiteManager.getInstance().saveMuestra(muestra, this);
	
			for(Muestra m:SQLiteManager.getInstance().getAll(this)){
				Log.i(TAG,m.getDescripcion());
		
			}
			reiniciar();
		}

			Toast toast2 =
	            Toast.makeText(getApplicationContext(),
	                    mensaje, Toast.LENGTH_SHORT);
	 
	        toast2.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
	 
	        toast2.show();
	
	}
	
	private void reiniciar(){
		latitud_actual=0.0;
		longitud_actual=0.0;
		precision_actual=0.0;
		numero_satelites=0;
		latitud.setText("");
		longitud.setText("");
		precision.setText("");
		satelites.setText("");
		votar.setVisibility(View.GONE);
		contendor_descripcion.setVisibility(View.GONE);
		descripcion.setText("");
		mapa.invalidate();
	    mapa.postInvalidate();
	    for (int i=0; i<mapa.getOverlays().size(); i++ ) {
	        mapa.getOverlays().remove(i);
	    }
	    Drawable drawable = this.getResources().getDrawable(R.drawable.push_pin);
        itemizedOverlay = new MyOverlay(drawable, this);
	}
	
	
	private String guardarImagen(){

        viewToBeCaptured = this.findViewById(R.id.mapa);
        viewToBeCaptured.setDrawingCacheEnabled(true);
        Bitmap b = viewToBeCaptured.getDrawingCache();

        File sd = Environment.getExternalStorageDirectory();
        String the_path = Environment.getExternalStorageDirectory()
        			+ File.separator + "prohibidoparquear";
        String uid = UUID.randomUUID().toString();
      	String the_file = the_path + File.separator +  uid +".jpg";

        File f = new File(the_file);
          
          try {
               if (sd.canWrite()) {
                    f.createNewFile();
                    OutputStream os = new FileOutputStream(f);
                    b.compress(Bitmap.CompressFormat.JPEG, 90, os);
                    os.close();
               }
          } catch (FileNotFoundException e) {
               e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          }
          viewToBeCaptured.setDrawingCacheEnabled(false);
          return uid;
	}
	private void obtenerUbicacion(){

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	Log.i(TAG, "-------------------------------------------------");
		    	Log.i(TAG,location.getExtras().getInt("satellites")+"");
		    	Log.i(TAG, "-------------------------------------------------");
		    	if(isBetterLocation(location, Main.this.currentBestLocation)){
		    		longitud_actual= location.getLongitude();
			    	latitud_actual = location.getLatitude();
			    	proveedor=location.getProvider();
			    	precision_actual=location.getAccuracy();
			    	numero_satelites=location.getExtras().getInt("satellites");
			    	Main.this.
			    	currentBestLocation=location;
		    	}else{
		    		longitud_actual= Main.this.currentBestLocation.getLongitude();
			    	latitud_actual = Main.this.currentBestLocation.getLatitude();
			    	proveedor=Main.this.currentBestLocation.getProvider();
			    	precision_actual=Main.this.currentBestLocation.getAccuracy();
			    	numero_satelites=Main.this.currentBestLocation.getExtras().getInt("satellites");
		    	}
		    	latitud.setText("Latitud: " + String.valueOf(latitud_actual));
	    		longitud.setText("Longitud: " + String.valueOf(longitud_actual));
	    		precision.setText("Precision: " + String.valueOf(precision_actual) +" m");
	    		satelites.setText("# satelites: " + String.valueOf(numero_satelites) );
	    		
		    	Log.i(TAG, "-------------------------------------------------");
		    	Log.i(TAG,"longitud:"+longitud_actual);
		    	Log.i(TAG,"latitud:"+latitud_actual);
		    	Log.i(TAG, "-------------------------------------------------");
		    	
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {
		    	/*Log.i(TAG, "-------------------------------------------------");
		    	Log.i(TAG, extras+"");
		    	Log.i(TAG, extras.getInt("satellites")+"");
		    	Log.i(TAG, "-------------------------------------------------");*/
		    }

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
 		mapController.setZoom(20);
 		

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
