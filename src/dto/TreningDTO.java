package dto;

import beans.User;

public class TreningDTO {
	
	private String id;
	private String naziv;
	private String tip;
	private String opis;
	private int trajanje;
	private String trener;
	private String slikaURL;
	private String objekatId;
	private String datumIVremeOdrzavanja;
	private String kupac;
	private boolean otkazan;
	private int cena;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getTrener() {
		return trener;
	}
	public void setTrener(String trenerId) {
		this.trener = trenerId;
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
	public String getKupac() {
		return kupac;
	}
	public void setKupac(String kupac) {
		this.kupac = kupac;
	}
	public boolean isOtkazan() {
		return otkazan;
	}
	public void setOtkazan(boolean otkazan) {
		this.otkazan = otkazan;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	
}
