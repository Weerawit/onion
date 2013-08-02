// the semi-colon before function invocation is a safety net against concatenated
// scripts and/or other plugins which may not be closed properly.
;
(function($, window, document, undefined) {

	// undefined is used here as the undefined global variable in ECMAScript 3
	// is
	// mutable (ie. it can be changed by someone else). undefined isn't really
	// being
	// passed in so we can ensure the value of it is truly undefined. In ES5,
	// undefined
	// can no longer be modified.

	// window and document are passed through as local variable rather than
	// global
	// as this (slightly) quickens the resolution process and can be more
	// efficiently
	// minified (especially when both are regularly referenced in your plugin).

	// Create the defaults once
	var pluginName = "lookup", defaults = {
		type : undefined,
		displayProperty : undefined,
		selectProperty : undefined, //incase , select value to display is diff from custom display list.
		btnSearch : '<a class="btn"> <i class="icon-search"></i></a>', //valid html for  button, 
		showBtnSearch : true,
		btnSearchCondition : undefined, //fucntion that return json condition to be pass as param.
		handler : function(json) {
		}
	};

	// The actual plugin constructor
	function Plugin(element, options) {
		this.element = element;

		// jQuery has an extend method which merges the contents of two or
		// more objects, storing the result in the first object. The first
		// object
		// is generally empty as we don't want to alter the default options for
		// future instances of the plugin
		this.options = $.extend({}, defaults, options);
		
		this._defaults = defaults;
		this._name = pluginName;
		this._self = this;
		this.init();
	}

	Plugin.prototype = {

		init : function() {
			// validate options
			if (!this.options.type) {
				console.log('[lookup] Invalid type parameter !!');
			}
			if (!this.options.displayProperty) {
				console.log('[lookup] Invalid displayProperty parameter !!');
			}
			if (!this.options.selectProperty) {
				this.options.selectProperty = this.options.displayProperty;
			}

			var type = this.options.type, displayProperty = this.options.displayProperty, handler = this.options.handler, element = this.element, self = this._self, btnSearchCondition = this.options.btnSearchCondition;

			// Place initialization logic here
			// You already have access to the DOM element and
			// the options via the instance, e.g. this.element
			// and this.options
			// you can add more functions like the one below and
			// call them like so: this.yourOtherFunction(this.element,
			// this.options).
			// register typeahead
			typeaheadOption = {
				source : function(query, process) {
					jsonStringList = [];
					url = 'json/' + type + '/';
					$.ajax({
						dataType : "json",
						url : url,
						data : {
							'q' : query
						},
						success : function(data) {
							$.each(data, function(i, obj) {
								jsonStringList.push(JSON.stringify(obj));
							});
							process(jsonStringList);
						}
					});
				},
				highlighter : function(item) {
					var json = jQuery.parseJSON(item);
					var regex = new RegExp('(' + this.query + ')', 'gi');
					//highlight with bold font
					return self.getDisplayProperty(json).replace(regex, "<strong>$1</strong>");
				},
				matcher : function(item) {
					var json = jQuery.parseJSON(item);
					return self.getDisplayProperty(json).toLocaleLowerCase().indexOf(this.query.toLocaleLowerCase()) != -1;
				},
				sorter : function(items) {
					return items.sort();
				},
				updater : function(item) {
					var json = jQuery.parseJSON(item);
					if (typeof handler == 'function') {
						handler.call(element, json);
					}
					$(element).data('lookup.displayProperty', self.getSelectProperty(json));
					return self.getSelectProperty(json);
				}
			};
			$(element).typeahead(typeaheadOption);

			$(element).on('focusout', function() {
				// reset code field, if text is not equal to original name store
				// in element.
				if ($(element).val() == '' || ($(element).data('lookup.displayProperty') && $(element).data('lookup.displayProperty') != $(element).val())) {
					if (typeof handler == 'function') {
						handler.call(element, undefined);
					}
				}
			});
			
			//add search button
			if (this.options.showBtnSearch) {
				// register popup
				$(element).wrap('<div class="input-append"/>');
				//bind btnSearch event
				var modalEl = this.initModal(this.element, this.options);
				var btnSearch = $(this.options.btnSearch).on('click', function() {
					var url = 'popup/' + type + '?' + jQuery.param(btnSearchCondition.call(this.element));
					var options = {remote : url, 
							modalOverflow: true,
							width : '80%'};
					modalEl.modal(options);
					modalEl.modal('show');
				});
				$(element).after(btnSearch);
				$(element).after(modalEl);
				
				//handle form submit
				var handleSubmit = function(event) {
					event.preventDefault();
					modalEl.find('.modal-body').load($(this).prop('action'), $(this).serialize(), function complete() {
						//rebind event after page re-load.
						modalEl.find('.modal-body form').on('submit', handleSubmit);
					});
				}
				//when modal is shown update event form submit.
				modalEl.on('shown', function() {
					modalEl.find('.modal-body form').on('submit', handleSubmit);
				});
			}
		},
		/**
		 * function the format text for each element is list, for selection
		 */
		getDisplayProperty : function(json) {
			if (typeof this.options.displayProperty == 'function') {
				return this.options.displayProperty.call(this.element, json);
			} else {
				return json[this.options.displayProperty];
			}
		}, 
		/**
		 * format the selected value and put in to text field
		 */
		getSelectProperty : function(json) {
			if (typeof this.options.selectProperty == 'function') {
				return this.options.selectProperty.call(this.element, json);
			} else {
				return json[this.options.selectProperty];
			}
		},
		/**
		 * Create div for modal dialog, 
		 */
		initModal : function(el, options) {
			var self = this;
			var modalId = $(el).prop('id') + 'popupModal';
			var modal = '<div id="' + modalId +'" class="modal hide fade">' +
			'<div class="modal-header">' +
			'<h3>Popup </h3>' +
			'</div>' +
			'<div class="modal-body"/>' +
			'<div class="modal-footer"/>'+
			'</div>';
			var modalEl = $(modal);
			var btnCloseEl = $('<button type="button" class="close">&times;</button>');
			btnCloseEl.on('click', function() {
				modalEl.modal('hide');
			});
			var btnSelectEl = $('<a href="#" class="btn btn-primary">Select</a>');
			btnSelectEl.on('click', function() {
				//update selected item
				if (typeof options.handler == 'function') {
					var json = jQuery.parseJSON(modalEl.find('.modal-body input[name=radio]:checked').val());
					$(el).val(self.getSelectProperty(json));//update text field
					options.handler.call(el, json);
				}
				modalEl.modal('hide');
			});
			var btnCancel = $('<a href="#" class="btn">Close</a>');
			btnCancel.on('click', function() {
				modalEl.modal('hide');
			})
			
			modalEl.find('.modal-header').prepend(btnCloseEl);
			modalEl.find('.modal-footer').append(btnSelectEl, btnCancel);
			return modalEl;
		}
	};

	// A really lightweight plugin wrapper around the constructor,
	// preventing against multiple instantiations
	$.fn[pluginName] = function(options) {
		return this.each(function() {
			if (!$.data(this, "plugin_" + pluginName)) {
				$.data(this, "plugin_" + pluginName, new Plugin(this, options));
			}
		});
	};

})(jQuery, window, document);