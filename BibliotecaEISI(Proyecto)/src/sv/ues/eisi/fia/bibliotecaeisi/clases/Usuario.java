package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Usuario {
	
	private String idUsuario;
	private String nombreUsuario;
	private String apellidoUsuario;
	private String contrasenia;
	private int activo;
	private String tipo;
	
	public Usuario(){
		
	}

	public Usuario(String idUsuario, String nombreUsuario, String apellidoUsuario, String contrasenia, int activo, String tipo) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellidoUsuario = apellidoUsuario;
		this.contrasenia = contrasenia;
		this.activo = activo;
		this.tipo = tipo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
