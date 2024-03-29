package services;

import java.util.ArrayList;
import java.util.List;

import beans.User;
import data.DataManager;
import dto.RegistracijaDTO;
import dto.UserWithoutCredentialsDTO;
import utils.UserType;

public class UserService {
	
	public List<UserWithoutCredentialsDTO> sviKorisnici() {
		DataManager.readData();
		List<UserWithoutCredentialsDTO> dtos = new ArrayList<UserWithoutCredentialsDTO>();
		for (User user : DataManager.data.getKorisnici()) {
			dtos.add(new UserWithoutCredentialsDTO(user.getName(), user.getLastName(), 
					 user.getUserName(), user.getSex(), user.getBirthDate(), user.getRole(), user.getType().toString(), user.getNumberOfPoints()));
		}
		
		return dtos;
	}
	
	public List<UserWithoutCredentialsDTO> menadzeriBezObjekta() {
		DataManager.readData();
		List<UserWithoutCredentialsDTO> dtos = new ArrayList<UserWithoutCredentialsDTO>();
		for (User user : DataManager.data.getKorisnici()) {
			if (user.getRole().toLowerCase().equals("menadzer") && user.getManagedSportObjectId() == null) {
				dtos.add(new UserWithoutCredentialsDTO(user.getName(), user.getLastName(), 
						 user.getUserName(), user.getSex(), user.getBirthDate(), user.getRole(), user.getType().toString(), user.getNumberOfPoints()));
			}
		}
		
		return dtos;
	}
	
	public User login(String korisnickoIme, String lozinka) throws Exception {
		DataManager.readData();
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
		DataManager.readData();
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
		DataManager.readData();
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
	
	public List<UserWithoutCredentialsDTO> sviTreneri() {
		List<UserWithoutCredentialsDTO> dtos = new ArrayList<UserWithoutCredentialsDTO>();
		for (User user : DataManager.data.getKorisnici()) {
			if (user.getRole().toLowerCase().equals("trener")) {
				dtos.add(new UserWithoutCredentialsDTO(user.getName(), user.getLastName(), 
						user.getUserName(), user.getSex(), user.getBirthDate(), user.getRole(), null, 0));				
			}
		}
		
		return dtos;
	}
	
	public List<UserWithoutCredentialsDTO> sviKupci() {
		DataManager.readData();
		List<UserWithoutCredentialsDTO> dtos = new ArrayList<UserWithoutCredentialsDTO>();
		for (User user : DataManager.data.getKorisnici()) {
			if (user.getRole().toLowerCase().equals("kupac")) {
				dtos.add(new UserWithoutCredentialsDTO(user.getName(), user.getLastName(), 
						user.getUserName(), user.getSex(), user.getBirthDate(), user.getRole(), null, 0));				
			}
		}
		
		return dtos;
	}
	
	public String getObjekatZaMenadzera(String menadzerUserName) {
		DataManager.readData();
		for (User user : DataManager.data.getKorisnici()) {
			if (user.getUserName().equals(menadzerUserName)) {
				return user.getManagedSportObjectId();			
			}
		}
		
		return null;
	}
	
	public User getPoUsername(String username) {
		DataManager.readData();
		for (User user : DataManager.data.getKorisnici()) {
			if (user.getUserName().equals(username)) {
				return user;			
			}
		}
		
		return null;
	}

	public void azurirajTipKorisnika(User user, int brojBodova) {
		DataManager.readData();
		for (User u : DataManager.data.getKorisnici()) {
			if (u.getUserName().equals(user.getUserName())) {
				int noviBrojBodova = u.getNumberOfPoints() + brojBodova;
				if (noviBrojBodova < 0) {
					noviBrojBodova = 0;
				}
				
				u.setNumberOfPoints(noviBrojBodova);
				if (noviBrojBodova < 100) {
					u.setType(UserType.BRONZANI);
				} else if (noviBrojBodova < 200) {
					u.setType(UserType.SREBRNI);
				} else {
					u.setType(UserType.ZLATNI);
				}
			}
		}
		
		DataManager.saveData();
	}
	
	public void azurirajMenadzera(String username, String noviObjekat) {
		DataManager.readData();
		for (User u : DataManager.data.getKorisnici()) {
			if (u.getUserName().equals(username)) {
				u.setManagedSportObject(noviObjekat);
			}
		}
		
		DataManager.saveData();
	}
	
	public void obrisiSportskiObjekatZaMenadzera(String objekatId) {
		DataManager.readData();
		for (User u : DataManager.data.getKorisnici()) {
			if (u.getManagedSportObjectId() != null &&   u.getManagedSportObjectId().equals(objekatId)) {
				u.setManagedSportObject(null);
			}
		}
		
		DataManager.saveData();
	}
}
