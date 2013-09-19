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
			
			var self = this._self, $self = $(this._self), $element = $(this.element);
			// validate options
			if (!self.options.url) {
				console.log('[ajax-displaytag] Invalid url parameter !!');
			}
			
			var params = self.options.params;
			if (typeof params == 'function') {
				params = self.options.params.call(self);
			}
			$.get(self.options.url + '?ajax=true', params, function( data ) {
				$element.html( data );
				self.pageLoadedHandler.call(self);
			})
			//$element.load(self.options.url + '?ajax=true', params);
			//register default event
			$element.on('click', 'table th.sortable', function() {
				// "this" is scoped as the sortable th element
				var link = $(this).find("a").attr("href") + '&ajax=true';
				$self.data('link', link);
				$element.load(self.options.url + link, function() {
					self.pageLoadedHandler.call(self);
				});
				return false;
			});
			
			$element.on('click', '.pagination a', function() {
				var link = $(this).attr("href") + '&ajax=true';
				$self.data('link', link);
				$element.load(self.options.url + link, function() {
					self.pageLoadedHandler.call(self);
				});
				return false;
			});
		},
		
		pageLoadedHandler : function() {
			var self = this._self, element = this.element;
			if (typeof self.options.loadedHandler == 'function') {
				self.options.loadedHandler.call(self);
			}
		},
		
		getLink : function() {
			var $self = $(this._self);
			return $self.data('link');
		},
		updateTable : function(params) {
			var self = this._self, $self = $(this._self), $element = $(this.element);
			$.get(self.options.url + '?ajax=true', params, function( data ) {
				$element.html( data );
			});
		}
	};

	// A really lightweight plugin wrapper around the constructor,
	// preventing against multiple instantiations
	$.fn[pluginName] = function(options) {
		if (!$.data(this, "plugin_" + pluginName)) {
			$.data(this, "plugin_" + pluginName, new Plugin(this, options));
		}
		return $.data(this, "plugin_" + pluginName); 
	};

})(jQuery, window, document);