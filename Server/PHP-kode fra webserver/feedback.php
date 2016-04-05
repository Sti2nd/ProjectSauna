<?php
	include_once("connection.php");

	if (isset($_POST['feedback'])){
		$feedback = $_POST['feedback'];

		$sql = "INSERT INTO Feedback (feedback) 
		VALUES ('$feedback')";

		if ($conn->query($sql) == TRUE){
			echo "Success";
		}

		else{
			echo "error";
		}
	}
$conn->close();
?>
