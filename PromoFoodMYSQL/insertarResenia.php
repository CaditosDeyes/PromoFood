<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_POST["idClienteSolicitado"];
    $idPlatillo=$_POST["idPlatillo"];
    $idBebida=$_POST["idBebida"];
    $nombrePlatilloBebida=$_POST["nombrePlatilloBebida"];
    $descripcion=$_POST["descripcion"];
    $puntuacion=$_POST["puntuacion"];
    $estatus=$_POST["estatus"];
    $query="INSERT INTO resenia(idClienteSolicitado, idPlatillo, idBebida, nombrePlatilloBebida, descripcion, puntuacion, estatus)
        VALUES('".$idClienteSolicitado."','".$idPlatillo."','".$idBebida."','".$nombrePlatilloBebida."','".$descripcion."','".$puntuacion."','".$estatus."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "La resenia se inserto de forma exitosa";
    }else{
        echo "Error al insertar resenia";
    }
}