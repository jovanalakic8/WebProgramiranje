function preuzmiPodatkeOTreningu () {
	let urlVars = getUrlVars();
	if (!urlVars.treningId) {
		alert("Id treninga nije prolsedjen");
		window.location.reload();
	}
	
	$("#treningId").val(urlVars.treningId);
	
	$.ajax({
        url: "treninzi/" + urlVars.treningId,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
	
			if (data.status === 404) {
				alert("Trening nije pronadjen");
				return;	
			}
			
            let trening = data.responseJSON;
            $("#treningId").val(trening.id);
            $("#naziv").val(trening.naziv);
            $("#opis").val(trening.opis);
            $("#trajanje").val(trening.trajanjeUMinutima);
            $("#select-treneri").val(trening.trenerId);
            $("#objekatId").val(trening.objekatId);
            $("#datumIVremeOdrzavanja").val(trening.datumIVremeOdrzavanja);
            $("#select-kupci").val(trening.kupacId);
            $("#cena").val(trening.cena);
            
            if (trening.tip.toLowerCase() === "grupni") {
				$("#radio_tip_grupni").attr("checked", "checked");
			} else {
				$("#radio_tip_personalni").attr("checked", "checked");
			}
			
        }
    });
}

function sacuvajNoviTrening() {
	if ($("#slika")[0].files.length === 0) {
		alert("Slika mora da bude izabran");
		return;
	}
	
    let formData = new FormData($("#dodavanje-novog-treninga")[0]);
        
    $.ajax({
        url: "/treninzi",
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
                alert("Trening je uspesno sacuvan");
                window.location.reload();
            }
        }
    });
}

function azurirajTrening() {
    let formData = new FormData($("#dodavanje-novog-treninga")[0]);
    
    if ($("#slika")[0].files.length === 0) {
		formData.delete("slika");
	}
	
	let urlVars = getUrlVars();
	
    $.ajax({
        url: "/treninzi/" + urlVars.treningId,
        type: "PUT",
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
                alert("Trening je uspesno azuriran");
                //window.location.reload();
            }
        }
    });
}

function getTreninziZaMenadzera () {
	
	$.ajax({
        url: "treninzi/menadzer",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        error: function(error) {
			if (data.status === 403) {
				alert("Niste ulogovani");
				window.location.href="/login.html";
				return;	
			} else if (data.status === 403) {
				$("#table-body").html("<h3>Treninzi nisu pronadjeni</h3>");
			}
		},
        complete: function(data) {
			
			let treninzi = data.responseJSON;
			let tableBody = $("#table-body");
            tableBody.html("");
            for (let t of treninzi) {
                tableBody.append(kreirajRedZaTrening(t));
            }
			
        }
    });
}

function kreirajRedZaTrening(t) {
	let row = "<tr>";
	row += "<td>" + t.naziv + "</td>";
	row += "<td>" + t.tip + "</td>";
	row += "<td>" + t.trajanje + "</td>";
	row += "<td>" + t.opis + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td><img width=50 height=50 src='" + t.slikaURL + "'</td>";
	row += "<td>" + t.datumIVremeOdrzavanja + "</td>";
	if (t.otkazan) {
		row += "<td>Otkazan</td>";		
	} else {
		row += "<td>Aktivan</td>";
	}
	
	if (t.kupac) {
		row += "<td>" + t.kupac + "</td>";		
	} else {
		row += "<td>/</td>";
	}
	row += "<td><a href='/izmena_treninga.html?treningId=" + t.id + "' class='btn btn-primary'>" + "Izmena" + "</a></td>";
	row += "<td><button data-treningId=" + t.id + " class='btn btn-danger' onclick='brisanjeTreninga(this)'>Brisanje</button></td>";
	row += "</tr>";
	return row;
}

function brisanjeTreninga(dugme) {
	const treningId = dugme.getAttribute("data-treningId");
	
	$.ajax({
	    url: "treninzi/" + treningId,
	    type: "DELETE",
	    contentType: "application/json",
	    dataType: "json",
	    complete: function(data) {
			alert("Brisanje uspesno");
			window.location.reload();
	    }
	});
}

var treninzi = [];
function getGrupniTreninziZaTrenera() {
	$.ajax({
	    url: "treninzi/trener/grupni/",
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    error: function(error) {
			if (data.status === 403) {
				alert("Niste ulogovani");
				window.location.href="/login.html";
				return;	
			} else if (data.status === 403) {
				$("#table-body").html("<h3>Treninzi nisu pronadjeni</h3>");
			}
		},
	    complete: function(data) {
			
			treninzi = data.responseJSON;
			let tableBody = $("#table-body");
	        tableBody.html("");
	        for (let t of treninzi) {
	            tableBody.append(kreirajRedZaGrupniTrening(t));
	        }
			
	    }
	});
}

