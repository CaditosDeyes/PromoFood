<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $nombre=$_POST["nombre"];
    $correoElectronico=$_POST["correoElectronico"];
    $contrasenia=$_POST["contrasenia"];
    $menu=0;
    $domicilio=$_POST["domicilio"];
    $numeroTelefono=$_POST["numeroTelefono"];
    $query="INSERT INTO restaurante (nombre, correoElectronico, contrasenia, menu, domicilio, numeroTelefono) 
        VALUES('".$nombre."', '".$correoElectronico."', '".$contrasenia."', '".$menu."', '".$domicilio."', '".$numeroTelefono."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}