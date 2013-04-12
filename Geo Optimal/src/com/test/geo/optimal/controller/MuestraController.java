package com.test.geo.optimal.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restful.DoRest;
import restful.DoRest.Verbs;
import restful.DoRestEventListener;
import utils.AppGlobal;
import utils.Params;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.test.geo.optimal.DetalleMuestra;
import com.test.geo.optimal.library.Muestra;



public class MuestraController {

	private static String TAG="MuestraController";
	private static	ProgressDialog progress;
	
	private static MuestraController instance;
	public static MuestraController getInstance() {
		if (instance == null) {
			instance = new MuestraController();
		}
		return instance;
	}

	private MuestraController() {

	}

	
	public void addMuestra(Muestra muestra){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", "$2a$10$8VNezZAIjVQzjbn7D.AKx."));
		params.add(new BasicNameValuePair("muestra[calificacion]", muestra.getCalificacion()+""));
		params.add(new BasicNameValuePair("muestra[description]", muestra.getDescripcion()));
		params.add(new BasicNameValuePair("muestra[latitude]", muestra.getLatitude()));
		params.add(new BasicNameValuePair("muestra[longitude]", muestra.getLonguitude()));
		params.add(new BasicNameValuePair("muestra[number_satelite]", muestra.getNumeroSatelites()+""));
		params.add(new BasicNameValuePair("muestra[precision]", muestra.getPrecision()+""));
		params.add(new BasicNameValuePair("muestra[provider_name]", muestra.getProviderName()));
		params.add(new BasicNameValuePair("muestra[path]", muestra.getPathImagen()));
		
		DoRest rest_get_add_event = new DoRest("http://geolab.herokuapp.com/api/muestras/create",
				Verbs.POST, params);
		rest_get_add_event.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Log.e(TAG, "Se presento un error al cargar las categorias");
				// showCategoriesError();
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				if (status == 200) {
					JSONObject response = null;
					try {
						response = new JSONObject(json_data_string);
						if(response.getBoolean("status")){
							Log.i(TAG,"agregada muestra");
						}else{
							Log.e(TAG, "error al agregar el evento");
						}
					}catch (JSONException e) {
						response = null;
					}
				}else{
					Log.e(TAG, "No se encontro la url especificada");
				}
			}
		});
		rest_get_add_event.call();
	}
	
	public List<Muestra> getMuestra(final Context context, final TableLayout table){
		
		final List<Muestra> muestras = new LinkedList<Muestra>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", "$2a$10$8VNezZAIjVQzjbn7D.AKx."));
		
		showProgess(context);
		
		DoRest rest_get_events = new DoRest("http://geolab.herokuapp.com/api/muestras",
				Verbs.POST, params);
		rest_get_events.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Log.e(TAG, "Se presento un error al cargar las categorias");
				// showCategoriesError();
				hideProgess();
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				if (status == 200) {
					JSONObject response = null;
					JSONObject data = null;
					try {
						response = new JSONObject(json_data_string);
						if(response.getBoolean("status")){
							data = response.getJSONObject("data");
							JSONArray muestras_array = data
									.getJSONArray("muestras");
							for (int i = 0; i < muestras_array.length(); i++) {
								JSONObject muestra = null;
								Muestra muestra_model = null;
								muestra = muestras_array.getJSONObject(i);
								
								muestra_model = new Muestra(muestra.getString("latitude") ,muestra.getString("longitude"),muestra.getDouble("precision"),muestra.getString("provider_name"),muestra.getInt("number_satelite"),muestra.getInt("calificacion"),muestra.getString("description"),muestra.getString("path"));
							
								muestras.add(muestra_model);
							}
							Log.e(TAG, muestras.size()+1+"");
							fillMuestras(muestras,table,context);
							hideProgess();
						} else {
							JSONArray errors = response
									.getJSONArray("errors");
							Log.e(TAG, errors+"");
							hideProgess();
						}
						
					} catch (JSONException e) {
						response = null;
						hideProgess();
						Log.e(TAG, muestras.size()+" "+e.getMessage());
					}
				}else{
					Log.e(TAG, "No se encontro la url especificada");
					hideProgess();
				}
			}
		});
		rest_get_events.call();
		
		return muestras;
	}
	
	public void showProgess(Context context){
		progress = ProgressDialog.show(context, "","Cargando...", true);
	}
	
	public void hideProgess(){
		progress.dismiss();
	}

	public void fillMuestras(List<Muestra> muestras,TableLayout table, final Context context){
		for (final Muestra muestra : muestras) {

			TableRow newRow = new TableRow(context);

			newRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v.setBackgroundColor(Color.GRAY);
					
					Handler handler  = new Handler();
					handler.postDelayed(cambiarActivity(muestra,v,context), 1000);
					
				}
			});
			TextView day1High = new TextView(context);
			day1High.setText(muestra.getLatitude());
			day1High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day1High);

			TextView day2High = new TextView(context);
			day2High.setText(muestra.getLonguitude() + "");
			day2High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day2High);

			TextView day3High = new TextView(context);
			day3High.setText(muestra.getCalificacion() + "");
			day3High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day3High);

			TextView day4High = new TextView(context);
			day4High.setText(muestra.getPrecision() + "");
			day4High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day4High);

			TextView day5High = new TextView(context);
			day5High.setText(muestra.getNumeroSatelites() + "");
			day5High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day5High);

			TextView day6High = new TextView(context);
			day6High.setText(muestra.getProviderName());
			day6High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day6High);

			table.addView(newRow);
		}
	}
	
	private Runnable cambiarActivity(final Muestra muestra, final View v, final Context context) {
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				
				v.setBackgroundColor(Color.TRANSPARENT);
				
				
				Params params = new Params();
				

				params.add(new BasicNameValuePair("latitud", muestra.getLatitude()));
				params.add(new BasicNameValuePair("longitud", muestra.getLonguitude()));
				params.add(new BasicNameValuePair("calificacion", muestra.getCalificacion()+""));
				params.add(new BasicNameValuePair("precision", muestra.getPrecision()+""));
				params.add(new BasicNameValuePair("satelites", muestra.getNumeroSatelites()+""));
				params.add(new BasicNameValuePair("proveedor", muestra.getProviderName()));
				params.add(new BasicNameValuePair("imagen", muestra.getPathImagen()));
				Log.e(TAG, params+"");
					AppGlobal.getInstance().initialize(context);
					AppGlobal.getInstance().dispatcher.open((Activity)context, "detalle", false, params);
				
			}
		};
		return runnable;
	}
	
	
}


