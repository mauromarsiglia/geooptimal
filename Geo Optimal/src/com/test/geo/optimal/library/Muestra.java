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
	private String precision;
	private String providerName;
	private int calificacion;	
	private String descripcion;
	private String pathImagen;

	/**
	 * @param latitude
	 * @param longuitude
	 * @param precision
	 * @param calificacion
	 * @param descripcion
	 * @param pathImagen
	 */
	public Muestra(String latitude, String longuitude, String precision, String providerName,
			int calificacion, String descripcion, String pathImagen) {
		super();
		this.latitude = latitude;
		this.longuitude = longuitude;
		this.precision = precision;
		this.providerName = providerName;
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

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

}
