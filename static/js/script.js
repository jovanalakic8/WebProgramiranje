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
