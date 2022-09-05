package services;

import java.util.ArrayList;
import java.util.List;

import beans.Trening;
import data.DataManager;
import utils.TreningTipEnum;

public class TreningService {
	
	public List<Trening> getSviTreninzi() {
		List<Trening> neizbrisaniTreninzi = new ArrayList<Trening>();
		for (Trening trening : DataManager.data.getTreninzi()) {
			if (!trening.isBrisanjeLogicko()) {
				neizbrisaniTreninzi.add(trening);
			}
		}
		return neizbrisaniTreninzi;
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
	
	public List<Trening> getTreninziPoObjektu(String objekatId) {
		List<Trening> treninzi = new ArrayList<Trening>();
		for (Trening tr : getSviTreninzi()) {
			if (tr.getObjekatId().equals(objekatId)) {
				treninzi.add(tr);
			}
		}
		
		return treninzi;
	}
	
	public List<Trening> getGrupniTreninziPoTreneru(String trenerId) {
		List<Trening> treninzi = new ArrayList<Trening>();
		for (Trening tr : getSviTreninzi()) {
			if (tr.getTrenerId().equals(trenerId) && tr.getTip() == TreningTipEnum.GRUPNI) {
				treninzi.add(tr);
			}
		}
		
		return treninzi;
	}
	
	public List<Trening> getPersonalniTreninziPoTreneru(String trenerId) {
		List<Trening> treninzi = new ArrayList<Trening>();
		for (Trening tr : getSviTreninzi()) {
			if (tr.getTrenerId().equals(trenerId) && tr.getTip() == TreningTipEnum.PERSONALNI) {
				treninzi.add(tr);
			}
		}
		
		return treninzi;
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
	
	public void otkaziTrening(String treningId) {
		for (Trening tr : getSviTreninzi()) {
			if (tr.getId().equals(treningId)) {
				tr.setOtkazan(true);
				break;
			}
		}
		
		DataManager.saveData();
	}
	
	public void obrisiTrening(String treningId) {
		for (Trening tr : getSviTreninzi()) {
			if (tr.getId().equals(treningId)) {
				tr.setBrisanjeLogicko(true);
				break;
			}
		}
		
		DataManager.saveData();
	}
	
}
