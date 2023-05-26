<?php
if($_SERVER['REQUEST_METHOD']=="GET"){
    require_once 'conexion.php';
    $idClienteSolicitado=$_GET['idClienteSolicitado'];
    $query="DELETE FROM resenia WHERE idClienteSolicitado='".$idClienteSolicitado."' and estatus='Recibido'";
    $resultado=$mysql->query($query);
    if($mysql->affected_rows>0){
        if($resultado==true){
            echo "Resenia borrado exitosamente";
        }
    }else{
        echo "Error al eliminar resenia";
    }
    $mysql->close();
}