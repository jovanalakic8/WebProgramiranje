package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import beans.SportskiObjekat;
import beans.User;

public class Data {
	
	private List<User> korisnici = new ArrayList<User>();
	private List<SportskiObjekat> sportskiObjekti = new ArrayList<SportskiObjekat>();

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
	
}
