package controller;

import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.put;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Trening;
import dto.TreningDTO;
import services.TreningService;
import utils.TreningTipEnum;

public class TreningController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static TreningService service = new TreningService();
	
	public static void endpoints() {
		
		get("treninzi/:id", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String treningId = req.params("id");
			Trening trening = service.getTreningPoId(treningId);
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
				treningDTO.setTrenerId(trening.getTrenerId());
				String json = g.toJson(trening, Trening.class);
				return json;
			}
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
			
			Trening kreiranTrening = service.dodajTrening(noviTrening);
			
			res.status(200);
			return g.toJson(kreiranTrening);

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
		    			
			Trening trening = service.getTreningPoId(treningId);
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
			
			if (!slikaURL.isEmpty()) {
				trening.setSlika(slikaURL);
			}
			
			service.azurirajTrening(trening);
			
			res.status(200);
			return g.toJson(trening);

		});
	}
}
