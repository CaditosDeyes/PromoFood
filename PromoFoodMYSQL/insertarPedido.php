<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_POST["idClienteSolicitado"];
    $idRestaurantePerteneciente=$_POST["idRestaurantePerteneciente"];
    $resumen=$_POST["resumen"];
    $horaEntrega=$_POST["horaEntrega"];
    $estatus=$_POST["estatus"];
    $direccionEntrega=$_POST["direccionEntrega"];
    $precioFinal=$_POST["precioFinal"];
    $query="INSERT INTO pedido(idClienteSolicitado, idRestaurantePerteneciente, resumen, horaEntrega, estatus, direccionEntrega, precioFinal)
        VALUES('".$idClienteSolicitado."','".$idRestaurantePerteneciente."','".$resumen."','".$horaEntrega."','".$estatus."','".$direccionEntrega."','".$precioFinal."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El pedido se inserto de forma exitosa";
    }else{
        echo "Error al insertar el pedido";
    }
}