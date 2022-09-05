package beans;

import utils.TreningTipEnum;

public class Trening {
	
	private String id;
	private String naziv;
	private TreningTipEnum tip;
	private int trajanjeUMinutima;
	private String trenerId;
	private String opis;
	private String slika;
	private String objekatId;
	private String datumIVremeOdrzavanja;
	private String kupacId;
	private boolean otkazan;
	private int cena;
	private boolean brisanjeLogicko;
	
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
	public TreningTipEnum getTip() {
		return tip;
	}
	public void setTip(TreningTipEnum tip) {
		this.tip = tip;
	}
	public int getTrajanjeUMinutima() {
		return trajanjeUMinutima;
	}
	public void setTrajanjeUMinutima(int trajanjeUMinutima) {
		this.trajanjeUMinutima = trajanjeUMinutima;
	}
	public String getTrenerId() {
		return trenerId;
	}
	public void setTrenerId(String trenerId) {
		this.trenerId = trenerId;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
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
	public boolean isBrisanjeLogicko() {
		return brisanjeLogicko;
	}
	public void setBrisanjeLogicko(boolean brisanjeLogicko) {
		this.brisanjeLogicko = brisanjeLogicko;
	}
	
	
}
