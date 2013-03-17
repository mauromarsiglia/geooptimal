/**
 * 
 */
package com.test.geo.optimal.library;

/**
 * @author LUSTER
 *
 */
public class Muestra {
	
	public static final int MALA = 1;
	public static final int BUENA = 2;
	public static final int EXCELENTE = 3;
	
	private String latitude;
	private String longuitude;
	private double precision;
	private String providerName;
	private int numeroSatelites;
	private int calificacion;	
	private String descripcion;
	private String pathImagen;

	/**
	 * 
	 * @param latitude
	 * @param longuitude
	 * @param precision
	 * @param providerName
	 * @param numeroSatelites
	 * @param calificacion
	 * @param descripcion
	 * @param pathImagen
	 */
	public Muestra(String latitude, String longuitude, double precision, String providerName, int numeroSatelites,
			int calificacion, String descripcion, String pathImagen) {
		super();
		this.latitude = latitude;
		this.longuitude = longuitude;
		this.precision = precision;
		this.providerName = providerName;
		this.numeroSatelites = numeroSatelites;
		this.calificacion = calificacion;
		this.descripcion = descripcion;
		this.pathImagen = pathImagen;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLonguitude() {
		return longuitude;
	}
	public void setLonguitude(String longuitude) {
		this.longuitude = longuitude;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPathImagen() {
		return pathImagen;
	}
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public int getNumeroSatelites() {
		return numeroSatelites;
	}

	public void setNumeroSatelites(int numeroSatelites) {
		this.numeroSatelites = numeroSatelites;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

}