function kreirajRedZaGrupniTrening(t) {
	let row = "<tr>";
	row += "<td>" + t.naziv + "</td>";
	row += "<td>" + t.tip + "</td>";
	row += "<td>" + t.trajanje + "</td>";
	row += "<td>" + t.opis + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td>" + t.cena + "</td>";
	row += "<td><img width=50 height=50 src='" + t.slikaURL + "'</td>";
	if (t.kupac) {
		row += "<td>" + t.kupac + "</td>";		
	} else {
		row += "<td>/</td>";
	}
	row += "<td>" + t.objekatId + "</td>";
	row += "</tr>";
	return row;
}

function getPersonalniTreninziZaTrenera() {
	$.ajax({
	    url: "treninzi/trener/personalni/",
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    error: function(error) {
			if (data.status === 403) {
				alert("Niste ulogovani");
				window.location.href="/login.html";
				return;	
			} else if (data.status === 404) {
				$("#table-body").html("<h3>Treninzi nisu pronadjeni</h3>");
			}
		},
	    complete: function(data) {
			
			treninzi = data.responseJSON;
			let tableBody = $("#table-body");
	        tableBody.html("");
	        for (let t of treninzi) {
	            tableBody.append(kreirajRedZaPersonalniTrening(t));
	        }
			
	    }
	});
}

function kreirajRedZaPersonalniTrening(t) {
	let row = "<tr>";
	row += "<td>" + t.naziv + "</td>";
	row += "<td>" + t.tip + "</td>";
	row += "<td>" + t.trajanje + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td>" + t.opis + "</td>";
	row += "<td><img width=50 height=50 src='" + t.slikaURL + "'</td>";
	row += "<td>" + t.cena + "</td>";
	row += "<td>" + t.datumIVremeOdrzavanja + "</td>";
	if (t.kupac) {
		row += "<td>" + t.kupac + "</td>";		
	} else {
		row += "<td>/</td>";
	}
	row += "<td>" + t.objekatId + "</td>";

	let inTwoDays = new Date();
	inTwoDays.setDate(inTwoDays.getDate() + 2);
	if (Date.parse(t.datumIVremeOdrzavanja) < inTwoDays) {
		row += "<td></td>";
	} else {		
		row += "<td><button class='btn btn-danger' onclick='otkaziTrening(" + t.id + ")'>Otkazi trening</button></td>";
	}
	row += "</tr>";
	return row;
}

function getTreninziZaKupcaPoObjekatId() {
	let selektovanObjekatId = $("#sportski-objekti-dropdown").val();
	if (selektovanObjekatId === '0') {
		return;
	}
	
	$.ajax({
	    url: "treninzi/kupac/" + selektovanObjekatId,
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    error: function(error) {
			if (data.status === 403) {
				alert("Niste ulogovani");
				window.location.href="/login.html";
				return;	
			}
		},
	    complete: function(data) {
			$("#treninzi-za-objekat").removeClass("d-none");
			let treninzi = data.responseJSON;
			
			let tableBody = $("#table-body");
			if (treninzi.length === 0) {
				tableBody.append("<h5>Nema dostupnih treninga za ovaj objekat</h5>")
				return;
			}
			
	        tableBody.html("");
	        for (let t of treninzi) {
	            tableBody.append(kreirajRedZTreningPrilikomPrijaveUObjekat(t));
	        }
			
	    }
	});
}

function kreirajRedZTreningPrilikomPrijaveUObjekat(t) {
	let row = "<tr>";
	row += "<td>" + t.naziv + "</td>";
	row += "<td>" + t.tip + "</td>";
	row += "<td>" + t.trajanje + "</td>";
	row += "<td>" + t.opis + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td><img width=50 height=50 src='" + t.slikaURL + "'</td>";
	row += "<td>" + t.datumIVremeOdrzavanja + "</td>";
	row += "<td><button class='btn btn-success' onclick='prijaviKupcaUObjekat(" + t.id + ")'>Izaberi trening</button></td>";
	row += "</tr>";
	return row;
}

function otkaziTrening(treningId) {
	$.ajax({
	    url: "treninzi/otkazi/" + treningId,
	    type: "PUT",
	    contentType: "application/json",
	    dataType: "json",
	    complete: function(data) {
			if (data.status === 403) {
				alert("Niste ulogovani");
				return;	
			}
			
			alert("Trening je uspesno otkazan");
			window.location.reload();
	    }
	});
}

function preuzimanjePodatakaZaRasporedTreningaUSportskomObjektu() {
	const objekatId = getUrlVars().objekatId;
	$.ajax({
        url: "/treninzi/objekat/" + objekatId,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            let treninzi = data.responseJSON;
			let tableBody = $("#table-body");
            tableBody.html("");
            for (let t of treninzi) {
                tableBody.append(kreirajRedZaTreningBezDugmeta(t));
            }
        }
    });
	
}

