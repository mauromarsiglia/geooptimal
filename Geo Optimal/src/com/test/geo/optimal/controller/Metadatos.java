/**
 * 
 */
package com.test.geo.optimal.controller;

import java.util.List;

import android.location.LocationManager;

import com.test.geo.optimal.library.Muestra;

/**
 * @author LUSTER
 *
 */
public class Metadatos {
	
	//para contar la calificacion de cada muestra
	private int count_malas;
	private int count_buenas;
	private int count_excelentes;
	
	//para sacar el promedio de calificacion de cada muestra
	private double malas;
	private double buenas;
	private double excelentes;
	
	//para acumular la precision de cada calificacion
	private int count_error_malas;
	private int count_error_buenas;
	private int count_error_excelentes;
	private int total_error;
	
	//promedios del acumulado de precision de cada calificacion
	private double prom_error_malas;
	private double prom_error_buenas;
	private double prom_error_excelentes;
	
	//acumular la cantidad de satelites en cada calificacion
	private int count_malas_satelites;
	private int count_buenas_satelites;
	private int count_excelentes_satelites;
	private int total_satelites;
	
	//promedio de satelites en cada clasificacion
	private double prom_malas_satelites;
	private double prom_buenas_satelites;
	private double prom_excelentes_satelites;
	
	//countar las veces que se registro gps y network provider en cada calificacion
	private int count_malas_gps;
	private int count_malas_net;
	private int count_buenas_gps;
	private int count_buenas_net;
	private int count_excelentes_gps;
	private int count_excelentes_net;
	private int total_satelites_gps;
	private int total_satelites_net;
	
	//promedio de cada proveedor en base a la calificacion
	private double prom_malas_gps;
	private double prom_malas_net;
	private double prom_buenas_gps;
	private double prom_buenas_net;
	private double prom_excelentes_gps;
	private double prom_excelentes_net;
	
	private static Metadatos instance;
	
	public static Metadatos getInstance(){
		if(instance == null){
			instance = new Metadatos();
		}
		return instance;
	}
	
	private Metadatos(){
	
	}
	
	public void reset(){
		
		count_malas = 0;
		count_buenas = 0;
		count_excelentes = 0;
		
		count_error_malas = 0;
		count_error_buenas = 0;
		count_error_excelentes = 0;
		total_error = 0;
		
		count_malas_satelites = 0;
		count_buenas_satelites = 0;
		count_excelentes_satelites = 0;
		total_satelites = 0;
		
		count_malas_gps = 0;
		count_malas_net = 0;
		count_buenas_gps = 0;
		count_buenas_net = 0;
		count_excelentes_gps = 0;
		count_excelentes_net = 0;
		total_satelites_gps = 0;
		total_satelites_net = 0;
		
	}
	
