var user = new Vue({
	el: "#user",
	data: {
		own: {
			name: "test"
		},
		other: {
			count: 0,
			users: []
		}
	},
});

var userEvent = new EventSource("/users");

userEvent.addEventListener("message", function(event) {
	var data = JSON.parse(event.data);
	user.other = data;
});