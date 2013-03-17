/**
 * 
 */
package com.test.geo.optimal.library;

/**
 * @author LUSTER
 *
 */
public class Muestra {
	
	public final int MALA = 1;
	public final int BUENA = 2;
	public final int EXCELENTE = 3;
	
	private String latitude;
	private String longuitude;
	private int calificacion;
	private String descripcion;
	private String pathImagen;

	/**
	 * @param latitude
	 * @param longuitude
	 * @param calificacion
	 * @param descripcion
	 * @param pathImagen
	 */
	public Muestra(String latitude, String longuitude,
			int calificacion, String descripcion, String pathImagen) {
		super();
		this.latitude = latitude;
		this.longuitude = longuitude;
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

}
