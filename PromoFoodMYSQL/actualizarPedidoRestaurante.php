<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idRestaurantePerteneciente=$_GET["idRestaurantePerteneciente"];
    $id=$_GET["id"];
    $query="UPDATE pedido SET estatus= 'Entregado' 
    where idRestaurantePerteneciente = '".$idRestaurantePerteneciente."' and estatus='No entregado' and id = '".$id."'";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "El usuario se inserto de forma exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}