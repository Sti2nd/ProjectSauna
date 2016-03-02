<?php
$servername = "mysql.stud.ntnu"; //replace it with your database server name
$username = "roberei_hotdog";  //replace it with your database username
$password = "pekka";  //replace it with your database password
$dbname = "roberei_hotdog_db";
// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>