package services;

import java.util.List;

import beans.Clanarina;
import beans.ClanarinaPonuda;
import data.DataManager;
import utils.ClanarinaStatus;

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
	
	public Clanarina getAktivnaClanarinaZaKupca(String username) {
		for (Clanarina clanarina: DataManager.data.getClanarine()) {
			if (clanarina.getKupac().getUserName().equals(username) && clanarina.getStatus().equals(ClanarinaStatus.AKTIVNA)) {
				return clanarina;
			}
		}
		return null;
	}

	public void sacuvajNovuClanarinu(Clanarina clanarina) {
		DataManager.data.getClanarine().add(clanarina);
		DataManager.saveData();
	}
	
}
