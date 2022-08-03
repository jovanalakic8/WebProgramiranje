package dto;

import beans.Lokacija;

public class NoviObjekatDTO {
	
	private String naziv;
	private String tip;
	private String ulica;
	private String broj;
	private String mesto;
	private String zip;
	private String gSirina;
	private String gDuzina;
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tipObjekta) {
		this.tip = tipObjekta;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getBroj() {
		return broj;
	}
	public void setBroj(String broj) {
		this.broj = broj;
	}
	public String getMesto() {
		return mesto;
	}
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	public String getgSirina() {
		return gSirina;
	}
	public void setgSirina(String gSirina) {
		this.gSirina = gSirina;
	}
	public String getgDuzina() {
		return gDuzina;
	}
	public void setgDuzina(String gDuzina) {
		this.gDuzina = gDuzina;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
