<?PHP 
    include_once("connection.php"); 
    if( isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['raspnum'])){ 
		$username = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$raspnum = $_POST['raspnum'];

		$sql = "INSERT INTO Bruker (brukernavn, passord, epost, Raspberry_lopeNr) VALUES ('$username', '$password', '$email', '$raspnum')";
    
		if($conn->query($sql) === TRUE) {
			echo "Success";
		} else {
			echo "error";
		}       
    }
$conn->close();	
?>