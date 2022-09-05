package services;

import java.util.ArrayList;
import java.util.List;

import beans.SportskiObjekat;
import data.DataManager;

public class SportskiObjektiService {
	
	public List<SportskiObjekat> getSviSportskiObjekti() {
		List<SportskiObjekat> neizbrisaniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat sportskiObjekat: DataManager.data.getSportskiObjekti()) {
			if (!sportskiObjekat.isBrisanjeLogicko()) {
				neizbrisaniObjekti.add(sportskiObjekat);
			}
		}
		return neizbrisaniObjekti;
	}
	
	public SportskiObjekat getSportskiObjekatPoId(String id) {
		for (SportskiObjekat so : DataManager.data.getSportskiObjekti()) {
			if (so.getId().equals(id)) {
				return so;
			}
		}
		
		return null;
	}
	
	public SportskiObjekat dodajSportskiObjekat(SportskiObjekat sportskiObjekat) {
		DataManager.data.getSportskiObjekti().add(sportskiObjekat);
		DataManager.saveData();
		return sportskiObjekat;
	}
	
	public void obrisiObjekat(String sportskiObjekatId) {
		for (SportskiObjekat sportskiObjekat : getSviSportskiObjekti()) {
			if (sportskiObjekat.getId().equals(sportskiObjekatId)) {
				sportskiObjekat.setBrisanjeLogicko(true);
			}
		}
		
		DataManager.saveData();
	}
}
