<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once 'conexion.php';
    $nombre=$_GET['nombre'];
    $query="SELECT * FROM usuarioscliente WHERE nombre='".$nombre."'";
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