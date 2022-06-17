package beans;

public class SportskiObjekat {
	private String naziv;
	private String tipObjekta;
	private String sadrzaj;
	private String status;
	private Lokacija lokacija;
	private String logo;
	private float prosecnaOcena;
	private String radnoVreme;
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTipObjekta() {
		return tipObjekta;
	}
	public void setTipObjekta(String tipObjekta) {
		this.tipObjekta = tipObjekta;
	}
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public float getProsecnaOcena() {
		return prosecnaOcena;
	}
	public void setProsecnaOcena(float prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
	public String getRadnoVreme() {
		return radnoVreme;
	}
	public void setRadnoVreme(String radnoVreme) {
		this.radnoVreme = radnoVreme;
	}
	
}
