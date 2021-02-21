
  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyC6VzyZC1cPPH9YKP3hdXccRZeQ7qxiAaE",
    authDomain: "superbriliant-42810.firebaseapp.com",
    databaseURL: "https://superbriliant-42810.firebaseio.com",
    projectId: "superbriliant-42810",
    storageBucket: "superbriliant-42810.appspot.com",
    messagingSenderId: "516523291175",
    appId: "1:516523291175:web:2d37767a50a43c14"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);


firebase.auth().onAuthStateChanged(function(user) {
      var element = document.getElementById("so");
      var element1 = document.getElementById("sg");
      var element2 = document.getElementById("tg");
      var element3 = document.getElementById("pf");
      var element4 = document.getElementById("un");

      if (user)
       {
        console.log('Super Brilliant User');
        element.classList.remove("hide");
        element1.classList.add("hide");
        element2.classList.add("hide");
        element3.classList.remove("hide");
        element4.classList.remove("hide");
      }else{
        console.log('not logged in');
        element.classList.add("hide");
        element1.classList.remove("hide");
        element2.classList.remove("hide");
        element3.classList.add("hide");
        element4.classList.add("hide");
      }
      if (user) {
        // User is signed in.
       var displayName = user.displayName;
       $("UPname").html("<b style=\"color:white\">" + displayName + "</b>");

      }


    });