	public void saveEstadistica(List<Muestra> listaMuestras){
		
		this.reset();
		
		for(Muestra item : listaMuestras){
			if(item.getCalificacion() == Muestra.MALA){
				
				this.count_malas++;
				this.count_error_malas += item.getPrecision();
				this.count_malas_satelites += item.getNumeroSatelites();
				
				if(item.getProviderName().equals(LocationManager.GPS_PROVIDER)){
					this.count_malas_gps++;
					this.total_satelites_gps += item.getNumeroSatelites(); 
				}else{
					this.count_malas_net++;
					this.total_satelites_net += item.getNumeroSatelites();
				}
				
			}else if(item.getCalificacion() == Muestra.BUENA){

				this.count_buenas++;
				this.count_error_buenas += item.getPrecision();
				this.count_buenas_satelites += item.getNumeroSatelites();
				
				if(item.getProviderName().equals(LocationManager.GPS_PROVIDER)){
					this.count_buenas_gps++;
					this.total_satelites_gps += item.getNumeroSatelites(); 
				}else{
					this.count_buenas_net++;
					this.total_satelites_net += item.getNumeroSatelites();
				}
				
			}else{

				this.count_excelentes++;
				this.count_error_excelentes += item.getPrecision();
				this.count_excelentes_satelites += item.getNumeroSatelites();
				
				if(item.getProviderName().equals(LocationManager.GPS_PROVIDER)){
					this.count_excelentes_gps++;
					this.total_satelites_gps += item.getNumeroSatelites(); 
				}else{
					this.count_excelentes_net++;
					this.total_satelites_net += item.getNumeroSatelites();
				}
				
			}
			
			this.total_error += item.getPrecision();
			this.total_satelites += item.getNumeroSatelites();			
			
		}
		
		//conteo
		int size = listaMuestras.size();
		this.malas = count_malas / size;
		this.buenas = count_buenas / size;
		this.excelentes = count_excelentes / size;
		
		//precision
		this.prom_error_malas = this.count_error_malas / this.total_error;
		this.prom_error_buenas = this.count_error_buenas / this.total_error;
		this.prom_error_excelentes = this.count_error_excelentes / this.total_error;
		
		//satelites
		this.prom_malas_satelites = this.count_malas_satelites / this.total_satelites;
		this.prom_buenas_satelites = this.count_buenas_satelites / this.total_satelites;
		this.prom_excelentes_satelites = this.count_excelentes_satelites / this.total_satelites;
		
		//proveedores gps
		this.prom_malas_gps = this.count_malas_gps / this.total_satelites_gps;
		this.prom_buenas_gps = this.count_buenas_gps / this.total_satelites_gps;
		this.prom_excelentes_gps = this.count_excelentes_gps / this.total_satelites_gps;
		
		//proveedores net
		this.prom_malas_net = this.count_malas_net / this.total_satelites_net;
		this.prom_buenas_net = this.count_buenas_net / this.total_satelites_net;
		this.prom_excelentes_net = this.count_excelentes_net / this.total_satelites_net;
		
	}

	public int getCount_malas() {
		return count_malas;
	}

	public void setCount_malas(int count_malas) {
		this.count_malas = count_malas;
	}

	public int getCount_buenas() {
		return count_buenas;
	}

	public void setCount_buenas(int count_buenas) {
		this.count_buenas = count_buenas;
	}

	public int getCount_excelentes() {
		return count_excelentes;
	}

	public void setCount_excelentes(int count_excelentes) {
		this.count_excelentes = count_excelentes;
	}

	public double getMalas() {
		return malas;
	}

	public void setMalas(double malas) {
		this.malas = malas;
	}

	public double getBuenas() {
		return buenas;
	}

	public void setBuenas(double buenas) {
		this.buenas = buenas;
	}

	public double getExcelentes() {
		return excelentes;
	}

	public void setExcelentes(double excelentes) {
		this.excelentes = excelentes;
	}

	public int getCount_error_malas() {
		return count_error_malas;
	}

	public void setCount_error_malas(int count_error_malas) {
		this.count_error_malas = count_error_malas;
	}

	public int getCount_error_buenas() {
		return count_error_buenas;
	}

	public void setCount_error_buenas(int count_error_buenas) {
		this.count_error_buenas = count_error_buenas;
	}

	public int getCount_error_excelentes() {
		return count_error_excelentes;
	}

	public void setCount_error_excelentes(int count_error_excelentes) {
		this.count_error_excelentes = count_error_excelentes;
	}

