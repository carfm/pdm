package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Area {

	Integer idArea;
	String nombreArea;
	String descripcion;
	
	public Area(){
		
	}
	
	public Area(Integer idArea, String nombreArea, String descripcion) {
		super();
		this.idArea = idArea;
		this.nombreArea = nombreArea;
		this.descripcion = descripcion;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public String getNombreArea() {
		return nombreArea;
	}
	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
