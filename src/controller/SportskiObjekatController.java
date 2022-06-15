package controller;

import static spark.Spark.get;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import services.SportskiObjektiService;


public class SportskiObjekatController {
	
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	private static SportskiObjektiService service = new SportskiObjektiService();
	
	public static void endpoints() {
		
		get("/sportski-objekti", (req, res) -> {
			res.type("application/json");
			res.status(200);
			
			String json = g.toJson(service.getSviSportskiObjekti(), List.class);
			return json;
		});
		
	}
}
