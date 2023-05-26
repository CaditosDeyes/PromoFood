<?php
$mysql=new mysqli("localhost", "root", "", "PromoFood");
if($mysql->connect_error){
    die("Error de conexion");
}else{
    //echo "Conexion exitosa";
}