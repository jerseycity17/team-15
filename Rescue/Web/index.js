const http = require('http');
const fs = require('fs');
const url = require('url');
const firebase = require('firebase');

const hostname = '127.0.0.1';
const port = 3000;

// set up firebase
var config = {
  apiKey: "AIzaSyAZwcjPfmUrrurbK03aIX-7s7ydnP7dCuo",
  authDomain: "project-380236083170.firebaseapp.com",
  databaseURL: "https://rescue-cf4dc.firebaseio.com",
  // storageBucket: "<BUCKET>.appspot.com",
};
firebase.initializeApp(config);

var database = firebase.database();
var userId = null


// var userId = firebase.auth().currentUser.uid;
// return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
//   var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
//   // ...
// });

const server = http.createServer((req, res) => {
	var q = url.parse(req.url, true);
    var filename = "." + q.pathname;
    fs.readFile(filename, function(err, data) {
      if (err) {
        res.writeHead(404, {'Content-Type': 'text/html'});
        return res.end("404 Not Found");
      }  
      res.writeHead(200, {'Content-Type': 'text/html'});
      res.write(data);
      return res.end();
    });
});

server.listen(port, hostname, () => {
	console.log('Server started on port ' + port);
});

// fs.readFile('index.html', (err, html) => {
// 	if (err) {
// 		throw err;
// 	}
// 	const server = http.createServer((req, res) => {
// 		res.statusCode = 200;
// 		res.setHeader('Content-type', 'text/html');
// 		res.write(html);
// 		res.end();
// 	});

// 	server.listen(port, hostname, () => {
// 		console.log('Server started on port ' + port);
// 	});
// });