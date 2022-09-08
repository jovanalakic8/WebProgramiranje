package controller;

import static spark.Spark.get;
import static spark.Spark.delete;
import static spark.Spark.post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.swing.filechooser.FileSystemView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Lokacija;
import beans.SportskiObjekat;
import beans.User;
import dto.NoviObjekatDTO;
import services.SportskiObjektiService;
import services.UserService;
import spark.Session;
import spark.utils.IOUtils;


public class SportskiObjekatController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static SportskiObjektiService service = new SportskiObjektiService();
	private static UserService userService = new UserService();
	
	public static void endpoints() {
		
		get("/sportski-objekti/menadzer", (req, res) -> {
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null) {
				res.status(403);
				return "Niste ulogovani";
			} else if (!trenutniKorisnik.getRole().toLowerCase().equals("menadzer")) {
				res.status(403);
				return "Niste ulogovani kao menadzer";
			}
			
			res.type("application/json");
			res.status(200);
			
			String objekatId = trenutniKorisnik.getManagedSportObjectId();
			
			SportskiObjekat sportskiObjekat = service.getSportskiObjekatPoId(objekatId);
			if (sportskiObjekat == null) {
				res.type("application/json");
				res.status(404);
				return "Objekat nije pronadjen";
			} else {
				String json = g.toJson(sportskiObjekat, SportskiObjekat.class);
				return json;
			}
		});
		
		get("/sportski-objekti", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			List<SportskiObjekat> sportskiObjekti = service.getSviSportskiObjekti();
			sportskiObjekti.sort(SportskiObjekat.SportskiObjekatOtvorenostComparator);
			String json = g.toJson(sportskiObjekti, List.class);
			return json;
		});
		
		get("/sportski-objekti/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String objekatId = req.params("id");
			
			SportskiObjekat sportskiObjekat = service.getSportskiObjekatPoId(objekatId);
			if (sportskiObjekat == null) {
				res.type("application/json");
				res.status(404);
				return "Objekat nije pronadjen";
			} else {
				String json = g.toJson(sportskiObjekat, SportskiObjekat.class);
				return json;
			}
		});
		
		post("/sportski-objekti", (req, res) -> {
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String naziv = req.queryParams("naziv");
			String tip = req.queryParams("tip");
			String gSirina = req.queryParams("gSirina");
			String gDuzina = req.queryParams("gDuzina");
			String ulica = req.queryParams("ulica");
			String broj = req.queryParams("broj");
			String mesto = req.queryParams("mesto");
			String zip = req.queryParams("zip");
			String menadzer = req.queryParams("menadzer");
			
			if (naziv.isEmpty() || tip.isEmpty() || gSirina.isEmpty() ||
					gDuzina.isEmpty() || ulica.isEmpty() || broj.isEmpty() ||
					mesto.isEmpty() || zip.isEmpty() || menadzer.isEmpty()) {
				res.status(400);
				return "Sva polja su obavezna";
			}
			
			String logoName = "";
		    try (InputStream is = req.raw().getPart("logo").getInputStream()) {
		    	logoName = "/images/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		        File targetFile = new File(System.getProperty("user.dir") + "/static" + logoName);
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
			
			try {
				SportskiObjekat sportskiObjekat = new SportskiObjekat();
				sportskiObjekat.setId(String.valueOf(System.currentTimeMillis()));
				sportskiObjekat.setNaziv(naziv);
				sportskiObjekat.setTipObjekta(tip);
				sportskiObjekat.setStatus("Ne radi");
				sportskiObjekat.setSadrzaj("");
				sportskiObjekat.setProsecnaOcena(0);
				sportskiObjekat.setRadnoVreme("");
				
				Lokacija lokacija = new Lokacija(
						Double.parseDouble(gSirina),
						Double.parseDouble(gDuzina), 
						ulica + " " + broj + ", " + mesto + ", " + zip);
				
				sportskiObjekat.setLokacija(lokacija);
				sportskiObjekat.setLogo(logoName);
				
				service.dodajSportskiObjekat(sportskiObjekat);
				
				res.status(200);
				return g.toJson("");
			} catch (Exception e) {
				res.status(400);
				return "Unete vrednosti nisu ispravne";
			}
		
		});
		
		delete("/sportski-objekti/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String objekatId = req.params("id");
			service.obrisiObjekat(objekatId);
			userService.obrisiSportskiObjekatZaMenadzera(objekatId);
			String json = g.toJson(null);
			return json;
		});
		
	}
}
