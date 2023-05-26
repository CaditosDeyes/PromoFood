<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idMenuPerteneciente=$_POST["idMenuPerteneciente"];
    $nombre=$_POST["nombre"];
    $precio=$_POST["precio"];
    $ingredientes=$_POST["ingredientes"];
    $query="INSERT INTO platillo(idMenuPerteneciente,nombre,precio,ingredientes)
        VALUES('".$idMenuPerteneciente."','".$nombre."','".$precio."','".$ingredientes."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El platillo se inserto de forma exitosa";
    }else{
        echo "Error al insertar el platillo";
    }
}