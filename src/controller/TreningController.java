package controller;

import static spark.Spark.post;


import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Trening;
import services.TreningService;
import utils.TreningTipEnum;

public class TreningController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static TreningService service = new TreningService();
	
	public static void endpoints() {
		
		post("/treninzi", (req, res) -> {
			res.type("application/json");
			
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			String naziv = req.queryParams("naziv");
			String tip = req.queryParams("tip");
			String opis = req.queryParams("opis");
			String trajanje = req.queryParams("trajanje");
			String trenerId = req.queryParams("trener");
			String objekatId = req.queryParams("objekatId");
			
			if (naziv.isEmpty() || tip.isEmpty() || opis.isEmpty() ||
					trajanje.isEmpty() || trenerId.isEmpty() || objekatId.isEmpty()) {
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
			
			Trening noviTrening = new Trening();
			noviTrening.setId(String.valueOf(System.currentTimeMillis()));
			noviTrening.setNaziv(naziv);
			noviTrening.setOpis(opis);
			noviTrening.setTip(treningTip);
			noviTrening.setTrajanjeUMinutima(trajanjeTreninga);
			noviTrening.setTrenerId(trenerId);
			noviTrening.setObjekatId(objekatId);
			
			Trening kreiranTrening = service.dodajTrening(noviTrening);
			
			res.status(200);
			return g.toJson(kreiranTrening);

		});
	}
	
}
