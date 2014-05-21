package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Prestamo {

	Integer NumPrestamo;
	String idUsuario;
	Integer idSecretaria;
	Integer idPenalizacion;
	String fechaPrestamo;
	String fechaEntrega;
	Integer CantidadLibros;

	public Prestamo() {

	}

	public Prestamo(Integer numPrestamo, String idUsuario,
			Integer idSecretaria, Integer idPenalizacion, String fechaPrestamo,
			String fechaEntrega, Integer cantidadLibros) {
		super();
		NumPrestamo = numPrestamo;
		this.idUsuario = idUsuario;
		this.idSecretaria = idSecretaria;
		this.idPenalizacion = idPenalizacion;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaEntrega = fechaEntrega;
		CantidadLibros = cantidadLibros;
	}

	public Integer getNumPrestamo() {
		return NumPrestamo;
	}

	public void setNumPrestamo(Integer numPrestamo) {
		NumPrestamo = numPrestamo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdSecretaria() {
		return idSecretaria;
	}

	public void setIdSecretaria(Integer idSecretaria) {
		this.idSecretaria = idSecretaria;
	}

	public Integer getIdPenalizacion() {
		return idPenalizacion;
	}

	public void setIdPenalizacion(Integer idPenalizacion) {
		this.idPenalizacion = idPenalizacion;
	}

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Integer getCantidadLibros() {
		return CantidadLibros;
	}

	public void setCantidadLibros(Integer cantidadLibros) {
		CantidadLibros = cantidadLibros;
	}

	Integer aprobado;

	public Integer getAprobado() {
		return aprobado;
	}

	public void setAprobado(Integer aprobado) {
		this.aprobado = aprobado;
	}

}
