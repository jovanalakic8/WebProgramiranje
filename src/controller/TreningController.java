package controller;

import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.put;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Trening;
import beans.User;
import dto.TreningDTO;
import services.TreningService;
import services.UserService;
import spark.Session;
import utils.TreningTipEnum;

public class TreningController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static TreningService treningService = new TreningService();
	private static UserService userService = new UserService();
	
	public static void endpoints() {
		
		get("treninzi/menadzer", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null) {
				res.status(403);
				return "Niste ulogovani";
			} else if (!trenutniKorisnik.getRole().toLowerCase().equals("menadzer")) {
				res.status(403);
				return "Niste ulogovani kao menadzer";
			}
			
			String objekatId = userService.getObjekatZaMenadzera(trenutniKorisnik.getUserName());
			if (objekatId == null) {
				res.type("application/json");
				res.status(404);
				return "Menadzer nema objekat kojim upravlja";
			}
			
			List<Trening> treninzi = treningService.getTreninziPoObjektu(objekatId);
			List<TreningDTO> dtos = new ArrayList<TreningDTO>();
			for (Trening trening : treninzi) {
				TreningDTO treningDTO = new TreningDTO();
				treningDTO.setId(trening.getId());
				treningDTO.setNaziv(trening.getNaziv());
				treningDTO.setObjekatId(trening.getObjekatId());
				treningDTO.setOpis(trening.getOpis());
				treningDTO.setSlikaURL(trening.getSlika());
				treningDTO.setTip(trening.getTip().toString());
				treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
				treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
				treningDTO.setOtkazan(trening.isOtkazan());
				
				User trener = userService.getPoUsername(trening.getTrenerId());
				if (trener != null) {
					treningDTO.setTrener(trener.getName() + " " + trener.getLastName());					
				}
				
				User kupac = userService.getPoUsername(trening.getKupacId());
				if (kupac != null) {
					treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
				}
				
				dtos.add(treningDTO);
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
		});
		
		get("treninzi/objekat/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String objekatId = req.params("id");
			if (objekatId == null) {
				res.type("application/json");
				res.status(404);
				return "Menadzer nema objekat kojim upravlja";
			}
			
			List<Trening> treninzi = treningService.getTreninziPoObjektu(objekatId);
			List<TreningDTO> dtos = new ArrayList<TreningDTO>();
			for (Trening trening : treninzi) {
				TreningDTO treningDTO = new TreningDTO();
				treningDTO.setId(trening.getId());
				treningDTO.setNaziv(trening.getNaziv());
				treningDTO.setObjekatId(trening.getObjekatId());
				treningDTO.setOpis(trening.getOpis());
				treningDTO.setSlikaURL(trening.getSlika());
				treningDTO.setTip(trening.getTip().toString());
				treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
				treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
				treningDTO.setOtkazan(trening.isOtkazan());
				
				User trener = userService.getPoUsername(trening.getTrenerId());
				if (trener != null) {
					treningDTO.setTrener(trener.getName() + " " + trener.getLastName());					
				}
				
				User kupac = userService.getPoUsername(trening.getKupacId());
				if (kupac != null) {
					treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
				}
				
				dtos.add(treningDTO);
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
		});
		
		get("treninzi/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String treningId = req.params("id");
			Trening trening = treningService.getTreningPoId(treningId);
			if (trening == null) {
				res.type("application/json");
				res.status(404);
				return "Trening nije pronadjen";
			} else {
				TreningDTO treningDTO = new TreningDTO();
				treningDTO.setId(trening.getId());
				treningDTO.setNaziv(trening.getNaziv());
				treningDTO.setObjekatId(trening.getObjekatId());
				treningDTO.setOpis(trening.getOpis());
				treningDTO.setSlikaURL(trening.getSlika());
				treningDTO.setTip(trening.getTip().toString());
				treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
				treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
				treningDTO.setOtkazan(trening.isOtkazan());
				
				User trener = userService.getPoUsername(trening.getTrenerId());
				if (trener != null) {
					treningDTO.setTrener(trener.getName() + " " + trener.getLastName());		
				}
				
				User kupac = userService.getPoUsername(trening.getKupacId());
				if (kupac != null) {
					treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
				}
				
				String json = g.toJson(trening, Trening.class);
				return json;
			}
		});
		
		get("treninzi/trener/grupni/", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null || !trenutniKorisnik.getRole().toLowerCase().equals("trener")) {
				res.status(403);
				return "Nemate pristup treninzima";
			}
			
			List<Trening> treninzi = treningService.getGrupniTreninziPoTreneru(trenutniKorisnik.getUserName());
			List<TreningDTO> dtos = new ArrayList<TreningDTO>();
			for (Trening trening : treninzi) {
				TreningDTO treningDTO = new TreningDTO();
				treningDTO.setId(trening.getId());
				treningDTO.setNaziv(trening.getNaziv());
				treningDTO.setObjekatId(trening.getObjekatId());
				treningDTO.setOpis(trening.getOpis());
				treningDTO.setSlikaURL(trening.getSlika());
				treningDTO.setTip(trening.getTip().toString());
				treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
				treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
				treningDTO.setOtkazan(trening.isOtkazan());
				
				User trener = userService.getPoUsername(trening.getTrenerId());
				if (trener != null) {
					treningDTO.setTrener(trener.getName() + " " + trener.getLastName());					
				}
				
				User kupac = userService.getPoUsername(trening.getKupacId());
				if (kupac != null) {
					treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
				}
				
				dtos.add(treningDTO);
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
		});
		
		get("treninzi/trener/personalni/", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null || !trenutniKorisnik.getRole().toLowerCase().equals("trener")) {
				res.status(403);
				return "Nemate pristup treninzima";
			}
			
			List<Trening> treninzi = treningService.getPersonalniTreninziPoTreneru(trenutniKorisnik.getUserName());
			List<TreningDTO> dtos = new ArrayList<TreningDTO>();
			for (Trening trening : treninzi) {
				if (trening.isOtkazan()) {
					continue;
				}
				
				TreningDTO treningDTO = new TreningDTO();
				treningDTO.setId(trening.getId());
				treningDTO.setNaziv(trening.getNaziv());
				treningDTO.setObjekatId(trening.getObjekatId());
				treningDTO.setOpis(trening.getOpis());
				treningDTO.setSlikaURL(trening.getSlika());
				treningDTO.setTip(trening.getTip().toString());
				treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
				treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
				treningDTO.setOtkazan(trening.isOtkazan());
				
				User trener = userService.getPoUsername(trening.getTrenerId());
				if (trener != null) {
					treningDTO.setTrener(trener.getName() + " " + trener.getLastName());					
				}
				
				User kupac = userService.getPoUsername(trening.getKupacId());
				if (kupac != null) {
					treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
				}
				
				dtos.add(treningDTO);
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
		});
		
		get("treninzi/kupac/:objekatId", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String objekatId = req.params("objekatId");
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null || !trenutniKorisnik.getRole().toLowerCase().equals("kupac")) {
				res.status(403);
				return "Nemate pristup treninzima";
			}
			
			List<Trening> treninzi = treningService.getTreninziPoObjektu(objekatId);
			
			List<TreningDTO> dtos = new ArrayList<TreningDTO>();
			for (Trening trening : treninzi) {
				if (trening.getTip() == TreningTipEnum.GRUPNI || 
						(trening.getTip() == TreningTipEnum.PERSONALNI && trening.getKupacId().equals(trenutniKorisnik.getUserName()))) {
					TreningDTO treningDTO = new TreningDTO();
					treningDTO.setId(trening.getId());
					treningDTO.setNaziv(trening.getNaziv());
					treningDTO.setObjekatId(trening.getObjekatId());
					treningDTO.setOpis(trening.getOpis());
					treningDTO.setSlikaURL(trening.getSlika());
					treningDTO.setTip(trening.getTip().toString());
					treningDTO.setTrajanje(trening.getTrajanjeUMinutima());
					treningDTO.setDatumIVremeOdrzavanja(trening.getDatumIVremeOdrzavanja());
					treningDTO.setOtkazan(trening.isOtkazan());
					
					User trener = userService.getPoUsername(trening.getTrenerId());
					if (trener != null) {
						treningDTO.setTrener(trener.getName() + " " + trener.getLastName());					
					}
					
					User kupac = userService.getPoUsername(trening.getKupacId());
					if (kupac != null) {
						treningDTO.setKupac(kupac.getName() + " " + kupac.getLastName());					
					}
					
					//TODO filter!
					dtos.add(treningDTO);
				}
			}
			
			String json = g.toJson(dtos, List.class);
			return json;
			
		});
		
		post("/treninzi", (req, res) -> {
			res.type("application/json");
			
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String naziv = req.queryParams("naziv");
			String tip = req.queryParams("tip");
			String opis = req.queryParams("opis");
			String trajanje = req.queryParams("trajanje");
			String trenerId = req.queryParams("trener");
			String objekatId = req.queryParams("objekatId");
			String datumIVremeOdrzavanja = req.queryParams("datumIVremeOdrzavanja");
			String kupacId = req.queryParams("kupacId");
			
			if (naziv.isEmpty() || tip.isEmpty() || opis.isEmpty() ||
					trajanje.isEmpty() || trenerId.isEmpty() || objekatId.isEmpty() && datumIVremeOdrzavanja.isEmpty()) {
				res.status(400);
				return "Sva polja su obavezna";
			}
			
			TreningTipEnum treningTip;
			int trajanjeTreninga;
			try {
				treningTip = TreningTipEnum.valueOf(tip);
				trajanjeTreninga = Integer.parseInt(trajanje);
			} catch (Exception e) {
				res.status(400);
				return "Uneti podaci nisu ispravni";
			}
			
			if (tip.equals(TreningTipEnum.PERSONALNI.toString()) && kupacId.isEmpty()) {
				res.status(400);
				return "Morate da izaberete kupca za personalni trening";
			}
			
			if (tip.equals(TreningTipEnum.GRUPNI.toString())) {
				kupacId = null;
			}
			
			String slikaURL = "";
		    try (InputStream is = req.raw().getPart("slika").getInputStream()) {
		    	slikaURL = "/images/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		        File targetFile = new File(System.getProperty("user.dir") + "/static" + slikaURL);
		        OutputStream outStream = new FileOutputStream(targetFile);

		        byte[] buffer = new byte[8 * 1024];
		        int bytesRead;
		        while ((bytesRead = is.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        is.close();
		        outStream.close();
		    } catch (Exception e) {
				e.printStackTrace();
				res.status(500);
				return "Internal server error";
			}
			
			Trening noviTrening = new Trening();
			noviTrening.setId(String.valueOf(System.currentTimeMillis()));
			noviTrening.setNaziv(naziv);
			noviTrening.setOpis(opis);
			noviTrening.setTip(treningTip);
			noviTrening.setTrajanjeUMinutima(trajanjeTreninga);
			noviTrening.setTrenerId(trenerId);
			noviTrening.setObjekatId(objekatId);
			noviTrening.setSlika(slikaURL);
			noviTrening.setDatumIVremeOdrzavanja(datumIVremeOdrzavanja);
			noviTrening.setKupacId(kupacId);
			
			Trening kreiranTrening = treningService.dodajTrening(noviTrening);
			
			res.status(200);
			return g.toJson(kreiranTrening);

		});
		
		put("/treninzi/otkazi/:id", (req, res) -> {
			res.type("application/json");
			
			String treningId = req.params("id");
			
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null || !trenutniKorisnik.getRole().toLowerCase().equals("trener")) {
				res.status(403);
				return "Nemate pristup treninzima";
			}
						
			treningService.otkaziTrening(treningId);
			
			res.status(200);
			return res;

		});
		
		put("/treninzi/:id", (req, res) -> {
			res.type("application/json");
			
			String treningId = req.params("id");
			
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String naziv = req.queryParams("naziv");
			String tip = req.queryParams("tip");
			String opis = req.queryParams("opis");
			String trajanje = req.queryParams("trajanje");
			String trenerId = req.queryParams("trener");
			String objekatId = req.queryParams("objekatId");
			String datumIVremeOdrzavanja = req.queryParams("datumIVremeOdrzavanja");
			String kupacId = req.queryParams("kupacId");
			
			
			if (naziv.isEmpty() || tip.isEmpty() || opis.isEmpty() ||
					trajanje.isEmpty() || trenerId.isEmpty() || objekatId.isEmpty() && datumIVremeOdrzavanja.isEmpty()) {
				res.status(400);
				return "Sva polja su obavezna";
			}
			
			TreningTipEnum treningTip;
			int trajanjeTreninga;
			try {
				treningTip = TreningTipEnum.valueOf(tip);
				trajanjeTreninga = Integer.parseInt(trajanje);
			} catch (Exception e) {
				res.status(400);
				return "Uneti podaci nisu ispravni";
			}
			
			if (tip.equals(TreningTipEnum.PERSONALNI.toString()) && kupacId.isEmpty()) {
				res.status(400);
				return "Morate da izaberete kupca za personalni trening";
			}
			
			if (tip.equals(TreningTipEnum.GRUPNI.toString())) {
				kupacId = null;
			}
			
			String slikaURL = "";
			if (req.raw().getPart("slika") != null) {
				try (InputStream is = req.raw().getPart("slika").getInputStream()) {
			    	slikaURL = "/images/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
			        File targetFile = new File(System.getProperty("user.dir") + "/static" + slikaURL);
			        OutputStream outStream = new FileOutputStream(targetFile);

			        byte[] buffer = new byte[8 * 1024];
			        int bytesRead;
			        while ((bytesRead = is.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			        is.close();
			        outStream.close();
			    } catch (Exception e) {
					e.printStackTrace();
					res.status(500);
					return "Internal server error";
				}
			}
		    			
			Trening trening = treningService.getTreningPoId(treningId);
			if (trening == null) {
				res.status(400);
				return "Trening nije pronadjen";
			}
			
			trening.setNaziv(naziv);
			trening.setOpis(opis);
			trening.setTip(treningTip);
			trening.setTrajanjeUMinutima(trajanjeTreninga);
			trening.setTrenerId(trenerId);
			trening.setObjekatId(objekatId);
			trening.setDatumIVremeOdrzavanja(datumIVremeOdrzavanja);
			trening.setKupacId(kupacId);
			
			if (!slikaURL.isEmpty()) {
				trening.setSlika(slikaURL);
			}
			
			treningService.azurirajTrening(trening);
			
			res.status(200);
			return g.toJson(trening);

		});
	}
}
