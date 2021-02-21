document.querySelector('.close').addEventListener("click", function() {
	document.querySelector('.app-container').style.display = "none";
});

document.getElementById('button').addEventListener("click", function() {
	document.querySelector('.app-container').style.display = "flex"
});

document.getElementById('button1').addEventListener("click", function() {
	document.querySelector('.app-container').style.display = "flex"
});

document.getElementById('button2').addEventListener("click", function() {
	document.querySelector('.app-container').style.display = "flex"
});

document.getElementById('button3').addEventListener("click", function() {
    document.querySelector('.app-container').style.display = "flex"
});

$( document ).ready(function() {

    var user = firebase.auth().currentUser;

     if (user) {
       // User is signed in.
       $("#button3").show() ;
     }
     else{
        $("#button3").hide() ;
     }
});

$( document ).ready(function() {

    var user = firebase.auth().currentUser;

     if (user) {
       // User is signed in.
       $("#button3").show() ;
       
}
    else{
        $("#button3").hide() ;
    }
    });

  firebase.database().ref("lecturelist/").push().set({student: name, telephone: tel,status: status});



