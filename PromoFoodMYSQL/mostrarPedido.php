<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_GET['idClienteSolicitado'];
    $query="SELECT * FROM pedido WHERE idClienteSolicitado='".$idClienteSolicitado."' and estatus='No entregado'";
    $resultado=$mysql->query($query);
    if($mysql->affected_rows>0){
        while($row=$resultado->fetch_assoc()){
            $array=$row;
        }
        echo json_encode(($array));
    }else{
        echo "No hay registros";
    }
    $resultado->close();
    $mysql->close();
}