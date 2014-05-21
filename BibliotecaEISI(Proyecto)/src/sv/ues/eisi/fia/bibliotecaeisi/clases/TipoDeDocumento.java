package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class TipoDeDocumento {

	private int idTipoDocumento;
	private String nombreDocumento;
	
	public TipoDeDocumento(){
		
	}
	public TipoDeDocumento(int idTipoDocumento, String nombreDocumento) {
		this.idTipoDocumento = idTipoDocumento;
		this.nombreDocumento = nombreDocumento;
	}
	
	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
}
