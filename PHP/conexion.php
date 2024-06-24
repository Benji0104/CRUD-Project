<?php
// DATOS DE CONEXION A LA BASE DE DATOS
$servername = "localhost";
$username = "ds62024";
$password = "123456";
$dbname = "android";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    // Conexión exitosa
} catch (PDOException $e) {
    // Conexión fallida: MOSTRAR MENSAJE DE ERROR
}
?>