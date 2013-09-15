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
		btnSearch : '<a class="btn add-on"> <i class="icon-search"></i></a>', //valid html for  button, 
		showBtnSearch : true,
		appendBtnAndText : true,
		btnSearchCondition : undefined, //function that return json condition to be pass as param.
		handler : function(json) {
		},
		autocompleteUrl : function () {
			return 'json/' + this.type;
		},
		popupUrl : function (withCondition) {
			url = 'popup/' + this.type;
			if (withCondition && typeof this.btnSearchCondition == 'function') {
				url += '?' + jQuery.param(this.btnSearchCondition.call(this));
			}
			return url;
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
			var self = this._self, element = this.element, $element = $(this.element);
			//var type = this.options.type, displayProperty = this.options.displayProperty, handler = this.options.handler, element = this.element, , btnSearchCondition = this.options.btnSearchCondition;

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
					url = self.options.autocompleteUrl.call(self.options);
					$.ajax({
						dataType : "json",
						url : url,
						data : {
							'q' : query
						},
						success : function(data) {
							jsonStringList = [];
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
				minLength : 2,
				updater : function(item) {
					var json = jQuery.parseJSON(item);
					if (typeof self.options.handler == 'function') {
						self.options.handler.call(element, json);
					}
					$element.data('lookupKeyValue', self.getSelectProperty(json));
					return self.getSelectProperty(json);
				}
			};
			$element.typeahead(typeaheadOption);

			$element.on('focusout', function() {
				// reset code field, if text is not equal to original name store
				// in element.
				if ($element.val() == '' || ($element.data('lookupKeyValue') && $element.data('lookupKeyValue') != $element.val())) {
					//remove data
					$element.removeData('lookupKeyValue');
					if (typeof self.options.handler == 'function') {
						self.options.handler.call(element, undefined);
					}
				}
			});
			
			//add search button
			if (this.options.showBtnSearch) {
				// register popup
				if (this.options.appendBtnAndText) {
					$element.wrap('<div class="input-append"/>');
				}
				//bind btnSearch event
				//var $modal = this.initModal(this.element, this.options);
				var $modal = $('<div class="modal container hide fade" tabindex="-1"></div>');
				var btnSearch = $(this.options.btnSearch).on('click', function() {
					$('body').modalmanager('loading');
					var url = self.options.popupUrl.call(self.options, true);
					
					$modal.load(url, '', function() {
						$modal.modal();
					});
				});
				$element.after(btnSearch);
				$element.after($modal);
				
				$modal.on('submit', 'form', function(event) {
					$modal.modal('loading');
					event.preventDefault();
					$modal.load($(this).prop('action'), $(event.target).serialize(), function() {
					});
				});
				
				$modal.on('click', 'th.sortable a, div.pagination li a', function(event) {
					$modal.modal('loading');
					event.preventDefault();
					$modal.load(self.options.popupUrl.call(self.options) + $(event.target).attr('href'), '', function() {
					});
				});
				
				$modal.on('change', 'select[name=ps]', function(event) {
					$modal.modal('loading');
					event.preventDefault();
					$modal.load(self.options.popupUrl.call(self.options) + '?ps=' + $(event.target).val(), '', function() {
					});
				});
				
				$modal.on('click', '#selectBtn', function(event) {
					event.preventDefault();
					var json = jQuery.parseJSON($modal.find('.modal-body input[name=radio]:checked').val());
					if (json) {
						if (typeof self.options.handler == 'function') {
							$element.val(self.getSelectProperty(json));//update text field
							self.options.handler.call(element, json);
						}
						$modal.modal('hide');
					}
				})
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