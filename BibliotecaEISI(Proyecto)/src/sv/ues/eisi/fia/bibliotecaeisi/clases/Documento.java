package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Documento {
	
	private int iddocumento;
	private int ideditorial;
	private int idtipodocumento;
	private String tema;
	private String descripcion;
	private String anio;
	private String numeropagina;
	private int cantidaddisponible;
	private int edicion;
	
	public Documento(){
		
	}
	
	public Documento(int idDocumento, int idEditorial, int idTipoDocumento,	String tema, String descripcion, String anio, String numeroPagina, int cantidadDisponible, int edicion) {
		this.iddocumento = idDocumento;
		this.ideditorial = idEditorial;
		this.idtipodocumento = idTipoDocumento;
		this.tema = tema;
		this.descripcion = descripcion;
		this.anio = anio;
		this.numeropagina = numeroPagina;
		this.cantidaddisponible = cantidadDisponible;
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
		return iddocumento;
	}
	public void setIdDocumento(int idDocumento) {
		this.iddocumento = idDocumento;
	}
	public int getIdEditorial() {
		return ideditorial;
	}
	public void setIdEditorial(int idEditorial) {
		this.ideditorial = idEditorial;
	}
	public int getIdTipoDocumento() {
		return idtipodocumento;
	}
	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idtipodocumento = idTipoDocumento;
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
		return numeropagina;
	}
	public void setNumeroPagina(String numeroPagina) {
		this.numeropagina = numeroPagina;
	}
	public int getCantidadDisponible() {
		return cantidaddisponible;
	}
	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidaddisponible = cantidadDisponible;
	}
	public int getEdicion() {
		return edicion;
	}
	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}

}
