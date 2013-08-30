function hasChecked(checkbox) {
	if (checkbox) {
		if (checkbox.length) {
			for (i = 0; i < checkbox.length; i++) {
				if (checkbox[i].checked) {
					return true;
				}
			}
		} else {
			if (checkbox.checked) {
				return true;
			}
		}
	}
	return false;
}

function toggleCheckAll(checker, checkbox) {
	if (checkbox) {
		if (checkbox.length) {
			for (i = 0; i < checkbox.length; i++) {
				checkbox[i].checked = checker.checked;
			}
		} else {
			checkbox.checked = checker.checked;
		}
	}
}

function clearForm(ele) {
	$(ele).find(':input').each(function() {
		switch (this.type) {
		case 'password':
		case 'select-multiple':
		case 'select-one':
		case 'text':
		case 'textarea':
			$(this).val('');
			break;
		case 'checkbox':
		case 'radio':
			this.checked = false;
		}
	});
}

function changePageSize(select) {
	var newURL = updateURLParameter(window.location.href, 'ps', $(select).val());
	window.location.href = newURL;
}

function updateURLParameter(url, param, paramVal) {
	var TheAnchor = null;
	var newAdditionalURL = "";
	var tempArray = url.split("?");
	var baseURL = tempArray[0];
	var additionalURL = tempArray[1];
	var temp = "";

	if (additionalURL) {
		var tmpAnchor = additionalURL.split("#");
		var TheParams = tmpAnchor[0];
		TheAnchor = tmpAnchor[1];
		if (TheAnchor)
			additionalURL = TheParams;

		tempArray = additionalURL.split("&");

		for (i = 0; i < tempArray.length; i++) {
			if (tempArray[i].split('=')[0] != param) {
				newAdditionalURL += temp + tempArray[i];
				temp = "&";
			}
		}
	} else {
		var tmpAnchor = baseURL.split("#");
		var TheParams = tmpAnchor[0];
		TheAnchor = tmpAnchor[1];

		if (TheParams)
			baseURL = TheParams;
	}

	if (TheAnchor)
		paramVal += "#" + TheAnchor;

	var rows_txt = temp + "" + param + "=" + paramVal;
	return baseURL + "?" + newAdditionalURL + rows_txt;
}

/** used in validtor-rules.xml */
function addErrorMessage(field, message) {
	$(field).closest('.control-group').addClass('error');
	$(field).closest('.control-group').find('.help-inline').remove();
	if ($(field).parent().hasClass("controls")) {
		$(field).after("<span class='help-inline'>" + message + "</span>");
	} else {
		$(field).parent().after("<span class='help-inline'>" + message + "</span>");
	}
    
    if (field.type == 'text' ||
            field.type == 'textarea' ||
            field.type == 'password') {
        $(field).keyup(function() {
	    		$(field).closest('.control-group').removeClass('error');
	    		$(field).closest('.control-group').find('.help-inline').remove();
        });
    }

    if (field.type == 'file' ||
            field.type == 'select-one' ||
            field.type == 'radio') {
    		$(field).change(function() {
    			$(field).closest('.control-group').removeClass('error');
    			$(field).closest('.control-group').find('.help-inline').remove();
    		});
    }
}
//validate custom field
function checkRequired(field, msg) {
    var isValid = true;
    if (field.type == 'text' ||
        field.type == 'textarea' ||
        field.type == 'file' ||
        field.type == 'select-one' ||
        field.type == 'radio' ||
        field.type == 'password') {
            
    		var value = '';
        // get field's value
        if (field.type == "select-one") {
            var si = field.selectedIndex;
            if (si >= 0) {
                value = field.options[si].value;
            }
        } else {
            value = field.value;
        }
            
        if (value.trim().length == 0) {
            addErrorMessage(field, msg);
            isValid = false;
        }
    }
    return isValid;
}

function confirmMessage(msg, cb) {
	bootbox.confirm(msg, cb);
	return false;
}
