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
	row += "<td><a href='/pregled_sportskog_objekta.html?objekatId=" + sportskiObjekat.id + "' class='btn btn-primary'>" + "Detalji" + "</a></td>";
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
	
	const tipObekta = $('input[name="tip"]:checked').val();
	if (tipObekta) {
		let filtriraniSportskiObjektiTip = [];
		for (let so of filtriraniSportskiObjekti) {
			if (so.tipObjekta.toLowerCase() === tipObekta) {
			    filtriraniSportskiObjektiTip.push(so);			
			}
		}
		
		filtriraniSportskiObjekti = filtriraniSportskiObjektiTip;
	}
	
	const samoOtvoreniObjekti = $('#input-otvorenost').is(':checked');
	if (samoOtvoreniObjekti) {
		let filtriraniSportskiObjektiOtvorenost = [];
		for (let so of filtriraniSportskiObjekti) {
			if (so.status.toLowerCase() === "radi") {
			    filtriraniSportskiObjektiOtvorenost.push(so);			
			}
		}
		
		filtriraniSportskiObjekti = filtriraniSportskiObjektiOtvorenost;
	}
	
	
	const rastuceSortiranje = $("#rastuce-sortiranje").is(":checked");
	const sortiranjePo = $("#tip-sortiranja").val();
	if (sortiranjePo === "naziv") {
		if (rastuceSortiranje) {
			filtriraniSportskiObjekti.sort(function(a, b) {
				const nameA = a.naziv.toUpperCase();
				const nameB = b.naziv.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniSportskiObjekti.sort(function(a, b) {
				const nameA = a.naziv.toUpperCase();
				const nameB = b.naziv.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "lokacija") {
		if (rastuceSortiranje) {
			filtriraniSportskiObjekti.sort(function(a, b) {
				const nameA = a.lokacija.adresa.toUpperCase();
				const nameB = b.lokacija.adresa.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniSportskiObjekti.sort(function(a, b) {
				const nameA = a.lokacija.adresa.toUpperCase();
				const nameB = b.lokacija.adresa.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "prosecna-ocena") {
		if (rastuceSortiranje) {
			filtriraniSportskiObjekti.sort(function(a, b) {
				if (a.prosecnaOcena < b.prosecnaOcena) {
				  return -1;
				}
				if (a.prosecnaOcena > b.prosecnaOcena) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniSportskiObjekti.sort(function(a, b) {
				if (a.prosecnaOcena > b.prosecnaOcena) {
				  return -1;
				}
				if (a.prosecnaOcena < b.prosecnaOcena) {
				  return 1;
				}
				
				return 0;
			});
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

function sacuvajNoviObjekat() {
	if ($("#logo")[0].files.length === 0) {
		alert("Logo mora da bude izabran");
		return;
	}
	
    let formData = new FormData($("#dodavanje-novog-objekta")[0]);
        
    $.ajax({
        url: "/sportski-objekti",
        type: "POST",
        data: formData,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        complete : function (data) {
            if(data.status == 400)
                alert(data.responseText);
            else {
                alert("Objekat je uspesno sacuvan");
                window.location.reload();
            }
        }
    });
}

function preuzimanjePodatakaZaSportskiObjekat() {
	const objekatId = getUrlVars().objekatId;
	$.ajax({
        url: "/sportski-objekti/" + objekatId,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            const sportskiObjekat = data.responseJSON;
 			$("#naziv").val(sportskiObjekat.naziv);
 			$("#tip").val(sportskiObjekat.tipObjekta);
 			$("#status").val(sportskiObjekat.status);
 			$("#lokacija").val(sportskiObjekat.lokacija.adresa);
 			$("#logo").attr("src", sportskiObjekat.logo);
 			$("#ocena").val(sportskiObjekat.prosecnaOcena);
        }
    });
	
}

function preuzmiPodatakeZaMenadzerovSportskiObjekat() {
	
	$.ajax({
        url: "/sportski-objekti/menadzer",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        error: function (error) {
			console.log(error)
			if (error.status === 403) {
				return;
			} else if (error.status === 404) {
				$("#form_holder").html("<h3>Menadzer nema objekat kojim upravlja</h3>");
				return;
			}
		},
        complete: function(data) {
			
            const sportskiObjekat = data.responseJSON;
            $("#objekatId").val(sportskiObjekat.id);
 			$("#naziv").val(sportskiObjekat.naziv);
 			$("#tip").val(sportskiObjekat.tipObjekta);
 			$("#status").val(sportskiObjekat.status);
 			$("#lokacija").val(sportskiObjekat.lokacija.adresa);
 			$("#logo").attr("src", sportskiObjekat.logo);
 			$("#ocena").val(sportskiObjekat.prosecnaOcena);
        }
    });
	
}

function preuzmiPodatkeOSportskomObjektuIzUrl () {
	let urlVars = getUrlVars();
	if (!urlVars.objectId) {
		alert("Id objekta nije prolsedjen");
		window.location.reload();
	}
	
	$("#objekatIme").html(urlVars.objectName);
	$("#objekatId").val(urlVars.objectId);
}

function ucitajSportskeObjekteUDropdown() {
	$.ajax({
        url: "sportski-objekti",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            let sportskiObjekti = data.responseJSON;
            let dropdown = $("#sportski-objekti-dropdown");
            dropdown.html("<option value='0'>Izaberite objekat</option");
            for (let so of sportskiObjekti) {
                dropdown.append("<option value='" + so.id + "'>" + so.naziv + "</option>");
            }
        }
    });
}

