package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import beans.Clanarina;
import beans.ClanarinaPonuda;
import beans.SportskiObjekat;
import beans.Trening;
import beans.TreningIstorija;
import beans.User;

public class Data {
	
	private List<User> korisnici = new ArrayList<User>();
	private List<SportskiObjekat> sportskiObjekti = new ArrayList<SportskiObjekat>();
	private List<Trening> treninzi = new ArrayList<Trening>();
	private List<ClanarinaPonuda> ponudaClanarina = new ArrayList<ClanarinaPonuda>();
	private List<Clanarina> clanarine = new ArrayList<Clanarina>();
	private List<TreningIstorija> istorijaTreninga = new ArrayList<TreningIstorija>();

	public Collection<User> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<User> korisnici) {
		this.korisnici = korisnici;
	}

	public List<SportskiObjekat> getSportskiObjekti() {
		return sportskiObjekti;
	}

	public void setSportskiObjekti(List<SportskiObjekat> sportskiObjekti) {
		this.sportskiObjekti = sportskiObjekti;
	}	
	
	public List<Trening> getTreninzi() {
		return treninzi;
	}

	public void setTreninzi(List<Trening> treninzi) {
		this.treninzi = treninzi;
	}

	public List<ClanarinaPonuda> getPonudaClanarina() {
		return ponudaClanarina;
	}

	public void setPonudaClanarina(List<ClanarinaPonuda> ponudaClanarina) {
		this.ponudaClanarina = ponudaClanarina;
	}

	public List<Clanarina> getClanarine() {
		return clanarine;
	}

	public void setClanarine(List<Clanarina> clanarine) {
		this.clanarine = clanarine;
	}

	public List<TreningIstorija> getIstorijaTreninga() {
		return istorijaTreninga;
	}

	public void setIstorijaTreninga(List<TreningIstorija> istorijaTreninga) {
		this.istorijaTreninga = istorijaTreninga;
	}
	
}
