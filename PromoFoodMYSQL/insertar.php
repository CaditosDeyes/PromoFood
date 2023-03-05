<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $nombre=$_POST["nombre"];
    $correoElectronico=$_POST["correoElectronico"];
    $contrasenia=$_POST["contrasenia"];
    $telefono=$_POST["telefono"];
    $query="INSERT INTO usuarioscliente (nombre, correoElectronico, contrasenia, telefono) 
        VALUES('".$nombre."', '".$correoElectronico."', '".$contrasenia."', '".$telefono."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}