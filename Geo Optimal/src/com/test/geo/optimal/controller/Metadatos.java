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
	private int count_total_veces_gps;
	private int count_total_veces_net;
	
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
		count_total_veces_gps = 0;
		count_total_veces_net = 0;
		
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
					this.count_total_veces_gps++; 
				}else{
					this.count_malas_net++;
					this.count_total_veces_net++;
				}
				
			}else if(item.getCalificacion() == Muestra.BUENA){

				this.count_buenas++;
				this.count_error_buenas += item.getPrecision();
				this.count_buenas_satelites += item.getNumeroSatelites();
				
				if(item.getProviderName().equals(LocationManager.GPS_PROVIDER)){
					this.count_buenas_gps++;
					this.count_total_veces_gps++;; 
				}else{
					this.count_buenas_net++;
					this.count_total_veces_net++;
				}
				
			}else{

				this.count_excelentes++;
				this.count_error_excelentes += item.getPrecision();
				this.count_excelentes_satelites += item.getNumeroSatelites();
				
				if(item.getProviderName().equals(LocationManager.GPS_PROVIDER)){
					this.count_excelentes_gps++;
					this.count_total_veces_gps++; 
				}else{
					this.count_excelentes_net++;
					this.count_total_veces_net++;
				}
				
			}
			
			this.total_error += item.getPrecision();
			this.total_satelites += item.getNumeroSatelites();			
			
		}
		
		//conteo
		int size = listaMuestras.size();
		
		if(size>0){
			this.malas = count_malas*100 / size;
			this.buenas = count_buenas*100 / size;
			this.excelentes = count_excelentes*100 / size;
		}
		
		if(this.count_malas > 0){
			this.prom_error_malas = this.count_error_malas / count_malas;
		}
		
		if(this.count_buenas > 0){
			this.prom_error_buenas = this.count_error_buenas / count_buenas;
		}
		
		if(this.count_excelentes > 0){
			this.prom_error_excelentes = this.count_error_excelentes / this.count_excelentes;
		}
		
		//satelites
		if(this.count_malas > 0){
			this.prom_malas_satelites = this.count_malas_satelites / this.count_malas;
		}
		
		if(this.count_buenas > 0){
			this.prom_buenas_satelites = this.count_buenas_satelites / this.count_buenas;
		}
		
		if(this.count_excelentes > 0){
			this.prom_excelentes_satelites = this.count_excelentes_satelites / this.count_excelentes;
		}
		
		//proveedores gps
		if(this.count_malas > 0){
			this.prom_malas_gps = this.count_malas_gps * 100 / this.count_malas;
		}
		
		if(this.count_buenas > 0){
			this.prom_buenas_gps = this.count_buenas_gps * 100 / this.count_buenas;
		}
		
		if(this.count_excelentes > 0){
			this.prom_excelentes_gps = this.count_excelentes_gps * 100 / this.count_excelentes;
		}
		
		//proveedores net
		if(this.count_malas > 0){
			this.prom_malas_net = this.count_malas_net * 100 / this.count_malas;
		}
		
		if(this.count_buenas > 0){
			this.prom_buenas_net = this.count_buenas_net * 100 / this.count_buenas;
		}
		
		if(this.count_excelentes > 0){
			this.prom_excelentes_net = this.count_excelentes_net * 100 / this.count_excelentes;
		}
		
	}

	/**
	 * @return the count_malas
	 */
	public int getCount_malas() {
		return count_malas;
	}

	/**
	 * @param count_malas the count_malas to set
	 */
	public void setCount_malas(int count_malas) {
		this.count_malas = count_malas;
	}

	/**
	 * @return the count_buenas
	 */
	public int getCount_buenas() {
		return count_buenas;
	}

	/**
	 * @param count_buenas the count_buenas to set
	 */
	public void setCount_buenas(int count_buenas) {
		this.count_buenas = count_buenas;
	}

	/**
	 * @return the count_excelentes
	 */
	public int getCount_excelentes() {
		return count_excelentes;
	}

	/**
	 * @param count_excelentes the count_excelentes to set
	 */
	public void setCount_excelentes(int count_excelentes) {
		this.count_excelentes = count_excelentes;
	}

	/**
	 * @return the malas
	 */
	public double getMalas() {
		return malas;
	}

	/**
	 * @param malas the malas to set
	 */
	public void setMalas(double malas) {
		this.malas = malas;
	}

	/**
	 * @return the buenas
	 */
	public double getBuenas() {
		return buenas;
	}

	/**
	 * @param buenas the buenas to set
	 */
	public void setBuenas(double buenas) {
		this.buenas = buenas;
	}

	/**
	 * @return the excelentes
	 */
	public double getExcelentes() {
		return excelentes;
	}

	/**
	 * @param excelentes the excelentes to set
	 */
	public void setExcelentes(double excelentes) {
		this.excelentes = excelentes;
	}

	/**
	 * @return the count_error_malas
	 */
	public int getCount_error_malas() {
		return count_error_malas;
	}

	/**
	 * @param count_error_malas the count_error_malas to set
	 */
	public void setCount_error_malas(int count_error_malas) {
		this.count_error_malas = count_error_malas;
	}

	/**
	 * @return the count_error_buenas
	 */
	public int getCount_error_buenas() {
		return count_error_buenas;
	}

	/**
	 * @param count_error_buenas the count_error_buenas to set
	 */
	public void setCount_error_buenas(int count_error_buenas) {
		this.count_error_buenas = count_error_buenas;
	}

	/**
	 * @return the count_error_excelentes
	 */
	public int getCount_error_excelentes() {
		return count_error_excelentes;
	}

	/**
	 * @param count_error_excelentes the count_error_excelentes to set
	 */
	public void setCount_error_excelentes(int count_error_excelentes) {
		this.count_error_excelentes = count_error_excelentes;
	}

	/**
	 * @return the total_error
	 */
	public int getTotal_error() {
		return total_error;
	}

	/**
	 * @param total_error the total_error to set
	 */
	public void setTotal_error(int total_error) {
		this.total_error = total_error;
	}

	/**
	 * @return the prom_error_malas
	 */
	public double getProm_error_malas() {
		return prom_error_malas;
	}

	/**
	 * @param prom_error_malas the prom_error_malas to set
	 */
	public void setProm_error_malas(double prom_error_malas) {
		this.prom_error_malas = prom_error_malas;
	}

	/**
	 * @return the prom_error_buenas
	 */
	public double getProm_error_buenas() {
		return prom_error_buenas;
	}

	/**
	 * @param prom_error_buenas the prom_error_buenas to set
	 */
	public void setProm_error_buenas(double prom_error_buenas) {
		this.prom_error_buenas = prom_error_buenas;
	}

	/**
	 * @return the prom_error_excelentes
	 */
	public double getProm_error_excelentes() {
		return prom_error_excelentes;
	}

	/**
	 * @param prom_error_excelentes the prom_error_excelentes to set
	 */
	public void setProm_error_excelentes(double prom_error_excelentes) {
		this.prom_error_excelentes = prom_error_excelentes;
	}

	/**
	 * @return the count_malas_satelites
	 */
	public int getCount_malas_satelites() {
		return count_malas_satelites;
	}

	/**
	 * @param count_malas_satelites the count_malas_satelites to set
	 */
	public void setCount_malas_satelites(int count_malas_satelites) {
		this.count_malas_satelites = count_malas_satelites;
	}

	/**
	 * @return the count_buenas_satelites
	 */
	public int getCount_buenas_satelites() {
		return count_buenas_satelites;
	}

	/**
	 * @param count_buenas_satelites the count_buenas_satelites to set
	 */
	public void setCount_buenas_satelites(int count_buenas_satelites) {
		this.count_buenas_satelites = count_buenas_satelites;
	}

	/**
	 * @return the count_excelentes_satelites
	 */
	public int getCount_excelentes_satelites() {
		return count_excelentes_satelites;
	}

	/**
	 * @param count_excelentes_satelites the count_excelentes_satelites to set
	 */
	public void setCount_excelentes_satelites(int count_excelentes_satelites) {
		this.count_excelentes_satelites = count_excelentes_satelites;
	}

	/**
	 * @return the total_satelites
	 */
	public int getTotal_satelites() {
		return total_satelites;
	}

	/**
	 * @param total_satelites the total_satelites to set
	 */
	public void setTotal_satelites(int total_satelites) {
		this.total_satelites = total_satelites;
	}

	/**
	 * @return the prom_malas_satelites
	 */
	public double getProm_malas_satelites() {
		return prom_malas_satelites;
	}

	/**
	 * @param prom_malas_satelites the prom_malas_satelites to set
	 */
	public void setProm_malas_satelites(double prom_malas_satelites) {
		this.prom_malas_satelites = prom_malas_satelites;
	}

	/**
	 * @return the prom_buenas_satelites
	 */
	public double getProm_buenas_satelites() {
		return prom_buenas_satelites;
	}

	/**
	 * @param prom_buenas_satelites the prom_buenas_satelites to set
	 */
	public void setProm_buenas_satelites(double prom_buenas_satelites) {
		this.prom_buenas_satelites = prom_buenas_satelites;
	}

	/**
	 * @return the prom_excelentes_satelites
	 */
	public double getProm_excelentes_satelites() {
		return prom_excelentes_satelites;
	}

	/**
	 * @param prom_excelentes_satelites the prom_excelentes_satelites to set
	 */
	public void setProm_excelentes_satelites(double prom_excelentes_satelites) {
		this.prom_excelentes_satelites = prom_excelentes_satelites;
	}

	/**
	 * @return the count_malas_gps
	 */
	public int getCount_malas_gps() {
		return count_malas_gps;
	}

	/**
	 * @param count_malas_gps the count_malas_gps to set
	 */
	public void setCount_malas_gps(int count_malas_gps) {
		this.count_malas_gps = count_malas_gps;
	}

	/**
	 * @return the count_malas_net
	 */
	public int getCount_malas_net() {
		return count_malas_net;
	}

	/**
	 * @param count_malas_net the count_malas_net to set
	 */
	public void setCount_malas_net(int count_malas_net) {
		this.count_malas_net = count_malas_net;
	}

	/**
	 * @return the count_buenas_gps
	 */
	public int getCount_buenas_gps() {
		return count_buenas_gps;
	}

	/**
	 * @param count_buenas_gps the count_buenas_gps to set
	 */
	public void setCount_buenas_gps(int count_buenas_gps) {
		this.count_buenas_gps = count_buenas_gps;
	}

	/**
	 * @return the count_buenas_net
	 */
	public int getCount_buenas_net() {
		return count_buenas_net;
	}

	/**
	 * @param count_buenas_net the count_buenas_net to set
	 */
	public void setCount_buenas_net(int count_buenas_net) {
		this.count_buenas_net = count_buenas_net;
	}

	/**
	 * @return the count_excelentes_gps
	 */
	public int getCount_excelentes_gps() {
		return count_excelentes_gps;
	}

	/**
	 * @param count_excelentes_gps the count_excelentes_gps to set
	 */
	public void setCount_excelentes_gps(int count_excelentes_gps) {
		this.count_excelentes_gps = count_excelentes_gps;
	}

	/**
	 * @return the count_excelentes_net
	 */
	public int getCount_excelentes_net() {
		return count_excelentes_net;
	}

	/**
	 * @param count_excelentes_net the count_excelentes_net to set
	 */
	public void setCount_excelentes_net(int count_excelentes_net) {
		this.count_excelentes_net = count_excelentes_net;
	}

	/**
	 * @return the count_total_veces_gps
	 */
	public int getCount_total_veces_gps() {
		return count_total_veces_gps;
	}

	/**
	 * @param count_total_veces_gps the count_total_veces_gps to set
	 */
	public void setCount_total_veces_gps(int count_total_veces_gps) {
		this.count_total_veces_gps = count_total_veces_gps;
	}

	/**
	 * @return the count_total_veces_net
	 */
	public int getCount_total_veces_net() {
		return count_total_veces_net;
	}

	/**
	 * @param count_total_veces_net the count_total_veces_net to set
	 */
	public void setCount_total_veces_net(int count_total_veces_net) {
		this.count_total_veces_net = count_total_veces_net;
	}

	/**
	 * @return the prom_malas_gps
	 */
	public double getProm_malas_gps() {
		return prom_malas_gps;
	}

	/**
	 * @param prom_malas_gps the prom_malas_gps to set
	 */
	public void setProm_malas_gps(double prom_malas_gps) {
		this.prom_malas_gps = prom_malas_gps;
	}

	/**
	 * @return the prom_malas_net
	 */
	public double getProm_malas_net() {
		return prom_malas_net;
	}

	/**
	 * @param prom_malas_net the prom_malas_net to set
	 */
	public void setProm_malas_net(double prom_malas_net) {
		this.prom_malas_net = prom_malas_net;
	}

	/**
	 * @return the prom_buenas_gps
	 */
	public double getProm_buenas_gps() {
		return prom_buenas_gps;
	}

	/**
	 * @param prom_buenas_gps the prom_buenas_gps to set
	 */
	public void setProm_buenas_gps(double prom_buenas_gps) {
		this.prom_buenas_gps = prom_buenas_gps;
	}

	/**
	 * @return the prom_buenas_net
	 */
	public double getProm_buenas_net() {
		return prom_buenas_net;
	}

	/**
	 * @param prom_buenas_net the prom_buenas_net to set
	 */
	public void setProm_buenas_net(double prom_buenas_net) {
		this.prom_buenas_net = prom_buenas_net;
	}

	/**
	 * @return the prom_excelentes_gps
	 */
	public double getProm_excelentes_gps() {
		return prom_excelentes_gps;
	}

	/**
	 * @param prom_excelentes_gps the prom_excelentes_gps to set
	 */
	public void setProm_excelentes_gps(double prom_excelentes_gps) {
		this.prom_excelentes_gps = prom_excelentes_gps;
	}

	/**
	 * @return the prom_excelentes_net
	 */
	public double getProm_excelentes_net() {
		return prom_excelentes_net;
	}

	/**
	 * @param prom_excelentes_net the prom_excelentes_net to set
	 */
	public void setProm_excelentes_net(double prom_excelentes_net) {
		this.prom_excelentes_net = prom_excelentes_net;
	}	

}
