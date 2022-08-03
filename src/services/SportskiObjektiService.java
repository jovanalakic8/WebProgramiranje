package services;

import java.util.List;

import beans.SportskiObjekat;
import data.DataManager;

public class SportskiObjektiService {
	
	public List<SportskiObjekat> getSviSportskiObjekti() {
		return DataManager.data.getSportskiObjekti();
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
}
