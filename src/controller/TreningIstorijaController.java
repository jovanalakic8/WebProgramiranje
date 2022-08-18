package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Clanarina;
import beans.ClanarinaPonuda;
import beans.SportskiObjekat;
import beans.Trening;
import beans.TreningIstorija;
import beans.User;
import dto.ClanarinaPonudaDTO;
import dto.NoviTreningIstorijaDTO;
import dto.TreningDTO;
import dto.TreningIstorijaDTO;
import services.ClanarinaService;
import services.SportskiObjektiService;
import services.TreningIstorijaService;
import services.TreningService;
import services.UserService;
import spark.Session;
import utils.ClanarinaStatus;

public class TreningIstorijaController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static TreningIstorijaService service = new TreningIstorijaService();
	private static TreningService treningService = new TreningService();
	private static SportskiObjektiService sportskiObjektiService = new SportskiObjektiService();
	private static UserService userService = new UserService();	
	
	public static void endpoints() {
		
		get("treninzi-istorija/kupac", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null) {
				res.status(403);
				return "Niste ulogovani";
			} else if (!trenutniKorisnik.getRole().toLowerCase().equals("kupac")) {
				res.status(403);
				return "Niste ulogovani kao menadzer";
			}
			
			List<TreningIstorija> list = service.pronadjiZapiseZaKupcaUPoslednjihMesecDana(trenutniKorisnik.getUserName());
			
			List<TreningIstorijaDTO> dtos = new ArrayList<TreningIstorijaDTO>();
			for (TreningIstorija ti : list) {
				TreningIstorijaDTO dto = new TreningIstorijaDTO();
				dto.setId(ti.getId());
				dto.setDatumIVremePrijave(ti.getDatumIVremePrijave());
				
				Trening trening = treningService.getTreningPoId(ti.getTreningId());
				dto.setTrening(trening.getNaziv());
				
				SportskiObjekat so = sportskiObjektiService.getSportskiObjekatPoId(trening.getObjekatId());
				dto.setObjekat(so.getNaziv());
				
				User user = userService.getPoUsername(trening.getTrenerId());
				dto.setTrener(user.getName() + " " + user.getLastName());
				
				dtos.add(dto);
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
		});
		
		post("/treninzi-istorija", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if (user == null) {
				res.status(403);
				return "Niste prijavljeni";
			} else if (!user.getRole().toLowerCase().equals("kupac")) {
				res.status(403);
				return "Samo kupci mogu da se prijave u objekat";
			}
			
			try {
				NoviTreningIstorijaDTO dto = g.fromJson(req.body(), NoviTreningIstorijaDTO.class);

				if (dto.getTreningId().isEmpty()) {
					res.status(400);
					return "Neispravan unos podataka";
				}
				
				TreningIstorija ti = service.pronadjiZapisZaKupcaITrening(user.getUserName(), dto.getTreningId());
				if (ti != null) {
					res.status(400);
					return "Vec ste se prijavili za izabrani trening";
				}
				
				res.status(200);
				
				TreningIstorija treningIstorija = new TreningIstorija();
				treningIstorija.setId(String.valueOf(System.currentTimeMillis()));
				treningIstorija.setDatumIVremePrijave(LocalDateTime.now().toString());
				treningIstorija.setKupacId(user.getUserName());
				treningIstorija.setTreningId(dto.getTreningId());
				
				service.sacuvajNovi(treningIstorija);
			} catch (Exception e) {
				res.status(400);
				return "Neispravni podaci";
			}
			
			return res;
		});
	}
	
}
