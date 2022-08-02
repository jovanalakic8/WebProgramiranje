function getFormData($form) {
	var unindexedArray = $form.serializeArray();
	var indexedArray = {};
	
	$.map(unindexedArray, function(n, i){
		indexedArray[n['name']] = n['value'];
    });

    return indexedArray;
}

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
            window.location.replace("/");
		}, error: function(e){
		    if(e.status == 400){
		        alert(e.responseText);
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

function preusmeriUlogovanogKorisnika() {
	$.ajax({
		url: "isLoggedIn",
		type: "GET",
		complete: function(data) {
			let dataJSON = data.responseJSON;
			console.log(dataJSON)
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

function prikaziProfil() {
	$.ajax({
        url: "/my-profile",
        type: "GET",
        data: {},
        contentType: "application/json",
        dataType: "json",
        complete : function (data) {
            if(data.status == 403) {
                alert("Samo registrovani korisnici mogu da koriste ovu stranicu");
                window.location.replace("/");
            }
            else {
				$("#save-button").attr("disabled", false);
				$("#ime").val(data.responseJSON.name);
				$("#prezime").val(data.responseJSON.lastName);
				$("#korisnickoIme").val(data.responseJSON.userName);
				$("#datumRodjenja").val(data.responseJSON.birthDate);
				
				if (data.responseJSON.sex === "ZENSKI") {
					$("#radio_sex_w").prop("checked", true);
				} else {
					$("#radio_sex_m").prop("checked", true);
				}
				
				if (data.responseJSON.role === "KUPAC") {
					$("#radio_role_c").prop("checked", true);
				} else if (data.responseJSON.role === "MENADZER") {
					$("#radio_role_m").prop("checked", true);
				} else {
					$("#radio_role_t").prop("checked", true);
				}
			}    
        }
    });
}

function azurirajProfil() {
	var data = getFormData($("#profile-data"));
    var json = JSON.stringify(data);
        
    $.ajax({
        url: "/my-profile",
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        complete : function (data) {
            if(data.status == 400)
                alert(data.responseText);
            else {
                alert("Uspesno ste ste azurirali podatke");
                window.location.reload();
            }
        }
    });
}

var registrovaniKorisnici;
function prikaziSveKorisnike() {
	$.ajax({
        url: "/users",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            registrovaniKorisnici = data.responseJSON;
            let tableBody = $("#table-body");
            tableBody.html("");
            for (let k of registrovaniKorisnici) {
                tableBody.append(kreirajRedZaKorisnika(k));
            }
 
        }
    });
}

function kreirajRedZaKorisnika(korisnik) {
	let row = "<tr>";
	row += "<td>" + korisnik.name + "</td>";
	row += "<td>" + korisnik.lastName + "</td>";
	row += "<td>" + korisnik.userName + "</td>";
	row += "<td>" + korisnik.role.toLowerCase() + "</td>";
	row += "<td>" + korisnik.birthDate + "</td>";
	row += "<td>" + korisnik.sex.toLowerCase() + "</td>";
	
	return row;
}

function pretragaRegistrovanihKorisnika() {
	let kriterijum = $("#kriterijum").val();
	let tekst = $("#pretraga").val();
	
	let filtriraniKorisnici = [];
	if (kriterijum === "ime") {
		for (let k of registrovaniKorisnici) {
			if (k.name.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniKorisnici.push(k);
			}
		}
	} else if (kriterijum === "prezime") {
		for (let k of registrovaniKorisnici) {
			if (k.lastName.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniKorisnici.push(k);
			}
		}
	} else if (kriterijum === "korisnicko-ime") {
		for (let k of registrovaniKorisnici) {
			if (k.userName.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniKorisnici.push(k);
			}
		}
	}
	
	const ulogaKorisnika = $('input[name="uloga"]:checked').val();
	
	if (ulogaKorisnika) {
		let filtriraniKorisniciUloga = [];
		for (let k of filtriraniKorisnici) {
			if (k.role === ulogaKorisnika) {
			    filtriraniKorisniciUloga.push(k);			
			}
		}
		
		filtriraniKorisnici = filtriraniKorisniciUloga;
	}
	
	const rastuceSortiranje = $("#rastuce-sortiranje").is(":checked");
	const sortiranjePo = $("#tip-sortiranja").val();
	if (sortiranjePo === "ime") {
		if (rastuceSortiranje) {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.name.toUpperCase();
				const nameB = b.name.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.name.toUpperCase();
				const nameB = b.name.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "prezime") {
		if (rastuceSortiranje) {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.lastName.toUpperCase();
				const nameB = b.lastName.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.lastName.toUpperCase();
				const nameB = b.lastName.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "korisnicko-ime") {
		if (rastuceSortiranje) {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.userName.toUpperCase();
				const nameB = b.userName.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniKorisnici.sort(function(a, b) {
				const nameA = a.userName.toUpperCase();
				const nameB = b.userName.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	}
	
	let tableBody = $("#table-body");
	tableBody.html("");
	for (let k of filtriraniKorisnici) {
	    tableBody.append(kreirajRedZaKorisnika(k));
	}
	
}

var sportskiObjekti = [];
function ucitajSportskeObjekte() {
	$.ajax({
        url: "sportski-objekti",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            sportskiObjekti = data.responseJSON;
            let tableBody = $("#table-body");
            tableBody.html("");
            for (let so of sportskiObjekti) {
                tableBody.append(kreirajRedZaSportskiObjekat(so));
            }
        }
    });
}

function kreirajRedZaSportskiObjekat(sportskiObjekat) {
	let row = "<tr>";
	row += "<td>" + sportskiObjekat.naziv + "</td>";
	row += "<td>" + sportskiObjekat.tipObjekta + "</td>";
	row += "<td>" + sportskiObjekat.sadrzaj + "</td>";
	row += "<td>" + sportskiObjekat.status + "</td>";
	row += "<td>" + sportskiObjekat.lokacija.adresa + "</td>";
	row += "<td><img width=50 height=50 src='" + sportskiObjekat.logo + "'</td>";
	row += "<td>" + sportskiObjekat.prosecnaOcena + "</td>";
	row += "<td>" + sportskiObjekat.radnoVreme + "</td>";
	row += "</tr>";
	return row;
}

function pretraga() {
	let kriterijum = $("#kriterijum").val();
	let tekst = $("#pretraga").val();
	
	let filtriraniSportskiObjekti = [];
	if (kriterijum === "naziv") {
		for (let so of sportskiObjekti) {
			if (so.naziv.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniSportskiObjekti.push(so);
			}
		}
	} else if (kriterijum === "tip") {
		for (let so of sportskiObjekti) {
			if (so.tipObjekta.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniSportskiObjekti.push(so);
			}
		}
	} else if (kriterijum === "lokacija") {
		for (let so of sportskiObjekti) {
			if (so.lokacija.adresa.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniSportskiObjekti.push(so);
			}
		}
	} else if (kriterijum === "ocena") {
		if (tekst === "") {
			for (let so of sportskiObjekti) {
				filtriraniSportskiObjekti.push(so);
			}
		} else {
			try {
				let broj = parseFloat(tekst);
				for (let so of sportskiObjekti) {
					if (so.prosecnaOcena === broj) {
						filtriraniSportskiObjekti.push(so);
					}
				}
			} catch {
				filtriraniSportskiObjekti = [];
			}	
		}
	}
	
	let tableBody = $("#table-body");
	tableBody.html("");
	for (let so of filtriraniSportskiObjekti) {
	    tableBody.append(kreirajRedZaSportskiObjekat(so));
	}
}

function restartujPretragu() {
	let tableBody = $("#table-body");
	tableBody.html("");
	for (let so of sportskiObjekti) {
	    tableBody.append(kreirajRedZaSportskiObjekat(so));
	}
}
