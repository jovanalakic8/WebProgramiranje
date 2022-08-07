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

	public Trening getTreningPoId(String treningId) {
		for (Trening tr : getSviTreninzi()) {
			if (tr.getId().equals(treningId)) {
				return tr;
			}
		}
		
		return null;
	}

	public void azurirajTrening(Trening trening) {
		for (Trening tr : getSviTreninzi()) {
			if (tr.getId().equals(trening.getId())) {
				tr.setNaziv(trening.getNaziv());
				tr.setOpis(trening.getOpis());
				tr.setObjekatId(trening.getObjekatId());
				tr.setTip(trening.getTip());
				tr.setTrajanjeUMinutima(trening.getTrajanjeUMinutima());
				tr.setTrenerId(trening.getTrenerId());
				tr.setSlika(trening.getSlika());
			}
		}
		
		DataManager.saveData();
	}
	
}
