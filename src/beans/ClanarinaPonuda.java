package beans;

import utils.ClanarinaTip;

public class ClanarinaPonuda {
	
	private String id;
	private String naziv;
	private ClanarinaTip tip;
	private long cena;
	private Integer brojDanaVazenja;
	private Integer brojTermina;
	
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
	public ClanarinaTip getTip() {
		return tip;
	}
	public void setTip(ClanarinaTip tip) {
		this.tip = tip;
	}
	public long getCena() {
		return cena;
	}
	public void setCena(long cena) {
		this.cena = cena;
	}
	public Integer getBrojDanaVazenja() {
		return brojDanaVazenja;
	}
	public void setBrojDanaVazenja(Integer brojDanaVazenja) {
		this.brojDanaVazenja = brojDanaVazenja;
	}
	public Integer getBrojTermina() {
		return brojTermina;
	}
	public void setBrojTermina(Integer brojTermina) {
		this.brojTermina = brojTermina;
	}
	
	
}
