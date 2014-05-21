package sv.ues.eisi.fia.bibliotecaeisi.clases;

public class Penalizacion {

	Integer idPenalizacion;
	String nombrePenaliza;
	String descricionPenaliza;
	Integer diasPenalizacion;
	
	
	public Penalizacion() {
		
	}


	public Penalizacion(Integer idPenalizacion, String nombrePenaliza,
			String descricionPenaliza, Integer diasPenalizacion) {
		super();
		this.idPenalizacion = idPenalizacion;
		this.nombrePenaliza = nombrePenaliza;
		this.descricionPenaliza = descricionPenaliza;
		this.diasPenalizacion = diasPenalizacion;
	}


	public Integer getIdPenalizacion() {
		return idPenalizacion;
	}


	public void setIdPenalizacion(Integer idPenalizacion) {
		this.idPenalizacion = idPenalizacion;
	}


	public String getNombrePenaliza() {
		return nombrePenaliza;
	}


	public void setNombrePenaliza(String nombrePenaliza) {
		this.nombrePenaliza = nombrePenaliza;
	}


	public String getDescricionPenaliza() {
		return descricionPenaliza;
	}


	public void setDescricionPenaliza(String descricionPenaliza) {
		this.descricionPenaliza = descricionPenaliza;
	}


	public Integer getDiasPenalizacion() {
		return diasPenalizacion;
	}


	public void setDiasPenalizacion(Integer diasPenalizacion) {
		this.diasPenalizacion = diasPenalizacion;
	}
	
	
	
	
	
}
