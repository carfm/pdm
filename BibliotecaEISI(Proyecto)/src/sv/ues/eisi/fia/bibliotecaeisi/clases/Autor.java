package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Autor {
	Integer idautor;
	String nombreautor;
	String apellidoautor;
	String codigopais;
	
		
	public Autor(){
		
	}
	
	
	public Autor(Integer idAutor, String nombre, String apellido,
			String codigoPais) {
		super();
		this.idautor = idAutor;
		this.nombreautor = nombre;
		this.apellidoautor = apellido;
		this.codigopais = codigoPais;
		
	}
	public Integer getIdAutor() {
		return idautor;
	}
	public void setIdAutor(Integer idAutor) {
		this.idautor = idAutor;
	}
	public String getNombre() {
		return nombreautor;
	}
	public void setNombre(String nombre) {
		this.nombreautor = nombre;
	}
	public String getApellido() {
		return apellidoautor;
	}
	public void setApellido(String apellido) {
		this.apellidoautor = apellido;
	}
	public String getCodigoPais() {
		return codigopais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigopais = codigoPais;
	}
	

}
