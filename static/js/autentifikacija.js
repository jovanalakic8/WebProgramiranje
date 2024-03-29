function login() {
	var data = getFormData($("#login")); 
	var s = JSON.stringify(data); 
	$.ajax({
		url: "login",
		type: "POST",
		data: s,
		contentType: "application/json",
		dataType: "json",
		complete : function (data) {
		    if (data.responseJSON.uloga.toLowerCase() === "menadzer") {
				window.location.href = "/menadzer_meni.html"
				return;
			} else if (data.responseJSON.uloga.toLowerCase() === "admin") {
				window.location.href = "/admin_meni.html"
				return;
			} else if (data.responseJSON.uloga.toLowerCase() === "trener") {
				window.location.href = "/trener_meni.html"
				return;
			} else if (data.responseJSON.uloga.toLowerCase() === "kupac") {
				window.location.href = "/kupac_meni.html"
				return;
			}
			    
            window.location.replace("/");
		}, error: function(e){
		    if (e.status == 400){
		        alert(e.responseText);
		        return;
		    } 
		}
	});
}

function logout() {
	$.ajax({
		url: "logout",
		type: "POST",
		contentType: "application/json",
		dataType: "json",
		complete : function (data) {    
            $(".logged").hide();
			$(".not-logged").show();
		}, error: function(e){
		    if(e.status == 400){
		        alert("Greska");
		    }
		}
	});
}

function proveriLogin() {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			
			if(dataJSON.loggedIn) {
				$(".logged").show();
				$(".not-logged").hide();
			} else {
				$(".logged").hide();
				$(".not-logged").show();
			}
		}
	});
}

function proveraLoginaIUloge(uloga) {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			if (!dataJSON.loggedIn) {
				alert("Niste ulogovani");
				window.location.href = "/login.html";
			} else if (dataJSON.uloga.toLowerCase() !== uloga) {
				alert("Niste prijavljeni kao " + uloga);
				window.location.href = "/login.html";
			}
		}
	});
}

function proveraLoginaIUlogeNaPocetnojStranici() {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			
			if (dataJSON.loggedIn) {
				$(".logged").show();
				$(".not-logged").hide();
				
				if (dataJSON.uloga.toLowerCase() === "menadzer") {
					$("#meni-za-menadzere").toggleClass("d-none");
				} else if (dataJSON.uloga.toLowerCase() === "admin") {
					$("#meni-za-admine").toggleClass("d-none");
				} else if (dataJSON.uloga.toLowerCase() === "trener") {
					$("#meni-za-trenere").toggleClass("d-none");
				} else if (dataJSON.uloga.toLowerCase() === "kupac") {
					$("#meni-za-kupce").toggleClass("d-none");
				}
			} else {
				$(".logged").hide();
				$(".not-logged").show();
			}
		}
	});
}

function proveraUlogeKodPregledaObjekta() {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			
			if (dataJSON.loggedIn) {
				if (dataJSON.uloga.toLowerCase() === "admin") {
					$("body").append("<button class='btn btn-danger' onclick='obrisiObjekat()'>Obrisi</button>");
				}
			}
		}
	});
}

function preusmeriUlogovanogKorisnika() {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			if(dataJSON.loggedIn) {
				window.location.href = "/";
			}
		}
	});
}

function dodajKorisnika(){
    var data = getFormData($("#dodajKorisnika"));
    var s = JSON.stringify(data);
    var myURL = "";
    myURL = "registracija";
        
    $.ajax({
        url: myURL,
        type: "POST",
        data: s,
        contentType: "application/json",
        dataType: "json",
        complete : function (data) {
            if(data.status == 400)
                alert(data.responseText);
            else
                {
                    alert("Uspesno ste se registrovali.");
                    window.location.replace("/");
                }
        }
    });
}