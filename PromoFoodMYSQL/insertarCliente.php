<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $nombre=$_POST["nombre"];
    $correoElectronico=$_POST["correoElectronico"];
    $contrasenia=$_POST["contrasenia"];
    $numeroTelefono=$_POST["numeroTelefono"];
    $query="INSERT INTO cliente (nombre, correoElectronico, contrasenia, numeroTelefono) 
        VALUES('".$nombre."', '".$correoElectronico."', '".$contrasenia."', '".$numeroTelefono."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}