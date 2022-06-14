package controller;

import static spark.Spark.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import beans.User;
import data.DataManager;
import dto.LoginDTO;
import dto.RegistracijaDTO;
import services.UserService;
import spark.Session;

public class UserController {
	
	private static UserService userService = new UserService();
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	
	public static void login() {
		post("/login", (req, res) -> {
			res.type("application/json");

			try {
				LoginDTO k = g.fromJson(req.body(), LoginDTO.class);

				if (k.getKorisnickoIme() == null || k.getLozinka() == null) {
					res.status(400);
					return res;
				}
				Session ss = req.session(true);
				User korisnik = userService.login(k.getKorisnickoIme(), k.getLozinka());
				ss.attribute("user", korisnik);
				res.status(200);
				String json = g.toJson(korisnik, User.class);
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(json);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				jsonObject.addProperty("uloga", korisnik.getClass().toString());
				return jsonObject.toString();
			} catch (Exception e) {
				res.status(400);
				return "Losi kredencijali";
			}

		});
		
		post("/registracija", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik != null) {
				res.status(400);
				return "Vec ste ulogovani, ne mozete izvrsiti registraciju";
			}

			try {
				RegistracijaDTO k = g.fromJson(req.body(), RegistracijaDTO.class);
				
				if (k.getIme().isEmpty() || k.getPrezime().isEmpty() || k.getKorisnickoIme().isEmpty() ||
				    k.getLozinka().isEmpty() || k.getDatumRodjenja().isEmpty()) {
					res.status(400);
					return "Popunite sva polja";
				}
				
				RegistracijaDTO novi = userService.registracijaKorisnika(k);
				res.status(200);
				return g.toJson(novi);

			} catch (Exception e) {
				res.status(400);
				return e.getMessage();
			}


		});
	}

}
