<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idCliente=$_POST["idCliente"];
    $idRestaurantePerteneciente=$_POST["idRestaurantePerteneciente"];
    $resumen=$_POST["resumen"];
    $precioIndividual=$_POST["precioIndividual"];
    $query="INSERT INTO carrito (idCliente, idRestaurantePerteneciente, resumen, precioIndividual) 
        VALUES('".$idCliente."', '".$idRestaurantePerteneciente."', '".$resumen."', '".$precioIndividual."')";
    $resultado=$mysql->query($query);
    if($resultado==true){
        echo "Se insertó en el carrito de manera exitosa";
    }else{
        echo "Error al insertar el usuario";
    }
}