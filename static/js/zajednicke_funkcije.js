function getFormData($form) {
	let unindexedArray = $form.serializeArray();
	let indexedArray = {};
	
	$.map(unindexedArray, function(n, i){
		indexedArray[n['name']] = n['value'];
    });

    return indexedArray;
}

function getUrlVars() {
    let vars = {};
    let parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function getMultipartFormData($form) {
	var unindexedArray = $form.serializeArray();
	let formData = new FormData();
	
	$.map(unindexedArray, function(n, i){
		formData.append(n['name'], n['value']);
    });

    return formData;
}