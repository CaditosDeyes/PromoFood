<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $id=$_POST["id"];
    $puntuacion=$_POST["puntuacion"];
    $descripcion=$_POST["descripcion"];
    $query="UPDATE resenia SET puntuacion='".$puntuacion."', descripcion='".$descripcion."', estatus='Calificado' WHERE id = '".$id."'";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}