var firebaseRef = firebase.database().ref();

function adminSignIn(){
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    firebase.auth().signInWithEmailAndPassword(email, password)
                .then(function () {
                    // If sign-in is successful, show the table and export button
                    // let user = firebase.auth().currentUser;
                    // let uid;
                    // if(user!=null){
                    //     uid = user.uid;
                    // }
                    // alert(uid)
                    // window.location.href = 'next_page.html';

                    var visibleDiv = document.getElementById('admin-signin-form');
                    var invisibleDiv = document.getElementById('table_show_layout');

                    //  alert("done")

                     var database = firebase.database();

                     visibleDiv.style.display = 'none';
                     invisibleDiv.style.display = 'block';


// Fetch data and populate the table
database.ref('data').once('value').then(function(snapshot) {
    var tableBody = document.getElementById('table-body');

    snapshot.forEach(function(childSnapshot) {
        var childData = childSnapshot.val();
        var row = "<tr><td>" + childData.userName + "</td><td>" + childData.email + "</td><td>" + childData.address + "</td><td>" + childData.phone + "</td></tr>";
        tableBody.innerHTML += row;
    });
});


                })
                .catch(function (error) {
                    alert("Error: " + error.message);
          });
}

function exportTableToText() {
    var rows = document.querySelectorAll("table tr");
    var data = [];

    for (var i = 0; i < rows.length; i++) {
        var row = [];
        var cols = rows[i].querySelectorAll("td, th");

        for (var j = 0; j < cols.length; j++) {
            row.push(cols[j].innerText);
        }

        data.push(row.join("\t")); // Use tab as a delimiter for text file
    }

    var textContent = data.join("\n");

    // Create a blob containing the text data
    var blob = new Blob([textContent], { type: 'text/plain' });

    // Download text file
    var link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "table_data.txt";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}