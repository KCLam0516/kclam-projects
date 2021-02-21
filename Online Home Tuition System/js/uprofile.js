firebase.auth().onAuthStateChanged(function(user) {


var user = firebase.auth().currentUser;
if(user)
{
var database = firebase.database();

if(user.email!=null){
return firebase.database().ref('/users/' + user.uid).once('value').then(function(snapshot) {
var type = snapshot.val().securitylevel;

if(type==1)
{
$('#admin').html("<a href='webdark/adminmain.html'>Admin</a>");
$('#esc').val(1);
}

var name = snapshot.val().name;
var gender = snapshot.val().gender;
var ic = snapshot.val().ic;

var st = snapshot.val().state;
var state="";
if (st=="Foreign country")
{
state="Foreign country";
}
else
{
state=st+", Malaysia";
}
var tel = snapshot.val().tel;

<!--set user data -->
$('#disname').html(user.displayName);
$('#state').html(" "+state+"<i class='glyphicon glyphicon-map-marker'></i>");
$('#udata').html("<i class='glyphicon glyphicon-user'></i>  "+name+", "+gender+"<br /><i class='glyphicon glyphicon-envelope'></i> "+user.email+"<br /><i class='glyphicon glyphicon-pencil'></i> "+ic+"<br /><i class='glyphicon glyphicon-earphone'></i> "+tel);

<!-- set edit data -->

$('#eemail').val(user.email);
$('#efull_name').val(name);
$('#eic').val(ic);
$('#edisplay_name').val(user.displayName);
$('#etel').val(tel);
$('#estate').val(st).prop('selected', true);
$('#egender').val(gender).prop('selected', true);
});

}
else{
$("#hireset").attr('style','display:none;');
return firebase.database().ref('/users/' + user.uid).once('value').then(function(snapshot) {

if(snapshot.val()==null)
{
$('#edisplay_name').val(user.displayName);
 $('#efull_name').val(user.displayName);
 $('#first').html("First Login Edit");
  $('#eemail').val("Facebook Account");
$("#click").click();}
else{
return firebase.database().ref('/users/' + user.uid).once('value').then(function(snapshot) {
  var type = snapshot.val().securitylevel;

if(type==1)
{
$('#admin').html("<a href='webdark/adminmain.html'>Admin</a>");
$('#esc').val(1);
}


var name = snapshot.val().name;
var gender = snapshot.val().gender;
var ic = snapshot.val().ic;

var st = snapshot.val().state;
var state="";
if (st=="Foreign country")
{
state="Foreign country";
}
else
{
state=st+", Malaysia";
}
var tel = snapshot.val().tel;

<!--set user data -->
$('#disname').html(user.displayName);
$('#state').html(" "+state+"<i class='glyphicon glyphicon-map-marker'></i>");
$('#udata').html("<i class='glyphicon glyphicon-user'></i>  "+name+", "+gender+"<br /><i class='glyphicon glyphicon-envelope'></i> Facebook Account<br /><i class='glyphicon glyphicon-pencil'></i> "+ic+"<br /><i class='glyphicon glyphicon-earphone'></i> "+tel);

<!-- set edit data -->

$('#eemail').val("Facebook Account");
$('#efull_name').val(name);
$('#eic').val(ic);
$('#edisplay_name').val(user.displayName);
$('#etel').val(tel);
$('#estate').val(st).prop('selected', true);
$('#egender').val(gender).prop('selected', true);
});
}

});
}
    }
});
