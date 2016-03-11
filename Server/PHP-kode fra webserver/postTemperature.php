
<?PHP
include_once("connection.php");
if( isset($_POST['temperatur']) && isset($_POST['lopeNr'])){
    $temperatur = $_POST['temperatur'];
    $lopeNr = $_POST['lopeNr'];
    $sql = "INSERT INTO DataLinje (temperatur, Raspberry_lopeNr) VALUES ($temperatur, $lopeNr)";

    if($conn->query($sql) === TRUE) {
        echo "Success";
    } else {
        echo "error";
    }
}
$conn->close();
?>