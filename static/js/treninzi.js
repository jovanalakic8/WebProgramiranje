function preuzmiPodatkeOSportskomObjektu () {
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
	row += "<td><a href='/izmena_treninga.html?treningId=" + t.id + "' class='btn btn-primary'>" + "Izmena" + "</a></td>";
	row += "</tr>";
	return row;
}
