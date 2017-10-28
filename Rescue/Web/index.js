var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');
const firebase = require('firebase');
const fs = require('fs');

var app = express();

// View Engine
app.set('view engine', 'pug');
app.set('views', path.join(__dirname, 'views'));

// Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

// Set Static Path
app.use(express.static(path.join(__dirname, 'static')));

// set up firebase
var config = {
  apiKey: "AIzaSyAZwcjPfmUrrurbK03aIX-7s7ydnP7dCuo",
  authDomain: "project-380236083170.firebaseapp.com",
  databaseURL: "https://rescue-cf4dc.firebaseio.com",
  // storageBucket: "<BUCKET>.appspot.com",
};

firebase.initializeApp(config);

// var database = firebase.database();
// var userId = null

// var userId = firebase.auth().currentUser.uid;
// return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
//   var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
//   // ...
// });

function renderPage(title, filename, res) {
	fs.readFile('views/' + filename, 'utf8', function (err, data) {
	    if (err) throw err;
	    // var fn = jade.compile(data);
	    // var html = fn({name:'Oleg'});
	    // console.log(html);
	    res.render('page.pug', {
			title: title,
			content: data
		});
	});
}

app.get('/', function(req, res) {
	// res.send('Hello World');
	res.render('login.pug', {
		title: 'Login'
	});
	// res.sendfile(__dirname + '/views/index.html');
});

app.post('/login', function(req, res) {
	console.log("pressed login");
	var email = req.body.email;
	var password = req.body.password;
	firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
	    // Handle Errors here.
	    var errorCode = error.code;
	    var errorMessage = error.message;
	});
	console.log("Logged in as " + email);
	renderPage('Communication', 'communication.pug', res);
});

app.post('/register', function(req, res) {
	var email = req.body.email;
	var password = req.body.password;
	var userId = "dummyuserid";
	// login to firebase
	firebase.auth().createUserWithEmailAndPassword(email, password).then(function(user) {
		userId = user.uid;
		var first = req.body.first;
		var last = req.body.last;
		var phone = req.body.phone;

		// add user information to database
		firebase.database().ref('Users/' + userId).set({
			first: first,
			last: last,
			phone: phone
		});
	});
	renderPage('Communication', 'communication.pug', res);

	// res.render('briefings', {title: 'Briefings'});
	console.log("Created new user with email " + email);
});

app.post('/logout', function(req, res) {
	firebase.auth().signOut().then(function() {
	// Sign-out successful.
	console.log("Successfully signed out")
	}).catch(function(error) {
		// An error happened.
	});
	res.render('login.pug', {
		title: 'Login'
	});
});

app.listen(3000, function() {
	console.log('Server started on port 3000');
});