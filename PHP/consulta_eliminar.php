<?php

// INCLUIR ARCHIVO DE CONEXIÓN A LA BASE DE DATOS
include 'conexion.php';

try {
    // VERIFICAR QUE SE HAYA RECIBIDO LA CÉDULA
    if (!isset($_POST['cedula'])) {
        throw new Exception("Error: No se recibió la cédula.");
    }

    // Recuperar los datos del formulario
    $cedula = $_POST['cedula'];

    // VERIFICAR QUE LA CÉDULA NO ESTÉ VACÍA
    if (empty($cedula)) {
        throw new Exception("Error: La cédula está vacía.");
    }

    // EJECUTAR LA DECLARACIÓN SQL
    $sql = "DELETE FROM personal WHERE cedula = :cedula";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':cedula', $cedula, PDO::PARAM_STR);

    // Ejecutar la declaración SQL
    $stmt->execute();

    echo "Datos eliminados correctamente";
} catch (PDOException $e) {
    // ERROR AL ELIMINAR EL REGISTRO: MOSTRAR MENSAJE DE ERROR
    echo "Error al eliminar el registro: " . $e->getMessage();
} catch (Exception $e) {
    echo $e->getMessage();
}
// CERRAR LA CONEXIÓN
$conn = null;
?>
