package beans;

import utils.ClanarinaTip;

public class ClanarinaPonuda {
	
	private String naziv;
	private ClanarinaTip tip;
	private long cena;
	private int brojDanaVazenja;
	private int brojUlazakaDnevno;
	
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
	public int getBrojDanaVazenja() {
		return brojDanaVazenja;
	}
	public void setBrojDanaVazenja(int brojDanaVazenja) {
		this.brojDanaVazenja = brojDanaVazenja;
	}
	public int getBrojUlazakaDnevno() {
		return brojUlazakaDnevno;
	}
	public void setBrojUlazakaDnevno(int brojUlazakaDnevno) {
		this.brojUlazakaDnevno = brojUlazakaDnevno;
	}
	
	
}
