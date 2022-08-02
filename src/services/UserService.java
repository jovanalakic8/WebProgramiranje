package services;

import java.util.ArrayList;
import java.util.List;

import beans.User;
import data.DataManager;
import dto.RegistracijaDTO;
import dto.UserWithoutCredentialsDTO;

public class UserService {
	
	public List<UserWithoutCredentialsDTO> sviKorisnici() {
		List<UserWithoutCredentialsDTO> dtos = new ArrayList();
		for (User user : DataManager.data.getKorisnici()) {
			dtos.add(new UserWithoutCredentialsDTO(user.getName(), user.getLastName(), 
					 user.getSex(), user.getBirthDate(), user.getRole()));
		}
		
		return dtos;
	}
	
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
	
	public RegistracijaDTO azuriranjeKorisnika(String korisnickoIme, RegistracijaDTO input) {
		List<User> korisnici = (List<User>) DataManager.data.getKorisnici();
	    for (User korisnik : korisnici) {
	    	if (korisnik.getUserName().equals(korisnickoIme)) {
	    		korisnik.setName(input.getIme());
	    		korisnik.setLastName(input.getPrezime());
	    		korisnik.setBirthDate(input.getDatumRodjenja());
	    		korisnik.setSex(input.getPol());
	    	}
        }
	    
        DataManager.saveData();
        
	    return input;
	}
}
