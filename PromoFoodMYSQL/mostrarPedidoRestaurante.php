<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once 'conexion.php';
    $idRestaurantePerteneciente=$_GET['idRestaurantePerteneciente'];
    $query="SELECT * FROM pedido WHERE idRestaurantePerteneciente='".$idRestaurantePerteneciente."' and estatus='No entregado'";
    $resultado=$mysql->query($query);
    if($mysql->affected_rows>0){
        while($row=$resultado->fetch_assoc()){
            $array=$row;
        }
        echo json_encode(($array));
    }else{
        echo "No hay registros";
    }
    $resultado->close();
    $mysql->close();
}