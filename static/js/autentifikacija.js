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