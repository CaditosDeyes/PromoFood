<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_GET["idClienteSolicitado"];
    $query="UPDATE resenia SET estatus= 'Recibido' where idClienteSolicitado = '".$idClienteSolicitado."' and estatus='Enviando'";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}