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
