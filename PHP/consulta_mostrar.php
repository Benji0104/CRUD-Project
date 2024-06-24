<?php

// INCLUIR ARCHIVO DE CONEXIÓN A LA BASE DE DATOS
include 'conexion.php';

try {
    // OBTENER LA CÉDULA DESDE UNA SOLICITUD GET
    if (isset($_GET['cedula'])) {
        $cedula = $_GET['cedula'];

        // PREPARAR LA CONSULTA SQL
        $stmt = $conn->prepare('SELECT * FROM personal WHERE cedula = :cedula');
        $stmt->execute(['cedula' => $cedula]);

        // OBTENER LOS RESULTADOS
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($user) {
            // RETORNAR LOS DATOS EN FORMATO JSON
            echo json_encode($user); 
        } else {
            // NO SE ENCONTRÓ EL USUARIO CON LA CÉDULA PROPORCIONADA
            echo json_encode(['error' => 'No se encontró el usuario con la cedula proporcionada.']);
        }
    } else {
        // NO SE PROPORCIONÓ UNA CÉDULA
        echo json_encode(['error' => 'No se proporciono una cedula.']);
    }
} catch (PDOException $e) {
    // ERROR EN LA BASE DE DATOS: MOSTRAR MENSAJE DE ERROR
    echo json_encode(['error' => 'Error en la base de datos: ' . $e->getMessage()]);
}

?>
