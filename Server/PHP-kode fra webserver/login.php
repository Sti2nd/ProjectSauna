<?PHP 
    include_once("connection.php"); 
    if( isset($_POST['username'])&& isset($_POST['password'])) { 
        $username = $_POST['username'];
        $password = $_POST['password'];
        
        $query = "SELECT brukernavn FROM Bruker WHERE brukernavn = '$username' AND passord = '$password'"; 
        
        $result = mysqli_query($conn, $query);
        
        if($result->num_rows > 0){
		if(isset($_POST['mobile']) && $_POST['mobile'] == "android") {
        	        echo "Success";
		}
        } else{ 
            echo "NoResult"; 
        } 
    } 
?>