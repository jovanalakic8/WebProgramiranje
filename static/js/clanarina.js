function ucitajPonudeClanarina() {
	$.ajax({
        url: "/clanarine/ponuda",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            clanarine = data.responseJSON;
            let tableBody = $("#table-body");
            tableBody.html("");
            for (let clanarina of clanarine) {
                tableBody.append(kreirajRedZaClanarinu(clanarina));
            }
        }
    });
}

function kreirajRedZaClanarinu(clanarina) {
	let row = "<tr>";
	row += "<td>" + clanarina.naziv + "</td>";
	row += "<td>" + clanarina.tip + "</td>";
	row += "<td>" + clanarina.cena + "</td>";
	row += "<td>" + clanarina.brojDanaVazenja + "</td>";
	if (clanarina.brojTermina ) {
		row += "<td>" + clanarina.brojTermina + "</td>";		
	} else {
		row += "<td>neograniceno</td>";
	}
	row += "<td><a class='btn btn-primary' href='/clanarina_kupovina.html?ponudaId=" + clanarina.id + "'>Kupi</a></td>";
	return row;
}

function preuzmiPodatkeZaClanarinu() {
	const ponudaId = getUrlVars().ponudaId;
	
	$.ajax({
        url: "/clanarine/ponuda/" + ponudaId,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
            let clanarina = data.responseJSON;
            $("#naziv").val(clanarina.naziv);
            $("#tip").val(clanarina.tip);
            $("#cena").val(clanarina.cena);
            $("#vaziDo").val(clanarina.vaziDo);
            
            $("#nazivTekst").val(clanarina.naziv);
            $("#tipTekst").val(clanarina.tip);
            $("#cenaPrikaz").val(clanarina.cena);
            $("#vaziDoPrikaz").val(clanarina.vaziDo);
            
            if (clanarina.brojTermina) {
        	    $("#brojTerminaTekst").val(clanarina.brojTermina);
			} else {
				$("#brojTerminaTekst").val("neograniceno");
			}
			
			$("#brojTermina").val(clanarina.brojTermina);
        }
        
    });
}

function potvrdiKupovinu() {
	const ponudaId = getUrlVars().ponudaId;
	
	let fromData = getFormData($("#clanarinaForm"));
	fromData.id = ponudaId;
    let json = JSON.stringify(fromData);
    
    $.ajax({
        url: "/clanarine/kupovina",
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        complete : function (data) {
            if(data.status == 400)
                alert(data.responseText);
            else{
                alert("Uspesna kupovina");
                window.location.replace("/kupac_meni.html");
            }
        }
    });
}

function pronadjiAktivnuClanarinuZaKupca() {
	$.ajax({
        url: "/clanarine/aktivna",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        complete: function(data) {
			let tableBody = $("#table-body");
			
            let clanarina = data.responseJSON;
            if (clanarina === null) {
				let tabela = $("#aktivna-clanarina-tabela");
				tabela.html("<h5>Nemate aktivnu clanarinu</h5>");
			} else {
	            let row = "<tr>";
				row += "<td>" + clanarina.tip + "</td>";
				row += "<td>" + clanarina.status + "</td>";
				row += "<td>" + clanarina.vaziDo + "</td>";
				row += "<td>" + clanarina.datumPlacanja + "</td>";
				if (clanarina.ukupanBrojTermina !== null) {
					row += "<td>" + clanarina.brojPreostalihTermina + "</td>";		
				} else {
					row += "<td>neograniceno</td>";
				}
				row += "<td>" + clanarina.cena + "</td>";
				tableBody.html(row);
			}
        }
    });
}
