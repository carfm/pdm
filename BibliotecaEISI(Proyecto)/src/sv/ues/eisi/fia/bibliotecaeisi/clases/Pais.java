package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Pais {

	private String codigopais;
	private String nombrepais;
	
	
	public Pais() {
		// TODO Auto-generated constructor stub
	}
	public Pais(String codigoPais, String nombrePais) {
		this.codigopais = codigoPais;
		this.nombrepais = nombrePais;
	}
	@Override
	public String toString() {
		return nombrepais;
	}
	public String getCodigoPais() {
		return codigopais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigopais = codigoPais;
	}
	public String getNombrePais() {
		return nombrepais;
	}
	public void setNombrePais(String nombrePais) {
		this.nombrepais = nombrePais;
	}
	
	
}
