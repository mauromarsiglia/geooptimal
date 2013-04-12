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
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.test.geo.optimal.R;
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
	
	public void getMuestra(final Context context, final TableLayout table, final int opcion){
		
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
							if(opcion==1){
								fillMuestras(muestras,table,context);
							}else{
								Metadatos.getInstance().saveEstadistica(muestras);
								fillEstadisticas(muestras,table,context);
							}
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
	
	public void fillEstadisticas(List<Muestra> muestras,TableLayout tableLayout, final Context context){
				//header
				TableRow newRow = new TableRow(context);
				TextView text = new TextView(context);
				text.setText(context.getString(R.string.desc_column));
				text.setGravity(Gravity.CENTER_HORIZONTAL);
				newRow.addView(text);
				newRow.addView(getTextView( R.string.value_column ,context));
				tableLayout.addView(newRow);
				
				//Cantidad de muestra segun calificacion
				tableLayout.addView(createRow(R.string.cantidad_muestras_malas, Metadatos.getInstance().getCount_malas()+"",context));
				tableLayout.addView(createRow(R.string.cantidad_muestras_buenas, Metadatos.getInstance().getCount_buenas()+"",context));
				tableLayout.addView(createRow(R.string.cantidad_muestras_excelentes, Metadatos.getInstance().getCount_excelentes()+"",context));
				
				//Promedio de muestras por categoria
				tableLayout.addView(createRow(R.string.porcentaje_muestras_malas, Metadatos.getInstance().getMalas()+"%",context));
				tableLayout.addView(createRow(R.string.porcentaje_muestras_buenas, Metadatos.getInstance().getBuenas()+"%",context));
				tableLayout.addView(createRow(R.string.porcentaje_muestras_excelentes, Metadatos.getInstance().getExcelentes()+"%",context));
				
				
				//Total precisión de las muestras segun la clasificacion
				tableLayout.addView(createRow(R.string.precision_muestras_malas, Metadatos.getInstance().getCount_error_malas()+"",context));
				tableLayout.addView(createRow(R.string.precision_muestras_buenas, Metadatos.getInstance().getCount_error_buenas()+"",context));
				tableLayout.addView(createRow(R.string.precision_muestras_excelentes, Metadatos.getInstance().getCount_error_excelentes()+"",context));
				
				//Total Acumulado de error
				tableLayout.addView(createRow(R.string.total_imprecision_promedios, Metadatos.getInstance().getTotal_error()+"",context));
				
				//Promedio de precisión segun la calificacion
				tableLayout.addView(createRow(R.string.porcentaje_precision_malas, Metadatos.getInstance().getProm_error_malas()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_precision_buenas, Metadatos.getInstance().getProm_error_buenas()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_precision_excelente, Metadatos.getInstance().getProm_error_excelentes()+"",context));
				
				//Promedio de satelites usados
				tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_malas, Metadatos.getInstance().getProm_malas_satelites()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_buenas, Metadatos.getInstance().getProm_buenas_satelites()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_satelites_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_satelites()+"",context));
				
				//total de veces que se uso cada proveedor
				tableLayout.addView(createRow(R.string.cantidad_gps, Metadatos.getInstance().getCount_total_veces_gps()+"",context));
				tableLayout.addView(createRow(R.string.cantidad_network, Metadatos.getInstance().getCount_total_veces_net()+"",context));
				
				//Resumen uso de gps segun calificacion
				//gps
				tableLayout.addView(createRow(R.string.proveedor_gps_muestas_malas, Metadatos.getInstance().getCount_malas_gps()+"",context));
				tableLayout.addView(createRow(R.string.proveedor_gps_muestras_buenas, Metadatos.getInstance().getCount_buenas_gps()+"",context));
				tableLayout.addView(createRow(R.string.proveedor_gps_muestras_excelentes, Metadatos.getInstance().getCount_excelentes_gps()+"",context));
				
				//network
				tableLayout.addView(createRow(R.string.proveedor_newtork_muestras_malas, Metadatos.getInstance().getCount_malas_net()+"",context));
				tableLayout.addView(createRow(R.string.proveedor_network_muestras_buenas, Metadatos.getInstance().getCount_buenas_net()+"",context));
				tableLayout.addView(createRow(R.string.proveedor_network_muestras_excelentes, Metadatos.getInstance().getCount_excelentes_net()+"",context));
				
				//Promedio de uso de proveedor por calificacion
				//gps
				tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_malas, Metadatos.getInstance().getProm_malas_gps()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_buenas, Metadatos.getInstance().getProm_buenas_gps()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_gps_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_gps()+"",context));
				
				//network
				tableLayout.addView(createRow(R.string.porcentaje_network_muestras_malas, Metadatos.getInstance().getProm_malas_net()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_network_muestras_buenas, Metadatos.getInstance().getProm_buenas_net()+"",context));
				tableLayout.addView(createRow(R.string.porcentaje_network_muestras_excelentes, Metadatos.getInstance().getProm_excelentes_net()+"",context));
	}
	
	private TextView getTextView(int string_id, Context context){
		
		TextView text = new TextView(context);
		text.setText(context.getString(string_id));
		text.setGravity(Gravity.LEFT);
		
		return text;
		
	}
	
	private TextView getTextView(String content, Context context){
		
		TextView text = new TextView(context);
		text.setText(content);
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		
		return text;
		
	}
	
	private TableRow createRow(int string_id, String content, Context context){
		
		TableRow newRow = new TableRow(context);
		newRow.addView(getTextView(string_id,context));
		newRow.addView(getTextView( content,context ));
		
		return newRow;
		
	}
	
	
}


