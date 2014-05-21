package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Autor {
	Integer idAutor;
	String nombre;
	String apellido;
	String codigoPais;
	
	
	
	
	
	
	

		
	public Autor(){
		
	}
	
	
	public Autor(Integer idAutor, String nombre, String apellido,
			String codigoPais) {
		super();
		this.idAutor = idAutor;
		this.nombre = nombre;
		this.apellido = apellido;
		this.codigoPais = codigoPais;
		
	}
	public Integer getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	

}
