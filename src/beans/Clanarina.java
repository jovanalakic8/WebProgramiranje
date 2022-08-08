package beans;

import java.time.LocalDateTime;

import utils.ClanarinaStatus;
import utils.ClanarinaTip;

public class Clanarina {
	private String id;
	private ClanarinaTip tip;
	private ClanarinaStatus status;
	private String vaziDo;
	private String datumPlacanja;
	private int brojPreostalihTermina;
	private int ukupanBrojTermina;
	private double cena;
	private User kupac;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ClanarinaTip getTip() {
		return tip;
	}
	public void setTip(ClanarinaTip tip) {
		this.tip = tip;
	}
	public ClanarinaStatus getStatus() {
		return status;
	}
	public void setStatus(ClanarinaStatus status) {
		this.status = status;
	}
	public String getVaziDo() {
		return vaziDo;
	}
	public void setVaziDo(String vaziDo) {
		this.vaziDo = vaziDo;
	}
	public String getDatumPlacanja() {
		return datumPlacanja;
	}
	public void setDatumPlacanja(String datumPlacanja) {
		this.datumPlacanja = datumPlacanja;
	}
	public int getBrojPreostalihTermina() {
		return brojPreostalihTermina;
	}
	public void setBrojPreostalihTermina(int brojPreostalihTermina) {
		this.brojPreostalihTermina = brojPreostalihTermina;
	}
	public int getUkupanBrojTermina() {
		return ukupanBrojTermina;
	}
	public void setUkupanBrojTermina(int ukupanBrojTermina) {
		this.ukupanBrojTermina = ukupanBrojTermina;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public User getKupac() {
		return kupac;
	}
	public void setKupac(User kupac) {
		this.kupac = kupac;
	}
	
}
