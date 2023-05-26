<?php
if($_SERVER['REQUEST_METHOD']=="GET"){
    require_once 'conexion.php';
    $idRestaurantePerteneciente=$_GET['idRestaurantePerteneciente'];
    $query="SELECT * FROM pedido WHERE idRestaurantePerteneciente='".$idRestaurantePerteneciente."' and estatus='No entregado'";
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
