var message = new Vue({
	el: "#message",
	data: {
		name: "test " + new Date(),
		message: "",
		messages: []
	},
	methods: {
		submitMessage: function() {
			var request = window.superagent;
			request
				.post("/messages/add")
				.send({
					name: this.name,
					message: this.message
				})
				.end((err, res) => {
					this.message = "";
				})
		}
	}
});

var messageEvent = new EventSource("/messages");

messageEvent.addEventListener("message", function(event) {
	var data = JSON.parse(event.data);
	message.messages.push(data);
});