var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');

var app = express();

// View Engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

// Set Static Path
app.use(express.static(path.join(__dirname, 'static')));

app.get('/', function(req, res) {
	// res.send('Hello World');
	res.render('index', {
		title: 'Login'
	});
});

app.post('/login', function(req, res) {
	var newUser = {
		email: req.body.email,
		password: req.body.password
	}
	console.log(newUser);
})

app.listen(3000, function() {
	console.log('Server started on port 3000');
})