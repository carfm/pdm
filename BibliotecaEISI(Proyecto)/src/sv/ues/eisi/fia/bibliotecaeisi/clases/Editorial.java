package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Editorial {

	private int idEditorial;
	private String nombreEditorial;
	private String codigoPais;

	public Editorial() {
		// TODO Auto-generated constructor stub
	}

	public Editorial(int idEditorial, String nombreEditorial) {
		this.idEditorial = idEditorial;
		this.nombreEditorial = nombreEditorial;
	}

	public int getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(int idEditorial) {
		this.idEditorial = idEditorial;
	}

	public String getNombreEditorial() {
		return nombreEditorial;
	}

	public void setNombreEditorial(String nombreEditorial) {
		this.nombreEditorial = nombreEditorial;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String idPais) {
		this.codigoPais = idPais;
	}

}
