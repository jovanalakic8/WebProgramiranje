package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import beans.Clanarina;
import beans.ClanarinaPonuda;
import beans.User;
import dto.ClanarinaPonudaDTO;
import services.ClanarinaService;
import spark.Session;
import utils.ClanarinaStatus;

public class ClanarinaController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static ClanarinaService service = new ClanarinaService();
	
	public static void endpoints() {
		
		get("/clanarine/ponuda", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			List<ClanarinaPonuda> clanarine = service.getPonudaClanarina();
			String json = g.toJson(clanarine, List.class);
			return json;
		});
		
		get("/clanarine/ponuda/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String ponudaId = req.params("id");
			
			ClanarinaPonuda clanarina = service.getPonudaClanarinaPoId(ponudaId);
			if (clanarina == null) {
				res.type("application/json");
				res.status(404);
				return "Ponuda nije pronadjena";
			}
			
			ClanarinaPonudaDTO dto = new ClanarinaPonudaDTO();
			dto.setId(ponudaId);
			dto.setCena(clanarina.getCena());
			dto.setBrojTermina(clanarina.getBrojTermina());
			dto.setNaziv(clanarina.getNaziv());
			dto.setTip(clanarina.getTip());
			dto.setVaziDo(LocalDateTime.now().plusDays(clanarina.getBrojDanaVazenja()).toLocalDate().toString());
			
			String json = g.toJson(dto, ClanarinaPonudaDTO.class);
			return json;
		});
		
		post("/clanarine/kupovina", (req, res) -> {
			
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if (user == null) {
				res.status(403);
				return "Niste prijavljeni";
			}
			
			try {
				ClanarinaPonudaDTO clanarinaDTO = g.fromJson(req.body(), ClanarinaPonudaDTO.class);

				if (clanarinaDTO.getNaziv().isEmpty() || clanarinaDTO.getCena() == 0 ||
						clanarinaDTO.getTip() == null || clanarinaDTO.getVaziDo().isEmpty() 
						|| clanarinaDTO.getBrojTermina() == 0) {
					res.status(400);
					return "Greska se dogodila. Pokusajte da osvezite stranicu";
				}
				
				res.status(200);
				
				Clanarina clanarina = new Clanarina();
				clanarina.setId(String.valueOf(System.currentTimeMillis()));
				clanarina.setCena(clanarinaDTO.getCena());
				clanarina.setBrojPreostalihTermina(clanarinaDTO.getBrojTermina());
				clanarina.setUkupanBrojTermina(clanarinaDTO.getBrojTermina());
				clanarina.setDatumPlacanja(LocalDateTime.now().toString());
				clanarina.setKupac(user);
				clanarina.setStatus(ClanarinaStatus.AKTIVNA);
				clanarina.setVaziDo(clanarinaDTO.getVaziDo());
				
				//TODO: deaktiviraj staru clanarinu
				service.sacuvajNovuClanarinu(clanarina);
				//TODO: dodaj kupcu bodove
			} catch (Exception e) {
				e.printStackTrace();
				res.status(400);
				return "Greska pri cuvanju";
			}
			
			return "Kupovina je uspesna";
		});
	}
}