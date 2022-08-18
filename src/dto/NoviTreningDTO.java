package dto;

import beans.User;

public class NoviTreningDTO {
	
	private String naziv;
	private String tip;
	private String opis;
	private int trajanje;
	private User trener;
	private String slikaURL;
	private String objekatId;
	private String datumIVremeOdrzavanja;
	private String kupacId;
	
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public int getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	public User getTrener() {
		return trener;
	}
	public void setTrener(User trener) {
		this.trener = trener;
	}
	public String getSlikaURL() {
		return slikaURL;
	}
	public void setSlikaURL(String slikaURL) {
		this.slikaURL = slikaURL;
	}
	public String getObjekatId() {
		return objekatId;
	}
	public void setObjekatId(String objekatId) {
		this.objekatId = objekatId;
	}
	public String getDatumIVremeOdrzavanja() {
		return datumIVremeOdrzavanja;
	}
	public void setDatumIVremeOdrzavanja(String datumIVremeOdrzavanja) {
		this.datumIVremeOdrzavanja = datumIVremeOdrzavanja;
	}
	public String getKupacId() {
		return kupacId;
	}
	public void setKupacId(String kupacId) {
		this.kupacId = kupacId;
	}
	
	
}
