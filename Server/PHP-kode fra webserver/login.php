<?PHP
    include_once("connection.php"); 
    if( isset($_POST['username'])&& isset($_POST['password'])) { 
        $username = $_POST['username'];
        $password = $_POST['password'];
        
        $query = "SELECT Raspberry_lopeNr FROM Bruker WHERE brukernavn = '$username' AND passord = '$password'"; 
        
        $result = mysqli_query($conn, $query);
        $row = mysqli_fetch_row($result);
        if($result->num_rows == 1){
			if(isset($_POST['mobile']) && $_POST['mobile'] == "android") {
				echo $row[0];
			}
        } else{ 
            echo "NoResult"; 
        } 
    } 
?>