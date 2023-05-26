<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $nombre=$_POST["nombre"];
    //$query="INSERT INTO menu (nombre_menu, platillos, bebidas)
        //VALUES('".$nombre_menu."','".$platillos."', '".$bebidas."')";
    $query="INSERT INTO menu(nombre)
        VALUES('".$nombre."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El menú se inserto de forma exitosa";
    }else{
        echo "Error al insertar el menú";
    }
}