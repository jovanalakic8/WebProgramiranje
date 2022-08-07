package services;

import java.util.List;

import beans.Trening;
import data.DataManager;

public class TreningService {
	
	public List<Trening> getSviTreninzi() {
		return DataManager.data.getTreninzi();
	}
	
	public Trening dodajTrening(Trening trening) {
		DataManager.data.getTreninzi().add(trening);
		DataManager.saveData();
		return trening;
	}
	
}
