/**
 * 
 */
package com.test.geo.optimal.controller;

import java.util.List;

import com.test.geo.optimal.library.Muestra;

/**
 * @author LUSTER
 *
 */
public class Statistical {
	
	private int count_malas;
	private int count_buenas;
	private int count_excelentes;
	
	private double malas;
	private double buenas;
	private double excelentes;
	
	private double error_malas;
	private double error_buenas;
	private double error_excelentes;
	
	private double error_malas_size;
	private double error_buenas_size;
	private double error_excelentes_size;
	
	
	
	private static Statistical instance;
	
	public static Statistical getInstance(){
		if(instance == null){
			instance = new Statistical();
		}
		return instance;
	}
	
	public void saveEstadistica(List<Muestra> listaMuestras){
		
		int malas = 0;
		int buenas = 0;
		int excelentes = 0;		
		
		int malasError = 0;
		int buenasError = 0;
		int excelenteError = 0;
		int totalError = 0;
		
		for(Muestra item : listaMuestras){
			if(item.getCalificacion() == Muestra.MALA){
				malas++;
				malasError += item.getPrecision();
			}else if(item.getCalificacion() == Muestra.BUENA){
				buenas++;
				buenasError += item.getPrecision();
			}else{
				excelentes++;
				excelenteError += item.getPrecision();
			}
			
			totalError += item.getPrecision();
			
		}
		
		int size = listaMuestras.size();
		
		//porcentajes muestra
		this.malas = malas / size;
		this.buenas = buenas / size;
		this.excelentes = excelentes / size;
		
		//porcentaje de error
		
		
	}
	

}
