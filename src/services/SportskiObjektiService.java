package services;

import java.util.List;

import beans.SportskiObjekat;
import data.DataManager;

public class SportskiObjektiService {
	
	public List<SportskiObjekat> getSviSportskiObjekti() {
		return DataManager.data.getSportskiObjekti();
	}
}
