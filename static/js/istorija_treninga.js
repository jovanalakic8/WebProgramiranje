function prijaviKupcaUObjekat(treningId) {
	$.ajax({
        url: "/treninzi-istorija",
        type: "POST",
        data: JSON.stringify({
			treningId
		}),
        contentType: "application/json",
        dataType: "json",
        complete : function (data) {
            if (data.status !== 200)
                alert(data.responseText);
            else {
                alert("Uspesno ste se prijavili u objekat");
                window.location.href = '/kupac_meni.html';
            }
        }
    });
}

function ucitajIstorijuTreningaZaKupca() {
	$.ajax({
	    url: "treninzi-istorija/kupac",
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    complete: function(response) {
			if (response.status === 403) {
				alert("Niste ulogovani");
				return;
			}
			
			let tableBody = $("#istorija-table-body");
			if (response.responseJSON.length === 0) {
				tableBody.append("<h5>Nema dosupnih treninga za proteklih mesec dana</h5>")
				return;
			}
			
	        tableBody.html("");
	        for (let t of response.responseJSON) {
	            tableBody.append(kreirajRedZaIstorijuTreningaKupca(t));
	        }
			
	    }
	});
}

function kreirajRedZaIstorijuTreningaKupca(t) {
	let row = "<tr>";
	row += "<td>" + t.datumIVremePrijave + "</td>";
	row += "<td>" + t.trening + "</td>";
	row += "<td>" + t.trener + "</td>";
	row += "<td>" + t.objekat + "</td>";
	return row;
}

function ucitajTrenereKojiSuBiliUMenadzerovomObjektu() {
	$.ajax({
	    url: "treninzi-istorija/pregled-trenera",
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    complete: function(response) {
			if (response.status === 403) {
				alert("Niste ulogovani");
				return;
			}
			
			let treneri = response.responseJSON;
			
			let tableBody = $("#table-body");
            tableBody.html("");
            for (let trener of treneri) {
                tableBody.append(kreirajRedZaTrenera(trener));
            }
			
	    }
	});
}


function kreirajRedZaTrenera(trener) {
	let row = "<tr>";
	row += "<td>" + trener.name + "</td>";
	row += "<td>" + trener.lastName + "</td>";
	row += "<td>" + trener.userName + "</td>";
	row += "<td>" + trener.role.toLowerCase() + "</td>";
	row += "<td>" + trener.birthDate + "</td>";
	row += "<td>" + trener.sex.toLowerCase() + "</td>";
	
	return row;
}

function ucitajKupceKojiSuBiliUMenadzerovomObjektu() {
	$.ajax({
	    url: "treninzi-istorija/pregled-kupaca",
	    type: "GET",
	    contentType: "application/json",
	    dataType: "json",
	    complete: function(response) {
			if (response.status === 403) {
				alert("Niste ulogovani");
				return;
			}
			
			let kupci = response.responseJSON;
			
			let tableBody = $("#table-body");
            tableBody.html("");
            for (let kupac of kupci) {
                tableBody.append(kreirajRedZaTrenera(kupac));
            }
			
	    }
	});
}