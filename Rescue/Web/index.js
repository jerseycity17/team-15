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
	// res.render('login.pug', {
	// 	title: 'Login'
	// });
	// renderPage('some title', 'content.pug', res);
	res.render('login.pug', {
		title: 'Login',
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
	// renderPage('Communication', 'communication.pug', res);
	firebase.database().ref('Regions/location_id_here/briefs').once('value', function(snapshot)
	{
	    let c = 0;
		let children = [];
		let done = false;
	    snapshot.forEach(function(childSnapshot) {
	        children.push(childSnapshot.val());    
	        c++;
	        
	        if (c >= childSnapshot.numChildren() && !done)
	        {
				done = true;
				res.render('communication.pug', {
					title: 'Communication',
	                briefs: children
				});
	        }
	    });
	    
	});
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
	// renderPage('Communication', 'communication.pug', res);

	firebase.database().ref('Regions/location_id_here/briefs').once('value', function(snapshot)
	{
	    let c = 0;
		let children = [];
		let done = false;
	    snapshot.forEach(function(childSnapshot) {
	        children.push(childSnapshot.val());    
	        c++;
	        
	        if (c >= childSnapshot.numChildren() && !done)
	        {
				done = true;
				res.render('communication.pug', {
					title: 'Communication',
	                briefs: children
				});
	        }
	    });
	    
	});

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

app.post('/create_brief', function(req, res) {
	var m = new Date();
	var time =
    m.getUTCFullYear() + "" +
    ("0" + (m.getUTCMonth()+1)).slice(-2) + "" +
    ("0" + m.getUTCDate()).slice(-2);
	// var time = "" + date.getYear() + "" + date.getHours() + "" + date.getMinutes();
	var title = req.body.title;
	var text = req.body.msg;
	var priority = req.body.priority;
	var location = req.body.location;
	console.log(time + ", " + text + ", " + priority + ", " + location);
	var newBrief = firebase.database().ref('Regions/' + location +'/briefs').push();
	newBrief.set({
		title: title,
		date: parseInt(time),
		text: text,
		urgency: parseInt(priority)
	});

	firebase.database().ref('Regions/location_id_here/briefs').once('value', function(snapshot)
	{
	    let c = 0;
		let children = [];
		let done = false;
	    snapshot.forEach(function(childSnapshot) {
	        children.push(childSnapshot.val());    
	        c++;
	        
	        if (c >= childSnapshot.numChildren() && !done)
	        {
				done = true;
				res.render('communication.pug', {
					title: 'Communication',
	                briefs: children
				});
	        }
	    });
	    
	});
});

app.get('/new_brief', function(req, res) {
	res.render('create_brief.pug', {
		title: 'New Brief'
	});
});

app.get('/communication', function(req, res) {
	firebase.database().ref('Regions/location_id_here/briefs').once('value', function(snapshot)
	{
	    let c = 0;
		let children = [];
		let done = false;
	    snapshot.forEach(function(childSnapshot) {
	        children.push(childSnapshot.val());    
	        c++;
	        
	        if (c >= childSnapshot.numChildren() && !done)
	        {
				done = true;
				res.render('communication.pug', {
					title: 'Communication',
	                briefs: children
				});
	        }
	    });
	    
	});
	// res.render('communication.pug', {
	// 	title: 'Communication',
	// 	// briefs: firebase.database().ref('Regions/location_id_here/briefs')
	// });
})

app.get('/analysis', function(req, res) {
	// renderPage('Analysis', 'analysis.pug', res);
	res.render('analysis.pug', {
		title: 'Analysis'
	});
})

app.get('/organization', function(req, res) {
	// renderPage('Organization', 'organization.pug', res);
	res.render('organization.pug', {
		title: 'Organization'
	});
})

app.get('/ping_user', function(req, res) {
	console.log('pinging');
	var uId = req.query.id;
	var newBrief = firebase.database().ref('Users/' + uId).update({
		safetyCheck: 1,
	});
	console.log('pinged user' + uId);
	res.render('organization.pug', {
		title: 'Organization'
	});
});

app.listen(3000, function() {
	console.log('Server started on port 3000');
});