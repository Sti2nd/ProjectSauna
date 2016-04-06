<?php
$username = "roberei_hotdog";
$password = "pekka";
$hostname = "mysql.stud.ntnu.no"; 
$db = "roberei_hotdog_db";

//connection to the database

$connection = new mysqli($hostname, $username, $password, $db);
$connection->set_charset("utf-8");

//select db
if ($connection->connect_error) {
	die("Connection failed");
}

$sqlBruker= "SELECT * FROM Bruker";
$sqlRaspberry= "SELECT * FROM DataLinje";
$sqlFeedback= "SELECT * FROM Feedback";


$userDataResult = $connection->query($sqlBruker);
$raspberryDataResult = $connection->query($sqlRaspberry);
$feedbackResult = $connection->query($sqlFeedback);


$userDataArray = array();
$raspberryDataArray = array();
$feedbackArray = array();

while ($row = $userDataResult->fetch_array(MYSQL_ASSOC)){
	$userDataArray[] = $row;
}

while ($row = $raspberryDataResult->fetch_array(MYSQL_ASSOC)){
	$raspberryDataArray[] = $row;
}


while ($row = $feedbackResult->fetch_array(MYSQL_ASSOC)){
	$feedbackArray[] = $row;
}




?>


<html>
<head>
<title> Stats </title>
<meta charset="UTF-8">
</head>
<body>
<h1>User Data</h1>
<table width="600" border="1" cellpadding="1" cellspacing="1">
<tr>
<th> Navn</th>
<th> epost</th>
<th> Raspberry Nr</th>
<tr>


<?php


for ($i = 0; $i < count($userDataArray); $i++) {
	echo "<tr>";

	echo "<td>".$userDataArray[$i]['brukernavn']."</td>";

	echo "<td>".$userDataArray[$i]['epost']."</td>";

	echo "<td>".$userDataArray[$i]['Raspberry_lopeNr']."</td>";

	echo "</tr>";
}




?>


<table width="600" border="1" cellspacing="1">
<tr>
<th> Feedback ID</th>
<th> User feedback</th>
<tr>
<br><br><br>

<?php

for ($i = 0; $i < count($feedbackArray); $i++){
	echo "<tr>";

	echo "<td>".$feedbackArray[$i]['feedback_id']."</td>";

	echo "<td>".$feedbackArray[$i]['feedback']."</td>";

	echo "</tr>";
}

?>



<h1>Feedback</h1>
<table width="600" border="1" cellpadding="1" cellspacing="1">
<tr>
<th> timeStamp</th>
<th> temperatur </th>
<th> Raspbery Nr</th>
<tr>
<br><br><br>

<?php


for ($i = 0; $i < count($raspberryDataArray); $i++) {
	echo "<tr>";

	echo "<td>".$raspberryDataArray[$i]['timeStamp']."</td>";

	echo "<td>".$raspberryDataArray[$i]['temperatur']."</td>";

	echo "<td>".$raspberryDataArray[$i]['Raspberry_lopeNr']."</td>";

	echo "</tr>";
}


?>


<h1>Raspberry Data</h1>




</body>
</html>