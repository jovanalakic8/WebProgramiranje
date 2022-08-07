package services;

import java.util.List;

import beans.ClanarinaPonuda;
import data.DataManager;

public class ClanarinaService {
	
	public List<ClanarinaPonuda> getPonudaClanarina() {
		return DataManager.data.getPonudaClanarina();
	}
	
}
