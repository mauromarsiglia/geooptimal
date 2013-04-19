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
import android.os.Handler;
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
import com.test.geo.optimal.controller.MuestraController;
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
	
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	
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
        
        
        
        
        Localizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				obtenerUbicacion();
				
				Handler handler  = new Handler();
				handler.postDelayed(guardarUbicacion(), 1000 * 60);
				
				/*Runnable run = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						obtenerUbicacion();
						
						try {
							Thread.sleep(60 * 1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						locationManager.removeUpdates(locationListener);
						mostrarUbicacionMapa();
						
						votar = (LinearLayout)findViewById(R.id.votar);
						votar.setVisibility(View.VISIBLE);
						
						contendor_descripcion=(LinearLayout)findViewById(R.id.conteneror_descripcion);
						contendor_descripcion.setVisibility(View.VISIBLE);
						
					}
					
				};
				
				
				Thread hilo = new Thread(run);
				hilo.start();*/
				
				
			}

			private Runnable guardarUbicacion() {
				// TODO Auto-generated method stub
				
				return new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						locationManager.removeUpdates(locationListener);
						mostrarUbicacionMapa();
						
						votar = (LinearLayout)findViewById(R.id.votar);
						votar.setVisibility(View.VISIBLE);
						
						contendor_descripcion=(LinearLayout)findViewById(R.id.conteneror_descripcion);
						contendor_descripcion.setVisibility(View.VISIBLE);
						resultados.setVisibility(View.GONE);
						estadisticas.setVisibility(View.GONE);
						
					}
					
				};
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
	
	public void onResume(){
		super.onResume();
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
			MuestraController.getInstance().addMuestra(muestra);
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
        estadisticas.setVisibility(View.VISIBLE);
        resultados.setVisibility(View.VISIBLE);
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

		this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				
		this.locationListener = new LocationListener() {
			
		    public void onLocationChanged(Location location) {
		    	
//		    	Log.i(TAG, "-------------------------------------------------");
////		    	Log.i(TAG,location.getExtras().getInt("satellites")+"");
//		    	Log.i(TAG, "-------------------------------------------------");
//		    	
		    	if(isBetterLocation(location, Main.this.currentBestLocation)){
		    		
		    		longitud_actual= location.getLongitude();
			    	latitud_actual = location.getLatitude();
			    	proveedor=location.getProvider();
			    	precision_actual=location.getAccuracy();
			    	numero_satelites=location.getExtras().getInt("satellites");
			    	
			    	Main.this.currentBestLocation=location;
			    	
			    	latitud.setText("Latitud: " + String.valueOf(latitud_actual));
		    		longitud.setText("Longitud: " + String.valueOf(longitud_actual));
		    		precision.setText("Precision: " + String.valueOf(precision_actual) +" m");
		    		satelites.setText("# satelites: " + String.valueOf(numero_satelites) );
//		    		
//			    	Log.i(TAG, "-------------------------------------------------");
//			    	Log.i(TAG,"longitud:"+longitud_actual);
//			    	Log.i(TAG,"latitud:"+latitud_actual);
//			    	Log.i(TAG, "-------------------------------------------------");
		    	}else{
		    		
		    		longitud_actual= Main.this.currentBestLocation.getLongitude();
			    	latitud_actual = Main.this.currentBestLocation.getLatitude();
			    	proveedor=Main.this.currentBestLocation.getProvider();
			    	precision_actual=Main.this.currentBestLocation.getAccuracy();
			    	numero_satelites=Main.this.currentBestLocation.getExtras().getInt("satellites");
			    	
		    	}
		    	
		    
		    	
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {
		    	
		    }

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };


		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		
		
		
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
	
	private static final int THIRTY_SECONDS = 1000 * 30;

	
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    
		if (currentBestLocation == null) {
	        return true;
	    }


	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > THIRTY_SECONDS;
	    boolean isSignificantlyOlder = timeDelta < -THIRTY_SECONDS;
	    boolean isNewer = timeDelta > 0;

	    
	    if (isSignificantlyNewer) {
	        return true;
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	 
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
		
	@Override
	public void onStop(){
		super.onStop();
		if(locationManager!=null){
			this.locationManager.removeUpdates(this.locationListener);
		}
	}
	

}
