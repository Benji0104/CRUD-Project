<!-- <?php

// INCLUIR ARCHIVO DE CONEXIÓN A LA BASE DE DATOS
include 'conexion.php';

$response = array();

try {
    // OBTENER VALORES DESDE POST
    $cedula = $_POST['cedula'];
    $nombre1 = $_POST['nombre1'];
    $nombre2 = $_POST['nombre2'];
    $apellido1 = $_POST['apellido1'];
    $apellido2 = $_POST['apellido2'];
    $direccion = $_POST['direccion'];
    $telefono = $_POST['telefono'];
    $email = $_POST['email'];

    // PREPARAR CONSULTA SQL
    $sql = "UPDATE personal SET nombre1 = :nombre1, nombre2 = :nombre2, apellido1 = :apellido1, apellido2 = :apellido2, direccion = :direccion, telefono = :telefono, email = :email WHERE cedula = :cedula";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':cedula', $cedula);
    $stmt->bindParam(':nombre1', $nombre1);
    $stmt->bindParam(':nombre2', $nombre2);
    $stmt->bindParam(':apellido1', $apellido1);
    $stmt->bindParam(':apellido2', $apellido2);
    $stmt->bindParam(':direccion', $direccion);
    $stmt->bindParam(':telefono', $telefono);
    $stmt->bindParam(':email', $email);

    // EJECUTAR DECLARACIÓN
    if ($stmt->execute()) {
        $response['success'] = true;
    } else {
        $response['success'] = false;
    }
} catch (Exception $e) {
    // CAPTURAR EXCEPCIÓN
    $response['success'] = false;
    $response['error'] = $e->getMessage();
}

// DEVOLVER RESPUESTA EN FORMATO JSON
echo json_encode($response);
?> -->


<?php

// INCLUIR ARCHIVO DE CONEXIÓN A LA BASE DE DATOS
include 'conexion.php';

$response = array();

try {
    // VERIFICAR QUE TODOS LOS CAMPOS ESTÉN PRESENTES
    if (
        isset($_POST['cedula']) && isset($_POST['nombre1']) && isset($_POST['nombre2']) &&
        isset($_POST['apellido1']) && isset($_POST['apellido2']) && isset($_POST['direccion']) &&
        isset($_POST['telefono']) && isset($_POST['email'])
    ) {
        // OBTENER VALORES DESDE POST
        $cedula = $_POST['cedula'];
        $nombre1 = $_POST['nombre1'];
        $nombre2 = $_POST['nombre2'];
        $apellido1 = $_POST['apellido1'];
        $apellido2 = $_POST['apellido2'];
        $direccion = $_POST['direccion'];
        $telefono = $_POST['telefono'];
        $email = $_POST['email'];

        // PREPARAR CONSULTA SQL
        $sql = "UPDATE personal SET nombre1 = :nombre1, nombre2 = :nombre2, apellido1 = :apellido1, apellido2 = :apellido2, direccion = :direccion, telefono = :telefono, email = :email WHERE cedula = :cedula";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':cedula', $cedula);
        $stmt->bindParam(':nombre1', $nombre1);
        $stmt->bindParam(':nombre2', $nombre2);
        $stmt->bindParam(':apellido1', $apellido1);
        $stmt->bindParam(':apellido2', $apellido2);
        $stmt->bindParam(':direccion', $direccion);
        $stmt->bindParam(':telefono', $telefono);
        $stmt->bindParam(':email', $email);

        // EJECUTAR DECLARACIÓN
        if ($stmt->execute()) {
            $response['success'] = true;
        } else {
            $errorInfo = $stmt->errorInfo();
            $response['success'] = false;
            $response['error'] = $errorInfo[2];
        }
    } else {
        $response['success'] = false;
        $response['error'] = 'Faltan parámetros requeridos';
    }
} catch (Exception $e) {
    // CAPTURAR EXCEPCIÓN
    $response['success'] = false;
    $response['error'] = $e->getMessage();
}

// DEVOLVER RESPUESTA EN FORMATO JSON
echo json_encode($response);
?>
