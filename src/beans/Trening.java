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
	
	
}
