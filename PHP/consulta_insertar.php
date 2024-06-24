<?php

// INCLUIR ARCHIVO DE CONEXIÓN A LA BASE DE DATOS
include 'conexion.php';

try {

    // RECUPERAR LOS DATOS DEL FORMULARIO
    $cedula = $_POST['cedula'];
    $nombre = $_POST['nombre'];
    $segundoNombre = $_POST['segundoNombre'];
    $primerApellido = $_POST['primerApellido'];
    $segundoApellido = $_POST['segundoApellido'];
    $direccion = $_POST['direccion'];
    $telefono = $_POST['telefono'];
    $email = $_POST['email'];

    // PREPARAR CONSULTA SQL
    $sql = "INSERT INTO personal (cedula, nombre1, nombre2, apellido1, apellido2, direccion, telefono, email)
    VALUES ('$cedula', '$nombre', '$segundoNombre', '$primerApellido', '$segundoApellido', '$direccion', '$telefono', '$email')";

    // EJECUTAR CONSULTA SQL
    $conn->exec($sql);

    echo "Datos insertados correctamente";
} catch(PDOException $e) {
    // ERROR AL CREAR EL REGISTRO: MOSTRAR MENSAJE DE ERROR
    echo "Error al crear el registro: " . $e->getMessage();
}
// CERRAR LA CONEXIÓN
$conn = null;
?>
