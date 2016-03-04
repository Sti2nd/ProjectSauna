<?PHP 
    include_once("connection.php"); 
    if( isset($_POST['raspnum'])) { 
        $raspnum = intval($_POST['raspnum']);
		
		$query = "SELECT temperatur FROM DataLinje WHERE Raspberry_lopeNr = '$raspnum' ORDER BY timeStamp DESC";		
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_row($result);
		
		if($result->num_rows > 0){
			if(isset($_POST['mobile']) && $_POST['mobile'] == "android") {
				echo $row[0];
			}
		}else {
			echo "Failed";
		}
	}
?>