package services;

import java.util.List;

import beans.User;
import data.DataManager;
import dto.RegistracijaDTO;

public class UserService {
	
	public User login(String korisnickoIme, String lozinka) throws Exception {
		List<User> korisnici = (List<User>) DataManager.data.getKorisnici();
		for (User korisnik : korisnici) {
			if (korisnik.getUserName().contains(korisnickoIme)) {
				if (korisnik.getPassword().equals(lozinka)) return korisnik;
		        else throw new Exception("Korisnicko ime ili lozinka su pogresni!");
			}
		}

	    throw new Exception("Korisnik nije pronadjen!");
    }
	
	public RegistracijaDTO registracijaKorisnika(RegistracijaDTO input) throws Exception {
		List<User> korisnici = (List<User>) DataManager.data.getKorisnici();
	    String korisnickoIme = input.getKorisnickoIme();
	    for (User korisnik : korisnici) {
	    	if (korisnik.getUserName().equals(korisnickoIme)) {
	    		throw new Exception("Korisnik sa datim korisnickim imenom vec postoji!");
	    	}
        }

	    User novi = new User(input.getKorisnickoIme(), input.getLozinka(), input.getIme(),
	    		input.getPrezime(), input.getPol(), input.getDatumRodjenja(), input.getUloga());
	    
        DataManager.data.getKorisnici().add(novi);
        DataManager.saveData();
        
	    return input;
    }
}
