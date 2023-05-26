<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idCliente=$_GET["idCliente"];
    $query="UPDATE carrito SET solicitado= '1' where idCliente = '".$idCliente."' and solicitado=0";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}