package dto;

import java.time.LocalDateTime;

import utils.ClanarinaTip;

public class ClanarinaPonudaDTO {
	private String id;
	private String naziv;
	private ClanarinaTip tip;
	private long cena;
	private String vaziDo;
	private int brojTermina;
	
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
	public String getVaziDo() {
		return vaziDo;
	}
	public void setVaziDo(String vaziDo) {
		this.vaziDo = vaziDo;
	}
	public int getBrojTermina() {
		return brojTermina;
	}
	public void setBrojTermina(int brojTermina) {
		this.brojTermina = brojTermina;
	}
}
