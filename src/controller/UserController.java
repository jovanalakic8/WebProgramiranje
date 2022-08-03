package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.List;

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
	
	public static void endpoints() {
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
				return "Neispravan unos podataka";
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
		
		post("/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if (user != null) {
				ss.invalidate();
			}
			
			res.status(200);
			return "";
		});
		
		get("/isLoggedIn", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User k = ss.attribute("user");
			JsonParser parser = new JsonParser();
			if (k == null) {
				String jsonString = "{'loggedIn': false}";
				JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
				return jsonObject.toString();
			} else {
				String jsonString = "{'loggedIn': true}";
				JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
				return jsonObject.toString();
			}
		});
		
		get("/my-profile", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User k = ss.attribute("user");
			JsonParser parser = new JsonParser();
			if (k == null) {
				String jsonString = "{'loggedIn': false}";
				JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
				res.status(403);
				return jsonObject.toString();
			} else {
				String json = g.toJson(k, User.class);
				return json;
			}
		});
		
		post("/my-profile", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User trenutniKorisnik = ss.attribute("user");
			if (trenutniKorisnik == null) {
				res.status(403);
				return "Niste ulogovani";
			}

			try {
				RegistracijaDTO k = g.fromJson(req.body(), RegistracijaDTO.class);
				if (k.getIme().isEmpty() || k.getPrezime().isEmpty() || k.getDatumRodjenja().isEmpty()) {
					res.status(400);
					return "Popunite sva polja";
				}
				
				RegistracijaDTO azuriran = userService.azuriranjeKorisnika(trenutniKorisnik.getUserName(), k);
				res.status(200);
				return g.toJson(azuriran);

			} catch (Exception e) {
				res.status(400);
				return e.getMessage();
			}
		});
		
		get("/users", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User k = ss.attribute("user");
			
			JsonParser parser = new JsonParser();
			if (k == null) {
				String jsonString = "{'error': 'not logged in'}";
				JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
				res.status(403);
				return jsonObject.toString();
			} else {
				if (!k.getRole().equals("ADMIN")) {
					String jsonString = "{'error': 'only admins can access this page'}";
					JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
					res.status(401);
					return jsonObject.toString();
				}
				
				String json = g.toJson(userService.sviKorisnici(), List.class);
				return json;
			}
		});
		
		get("/users/without-object", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User k = ss.attribute("user");
			
			JsonParser parser = new JsonParser();
			if (k == null) {
				String jsonString = "{'error': 'not logged in'}";
				JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
				res.status(403);
				return jsonObject.toString();
			} else {
				if (!k.getRole().equals("ADMIN")) {
					String jsonString = "{'error': 'only admins can access this page'}";
					JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
					res.status(401);
					return jsonObject.toString();
				}
				
				String json = g.toJson(userService.menadzeriBezObjekta(), List.class);
				return json;
			}
		});
	}

}
