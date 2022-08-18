package dto;

public class TreningIstorijaDTO {
	private String id;
	private String datumIVremePrijave;
	private String trener;
	private String trening;
	private String objekat;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatumIVremePrijave() {
		return datumIVremePrijave;
	}
	public void setDatumIVremePrijave(String datumIVremePrijave) {
		this.datumIVremePrijave = datumIVremePrijave;
	}
	public String getTrener() {
		return trener;
	}
	public void setTrener(String trener) {
		this.trener = trener;
	}
	public String getTrening() {
		return trening;
	}
	public void setTrening(String trening) {
		this.trening = trening;
	}
	public String getObjekat() {
		return objekat;
	}
	public void setObjekat(String objekat) {
		this.objekat = objekat;
	}
	
	
}
