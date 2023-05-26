<?php
if($_SERVER['REQUEST_METHOD']=="GET"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_GET['idClienteSolicitado'];
    $query="SELECT * FROM pedido WHERE idClienteSolicitado='".$idClienteSolicitado."' and estatus='No entregado'";
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