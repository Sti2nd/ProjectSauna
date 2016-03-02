<?php
$servername = "mysql.stud.ntnu.no"; 
$username = "roberei_hotdog"; 
$password = "pekka";
$dbname = "roberei_hotdog_db";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>