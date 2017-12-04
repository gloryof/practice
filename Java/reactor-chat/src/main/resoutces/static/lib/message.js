var message = new Vue({
	el: "#message",
	data: {
		messages: []
	}
});

var messageEvent = new EventSource("/messages");

messageEvent.addEventListener("message", function(event) {
	var data = JSON.parse(event.data);
	message.messages.push(data);
});