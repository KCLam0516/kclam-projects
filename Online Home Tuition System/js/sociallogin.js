window.fbAsyncInit = function() {
    FB.init({
      appId      : '158584558151737',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.12'
    });
      
    FB.AppEvents.logPageView();   
      
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));


var provider = new firebase.auth.FacebookAuthProvider();
 function logFacebook()
{
firebase.auth().signInWithPopup(provider).then(function(result) {

  var token = result.credential.accessToken;
  var user = result.user;

}).catch(function(error) {
  var errorCode = error.code;
  var errorMessage = error.message;
  var email = error.email;
  var credential = error.credential;
});
}

var provider2 = new firebase.auth.GoogleAuthProvider();
function logGoogle()
{
firebase.auth().signInWithPopup(provider2).then(function(result) {

  var token = result.credential.accessToken;
  var user = result.user;

}).catch(function(error) {
  var errorCode = error.code;
  var errorMessage = error.message;
  var email = error.email;
  var credential = error.credential;
});
}

var provider3 = new firebase.auth.TwitterAuthProvider();
function logTwi()
{
firebase.auth().signInWithPopup(provider3).then(function(result) {

  var token = result.credential.accessToken;
  var user = result.user;

}).catch(function(error) {
  var errorCode = error.code;
  var errorMessage = error.message;
  var email = error.email;
  var credential = error.credential;
});
}

var provider4 = new firebase.auth.GithubAuthProvider();
function logGit()
{
firebase.auth().signInWithPopup(provider4).then(function(result) {

  var token = result.credential.accessToken;
  var user = result.user;
  
}).catch(function(error) {
  var errorCode = error.code;
  var errorMessage = error.message;
  var email = error.email;
  var credential = error.credential;
});
}