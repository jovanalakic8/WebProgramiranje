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

function pronadjiMenadzereBezObekta() {
	$.ajax({
        url: "users/menadzeri/bez-objekta",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            menadzeriBezObjekta = data.responseJSON;
            let select = $("#select-menadzeri");
            for (let men of menadzeriBezObjekta) {
                select.append("<option value='" + men.userName + "' >" + men.name + " " + men.lastName + "</option>");
            }
 
        }
    });
}

function preuzmiTrenere() {
	$.ajax({
        url: "/users/treneri",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            const treneri = data.responseJSON;
            let select = $("#select-treneri");
            for (let trener of treneri) {
                select.append("<option value='" + trener.userName + "' >" + trener.name + " " + trener.lastName + "</option>");
            }
 
        }
    });
}

