package controller;

import static spark.Spark.get;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.ClanarinaPonuda;
import services.ClanarinaService;

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
	}
}
