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
	var pluginName = "ajaxDisplaytag", defaults = {
		url : undefined,
		loadedHandler : undefined,
		params : {}
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
		//default ajax=true
		$(this).data('link', '?ajax=true');
		this.init();
	}

	Plugin.prototype = {

		init : function() {
			
			var self = this._self, element = this.element;
			// validate options
			if (!self.options.url) {
				console.log('[ajax-displaytag] Invalid url parameter !!');
			}
			
			var params = self.options.params;
			if (typeof params == 'function') {
				params = self.options.params.call(self);
			}
			$(element).load(self.options.url + '?ajax=true', params, function() {
				self.pageLoadedHandler.call(self);
			});
		},
		
		pageLoadedHandler : function() {
			var self = this._self, element = this.element;
			self.ajaxDisplayTagLoadHandler.call(self);
			if (typeof self.options.loadedHandler == 'function') {
				self.options.loadedHandler.call(self);
			}
		},
		
		ajaxDisplayTagLoadHandler : function() {
			var self = this._self, element = this.element;
			$(element).find("table th.sortable").each(function() {
				$(this).click(function() {
					// "this" is scoped as the sortable th element
					var link = $(this).find("a").attr("href");
					$(self).data('link', link);
					var params = self.options.params;
					if (typeof params == 'function') {
						params = self.options.params.call(self);
					}
					$(element).load(self.options.url + link, params, function() {
						self.pageLoadedHandler.call(self);
					});
					return false;
				});
				
			});
			
			$(element).find(".pagination a").each(function() {
				$(this).click(function() {
					var link = $(this).attr("href");
					$(self).data('link', link);
					var params = self.options.params;
					if (typeof params == 'function') {
						params = self.options.params.call(self);
					}
					$(element).load(self.options.url + link, params, function() {
						self.pageLoadedHandler.call(self);
					});
					return false;
				});
			});
		}
	};

	// A really lightweight plugin wrapper around the constructor,
	// preventing against multiple instantiations
	$.fn[pluginName] = function(options) {
		return this.each(function() {
			$.data(this, "plugin_" + pluginName, new Plugin(this, options));
		});
	};

})(jQuery, window, document);