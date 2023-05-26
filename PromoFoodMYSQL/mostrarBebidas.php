<?php
if($_SERVER['REQUEST_METHOD']=="GET"){
    require_once 'conexion.php';
    $idMenuPerteneciente=$_GET['idMenuPerteneciente'];
    $query="SELECT * FROM bebida where idMenuPerteneciente='".$idMenuPerteneciente."'";
    $resultado=$mysql->query($query);
    if($mysql->affected_rows>0){
        $json="{\"data\":[";
        while($row=$resultado->fetch_assoc()){
            $json=$json.json_encode($row);
            $json=$json.",";
        }
        $json=substr(trim($json), 0, -1);
        $json=$json."]}";
    }
    echo $json;
    $resultado->close();
    $mysql->close();
}