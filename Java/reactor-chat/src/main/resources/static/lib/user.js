var user = new Vue({
	el: "#user",
	data: {
		isLogin: false,
		own: {
			name: "test"
		},
		other: {
			count: 0,
			users: []
		}
	},
	methods: {
		join: function() {
			var request = window.superagent;
			request
				.post("/users/add")
				.send({
					name: this.own.name,
					type: "Cold"
				})
				.end((err, res) => {
					this.isLogin = true;
					message.name = this.own.name; // 本当はイベントでやるべきだけど面倒なので直接
				})
		}
	}
});

var userEvent = new EventSource("/users");

userEvent.addEventListener("message", function(event) {
	var data = JSON.parse(event.data);
	user.other = data;
});