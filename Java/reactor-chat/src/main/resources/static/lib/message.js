var message = new Vue({
	el: "#message",
	data: {
		name: "",
		message: "",
		messages: []
	},
	computed: {
		isSendable: function() {
			return (this.name != "" && this.name != null);
		}
	},
	methods: {
		submitMessage: function() {
			var request = window.superagent;
			request
				.post("/messages/add")
				.send({
					username: this.name,
					message: this.message
				})
				.end((err, res) => {
					this.message = "";
				})
		},
		submitMessageDelay: function() {
			var request = window.superagent;
			request
				.post("/messages/addDelay")
				.send({
					username: this.name,
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
	message.messages.unshift(data);
});