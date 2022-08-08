package services;

import java.util.List;

import beans.Clanarina;
import beans.ClanarinaPonuda;
import data.DataManager;

public class ClanarinaService {
	
	public List<ClanarinaPonuda> getPonudaClanarina() {
		return DataManager.data.getPonudaClanarina();
	}

	public ClanarinaPonuda getPonudaClanarinaPoId(String ponudaId) {
		for (ClanarinaPonuda ponuda : getPonudaClanarina()) {
			if (ponuda.getId().equals(ponudaId)) {
				return ponuda;
			}
		}
		return null;
	}

	public void sacuvajNovuClanarinu(Clanarina clanarina) {
		DataManager.data.getClanarine().add(clanarina);
		DataManager.saveData();
	}
	
}
