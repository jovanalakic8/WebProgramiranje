package beans;

public class Lokacija {
	
	private int geografskaSirina;
	private int geografskaDuzina;
	private String adresa;
	
	public Lokacija( ) {}
	
	public Lokacija(int geografskaSirina, int geografskaDuzina, String adresa) {
		super();
		this.geografskaSirina = geografskaSirina;
		this.geografskaDuzina = geografskaDuzina;
		this.adresa = adresa;
	}

	public int getGeografskaSirina() {
		return geografskaSirina;
	}

	public void setGeografskaSirina(int geografskaSirina) {
		this.geografskaSirina = geografskaSirina;
	}

	public int getGeografskaDuzina() {
		return geografskaDuzina;
	}

	public void setGeografskaDuzina(int geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
}
