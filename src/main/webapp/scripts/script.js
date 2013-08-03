
// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {
    var bValid = true;
    var focusField = null;
    var i = 0;
    var fields = new Array();
    oRequired = new required();

    for (x in oRequired) {
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
           if (i == 0)
              focusField = form[oRequired[x][0]];

           fields[i++] = oRequired[x][1];

           bValid = false;
        }
    }

    if (fields.length > 0) {
       focusField.focus();
       alert(fields.join('\n'));
    }

    return bValid;
}

// This function is a generic function to create form elements
function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name);
    e.setAttribute("type", type);
    e.setAttribute("id", id);
    e.setAttribute("value", value);
    parent.appendChild(e);
}

/** popup */
var popupHandler = [];

function openPopup(url, param, handler) {
	var options = {remote : url + '?' + jQuery.param(param)};
	$('#popupModal').modal(options);
	$('#popupModal').modal('show');
	popupHandler.push(handler);
};

function closePopup() {
	var handler = popupHandler.pop();
	if (typeof handler == "function") {
	}
	$('#popupModal').modal('hide');
};

function selectAndClosePopup(jsonObject) {
	var handler = popupHandler.pop();
	if (typeof handler == "function") {
		popupHandler.call(this, jsonObject);
	}
	$('#popupModal').modal('hide');
};

