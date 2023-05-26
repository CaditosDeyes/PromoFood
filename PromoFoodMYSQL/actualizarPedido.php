<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_GET["idClienteSolicitado"];
    $query="UPDATE pedido SET estatus= 'Entregado' where idClienteSolicitado = '".$idClienteSolicitado."' and estatus='No entregado'";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}