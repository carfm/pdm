package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class DetallePrestamo {

	private int idDocumento;
	private int numeroPrestamo;
	private Integer idDetallePrestamo;

	public DetallePrestamo() {
		// TODO Auto-generated constructor stub
	}

	public DetallePrestamo(int idDocumento, int numeroPrestamo,
			Integer idDetallePrestamo) {
		this.idDocumento = idDocumento;
		this.numeroPrestamo = numeroPrestamo;
		this.idDetallePrestamo = idDetallePrestamo;
	}

	public Integer getIdDetallePrestamo() {
		return idDetallePrestamo;
	}

	public void setIdDetallePrestamo(Integer idDetallePrestamo) {
		this.idDetallePrestamo = idDetallePrestamo;
	}

	public int getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}

	public int getIdPrestamo() {
		return numeroPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.numeroPrestamo = idPrestamo;
	}
}
