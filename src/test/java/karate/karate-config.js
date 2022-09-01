function fn() {
	var config = {
		baseUrl: 'http://localhost:8080'
	};
	karate.configure('connectTimeout', 30000);
	karate.configure('readTimeout', 30000);
}