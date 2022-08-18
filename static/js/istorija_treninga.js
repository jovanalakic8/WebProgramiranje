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