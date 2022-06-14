package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import beans.User;

public class Data {
	
	private List<User> korisnici = new ArrayList<User>();

	public Collection<User> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<User> korisnici) {
		this.korisnici = korisnici;
	}	
	
}
