package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.ClanarinaPonuda;
import beans.TreningIstorija;
import data.DataManager;

public class TreningIstorijaService {
	
	public void sacuvajNovi(TreningIstorija treningIstorija) {
		DataManager.data.getIstorijaTreninga().add(treningIstorija);
		DataManager.saveData();
	}
	
	public TreningIstorija pronadjiZapisZaKupcaITrening(String kupacId, String treningId) {
		for (TreningIstorija tr : DataManager.data.getIstorijaTreninga()) {
			if (tr.getKupacId().equals(kupacId) && tr.getTreningId().equals(treningId)) {
				return tr;
			}
		};
		
		return null;
	}
	
	public List<TreningIstorija> pronadjiZapiseZaKupcaUPoslednjihMesecDana(String kupacId) {
		List<TreningIstorija> list = new ArrayList<TreningIstorija>();
		for (TreningIstorija tr : DataManager.data.getIstorijaTreninga()) {
			if (tr.getKupacId().equals(kupacId) &&
					LocalDateTime.parse(tr.getDatumIVremePrijave()).compareTo(LocalDateTime.now().minusMonths(1)) > 0) {
				list.add(tr);
			}
		}
		
		return list;
	}
	
}