function kreirajRedZaTreningBezDugmeta(t) {
	let row = "<tr>";
	row += "<td>" + t.naziv + "</td>";
	row += "<td>" + t.tip + "</td>";
	row += "<td>" + t.trajanje + "</td>";
	row += "<td>" + t.opis + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td><img width=50 height=50 src='" + t.slikaURL + "'</td>";
	row += "<td>" + t.datumIVremeOdrzavanja + "</td>";
	if (t.otkazan) {
		row += "<td>Otkazan</td>";		
	} else {
		row += "<td>Aktivan</td>";
	}
	
	if (t.kupac) {
		row += "<td>" + t.kupac + "</td>";		
	} else {
		row += "<td>/</td>";
	}
	
	row += "</tr>";
	return row;
}

function pretragaTreninga(tipTreninga) {
	let kriterijum = $("#kriterijum").val();
	let tekst = $("#pretraga").val();
	
	let filtriraniTreninzi = [];
	if (kriterijum === "objekat") {
		for (let t of treninzi) {
			if (t.objekatId.toLowerCase().includes(tekst.toLowerCase())) {
				filtriraniTreninzi.push(t);
			}
		}
	} else if (kriterijum === "cena") {
		let error = false;
		const podeljenString = tekst.split('-');
		if (podeljenString.length !== 2) {
			error = true;
			filtriraniTreninzi = treninzi;
		}
		
		const minCena = parseInt(podeljenString[0]);
		const maxCena = parseInt(podeljenString[1]);
		
		if (isNaN(minCena) || isNaN(maxCena)) {
			error = true;
			filtriraniTreninzi = treninzi;
		}
		
		if (!error) {
			for (let t of treninzi) {
				if (t.cena >= minCena && t.cena <= maxCena) {
					filtriraniTreninzi.push(t);
				}
			}
		}
	}
	
	const datumOd = $("#datum-od").val();
	if (datumOd) {
		let treninziDatumOd = [];
		for (let t of filtriraniTreninzi) {
				if (t.datumIVremeOdrzavanja > datumOd) {
					treninziDatumOd.push(t);
				}
			}
	
		
		filtriraniTreninzi = treninziDatumOd;
	}
	
	const datumDo = $("#datum-do").val();
	if (datumDo) {
		let treninziDatumDo = [];
		for (let t of filtriraniTreninzi) {
				if (t.datumIVremeOdrzavanja < datumDo) {
					treninziDatumDo.push(t);
				}
			}
	
		
		filtriraniTreninzi = treninziDatumOd;
	}
	
	const rastuceSortiranje = $("#rastuce-sortiranje").is(":checked");
	const sortiranjePo = $("#tip-sortiranja").val();
	if (sortiranjePo === "objekat") {
		if (rastuceSortiranje) {
			filtriraniTreninzi.sort(function(a, b) {
				const nameA = a.objekatId.toUpperCase();
				const nameB = b.objekatId.toUpperCase();
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniTreninzi.sort(function(a, b) {
				const nameA = a.objekatId.toUpperCase();
				const nameB = b.objekatId.toUpperCase();
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "cena") {
		if (rastuceSortiranje) {
			filtriraniTreninzi.sort(function(a, b) {
				return a.cena > b.cena ? 1 : 0;
			});
		} else {
			filtriraniTreninzi.sort(function(a, b) {
				return a.cena < b.cena ? 1 : 0;
			});
		}
	} else if (sortiranjePo === "datum") {
		if (rastuceSortiranje) {
			filtriraniTreninzi.sort(function(a, b) {
				const nameA = a.datumIVremeOdrzavanja;
				const nameB = b.datumIVremeOdrzavanja;
				if (nameA < nameB) {
				  return -1;
				}
				if (nameA > nameB) {
				  return 1;
				}
				
				return 0;
			});
		} else {
			filtriraniTreninzi.sort(function(a, b) {
				const nameA = a.datumIVremeOdrzavanja;
				const nameB = b.datumIVremeOdrzavanja;
				if (nameA < nameB) {
				  return 1;
				}
				if (nameA > nameB) {
				  return -1;
				}
				
				return 0;
			});
		}
	} else if (sortiranjePo === "broj-bodova") {
		if (rastuceSortiranje) {
			filtriraniKorisnici.sort(function(a, b) {
				return a.numberOfPoints > b.numberOfPoints ? 1 : -1;
			});
		} else {
			filtriraniKorisnici.sort(function(a, b) {
				return a.numberOfPoints < b.numberOfPoints ? 1 : -1;
			});
		}
	}
	
	let tableBody = $("#table-body");
	tableBody.html("");
	for (let t of filtriraniTreninzi) {
		if (tipTreninga.toLowerCase() === 'grupni')
	    	tableBody.append(kreirajRedZaGrupniTrening(t));
    	else
    		tableBody.append(kreirajRedZaPersonalniTrening(t));
	}
}
