<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="logView.title" /></title>
<meta name="menu" content="AdminMenu" />
<meta name="decorator" content="popup_window" />
<script type="text/javascript" src="<c:url value='/scripts/lib/sockjs-0.3.4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/stomp.js'/>"></script>
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="logView.heading" />
	</h2>
	<div id="logView" class="well" style="overflow: auto; height: 300px"></div>

</div>

<script>
	var url = '<c:url value="/logStream"/>';
    var ws = new SockJS(url);
    var client = Stomp.over(ws);
    var connectCallback = function() {
        // called back after the client is connected and authenticated to the STOMP server
	    	var callback = function(message) {
	            // called when the client receives a STOMP message from the server
	    		if (message.body) {
	            	var divObj = $('#logView');
	    			divObj.html(divObj.html() + '<br/>' + message.body);
	    			divObj.animate({scrollTop : divObj.prop('scrollHeight') - divObj.height()}, 10);
			} else {
	        		alert("got empty message");
			}
		};
	    	
	    	var subscription = client.subscribe('/topic/logStream', callback);
	};
	var errorCallback = function(error) {
	    // display the error's message header:
	    alert(error.headers.message);
	};
	
	client.connect('test', 'test', connectCallback, errorCallback);
    
    
</script>