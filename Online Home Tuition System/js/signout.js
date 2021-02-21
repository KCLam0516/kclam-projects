  $(function () {


        $("#signout").click(function (e) {
          firebase.auth().signOut().then(function() {
            console.log('Signed Out');
		alert("Sign out completed");
          }).catch(function(error) {
            console.error('Sign Out Error', error);
          });
        });
      });