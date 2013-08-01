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

		this.init();
	}

	Plugin.prototype = {

		init : function() {
			//validate options
			if (!this.options.type) {
				console.log('[lookup] Invalid type parameter !!');
			}
			if (!this.options.displayProperty) {
				console.log('[lookup] Invalid displayProperty parameter !!');
			}
			
			var type = this.options.type, displayProperty = this.options.displayProperty, handler = this.options.handler, element = this.element;
			
			// Place initialization logic here
			// You already have access to the DOM element and
			// the options via the instance, e.g. this.element
			// and this.options
			// you can add more functions like the one below and
			// call them like so: this.yourOtherFunction(this.element,
			// this.options).
			//register typeahead
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
					return json[displayProperty].replace(regex, "<strong>$1</strong>");
				},
				matcher : function(item) {
					var json = jQuery.parseJSON(item);
					return json[displayProperty].toLocaleLowerCase().indexOf(
							this.query.toLocaleLowerCase()) != -1;
				},
				sorter : function(items) {
					return items.sort();
				},
				updater : function(item) {
					var json = jQuery.parseJSON(item);
					if (typeof handler == 'function') {
						handler.call(this.element, json);
					}
					return json[displayProperty];
				}
			};
			$(element).typeahead(typeaheadOption);
			
			$(element).on('change', function() {
				if ($(element).val() == '') {
					if (typeof handler == 'function') {
						handler.call(element, undefined);
					}
				}
			});
			
			//register popup
			$(element).wrap('<div class="input-append"/>');
			$(element).after('<a class="btn"> <i class="icon-search"></i></a>');
		},

		yourOtherFunction : function(el, options) {
			// some logic
		}
	};

	// A really lightweight plugin wrapper around the constructor,
	// preventing against multiple instantiations
	$.fn[pluginName] = function(options) {
		return this
				.each(function() {
					if (!$.data(this, "plugin_" + pluginName)) {
						$.data(this, "plugin_" + pluginName, new Plugin(this,
								options));
					}
				});
	};

})(jQuery, window, document);