	public int getTotal_error() {
		return total_error;
	}

	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}

	public double getProm_error_malas() {
		return prom_error_malas;
	}

	public void setProm_error_malas(double prom_error_malas) {
		this.prom_error_malas = prom_error_malas;
	}

	public double getProm_error_buenas() {
		return prom_error_buenas;
	}

	public void setProm_error_buenas(double prom_error_buenas) {
		this.prom_error_buenas = prom_error_buenas;
	}

	public double getProm_error_excelentes() {
		return prom_error_excelentes;
	}

	public void setProm_error_excelentes(double prom_error_excelentes) {
		this.prom_error_excelentes = prom_error_excelentes;
	}

	public int getCount_malas_satelites() {
		return count_malas_satelites;
	}

	public void setCount_malas_satelites(int count_malas_satelites) {
		this.count_malas_satelites = count_malas_satelites;
	}

	public int getCount_buenas_satelites() {
		return count_buenas_satelites;
	}

	public void setCount_buenas_satelites(int count_buenas_satelites) {
		this.count_buenas_satelites = count_buenas_satelites;
	}

	public int getCount_excelentes_satelites() {
		return count_excelentes_satelites;
	}

	public void setCount_excelentes_satelites(int count_excelentes_satelites) {
		this.count_excelentes_satelites = count_excelentes_satelites;
	}

	public int getTotal_satelites() {
		return total_satelites;
	}

	public void setTotal_satelites(int total_satelites) {
		this.total_satelites = total_satelites;
	}

	public double getProm_malas_satelites() {
		return prom_malas_satelites;
	}

	public void setProm_malas_satelites(double prom_malas_satelites) {
		this.prom_malas_satelites = prom_malas_satelites;
	}

	public double getProm_buenas_satelites() {
		return prom_buenas_satelites;
	}

	public void setProm_buenas_satelites(double prom_buenas_satelites) {
		this.prom_buenas_satelites = prom_buenas_satelites;
	}

	public double getProm_excelentes_satelites() {
		return prom_excelentes_satelites;
	}

	public void setProm_excelentes_satelites(double prom_excelentes_satelites) {
		this.prom_excelentes_satelites = prom_excelentes_satelites;
	}

	public int getCount_malas_gps() {
		return count_malas_gps;
	}

	public void setCount_malas_gps(int count_malas_gps) {
		this.count_malas_gps = count_malas_gps;
	}

	public int getCount_malas_net() {
		return count_malas_net;
	}

	public void setCount_malas_net(int count_malas_net) {
		this.count_malas_net = count_malas_net;
	}

	public int getCount_buenas_gps() {
		return count_buenas_gps;
	}

	public void setCount_buenas_gps(int count_buenas_gps) {
		this.count_buenas_gps = count_buenas_gps;
	}

	public int getCount_buenas_net() {
		return count_buenas_net;
	}

	public void setCount_buenas_net(int count_buenas_net) {
		this.count_buenas_net = count_buenas_net;
	}

	public int getCount_excelentes_gps() {
		return count_excelentes_gps;
	}

	public void setCount_excelentes_gps(int count_excelentes_gps) {
		this.count_excelentes_gps = count_excelentes_gps;
	}

	public int getCount_excelentes_net() {
		return count_excelentes_net;
	}

	public void setCount_excelentes_net(int count_excelentes_net) {
		this.count_excelentes_net = count_excelentes_net;
	}

	public int getTotal_satelites_gps() {
		return total_satelites_gps;
	}

	public void setTotal_satelites_gps(int total_satelites_gps) {
		this.total_satelites_gps = total_satelites_gps;
	}

	public int getTotal_satelites_net() {
		return total_satelites_net;
	}

	public void setTotal_satelites_net(int total_satelites_net) {
		this.total_satelites_net = total_satelites_net;
	}

	public double getProm_malas_gps() {
		return prom_malas_gps;
	}

	public void setProm_malas_gps(double prom_malas_gps) {
		this.prom_malas_gps = prom_malas_gps;
	}

	public double getProm_malas_net() {
		return prom_malas_net;
	}

	public void setProm_malas_net(double prom_malas_net) {
		this.prom_malas_net = prom_malas_net;
	}

	public double getProm_buenas_gps() {
		return prom_buenas_gps;
	}

	public void setProm_buenas_gps(double prom_buenas_gps) {
		this.prom_buenas_gps = prom_buenas_gps;
	}

	public double getProm_buenas_net() {
		return prom_buenas_net;
	}

	public void setProm_buenas_net(double prom_buenas_net) {
		this.prom_buenas_net = prom_buenas_net;
	}

	public double getProm_excelentes_gps() {
		return prom_excelentes_gps;
	}

	public void setProm_excelentes_gps(double prom_excelentes_gps) {
		this.prom_excelentes_gps = prom_excelentes_gps;
	}

	public double getProm_excelentes_net() {
		return prom_excelentes_net;
	}

	public void setProm_excelentes_net(double prom_excelentes_net) {
		this.prom_excelentes_net = prom_excelentes_net;
	}

}
