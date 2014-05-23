package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Documento {
	
	private int idDocumento;
	private int idEditorial;
	private int idTipoDocumento;
	private String tema;
	private String descripcion;
	private String anio;
	private String numeroPagina;
	private int cantidadDisponible;
	private int edicion;
	
	public Documento(){
		
	}
	
	public Documento(int idDocumento, int idEditorial, int idTipoDocumento,	String tema, String descripcion, String anio, String numeroPagina, int cantidadDisponible, int edicion) {
		this.idDocumento = idDocumento;
		this.idEditorial = idEditorial;
		this.idTipoDocumento = idTipoDocumento;
		this.tema = tema;
		this.descripcion = descripcion;
		this.anio = anio;
		this.numeroPagina = numeroPagina;
		this.cantidadDisponible = cantidadDisponible;
		this.edicion = edicion;
	}
	
	@Override
	public String toString() {
		String tipo;
		if(getIdTipoDocumento()==1){
			tipo="LIBRO";
		}else{
			tipo="TESIS";
		}
		return "Tema o Titulo: "+tema+" \nTipo de Documento: "+tipo;
	}
	
	public int getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	public int getIdEditorial() {
		return idEditorial;
	}
	public void setIdEditorial(int idEditorial) {
		this.idEditorial = idEditorial;
	}
	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNumeroPagina() {
		return numeroPagina;
	}
	public void setNumeroPagina(String numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	public int getCantidadDisponible() {
		return cantidadDisponible;
	}
	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}
	public int getEdicion() {
		return edicion;
	}
	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}

}
