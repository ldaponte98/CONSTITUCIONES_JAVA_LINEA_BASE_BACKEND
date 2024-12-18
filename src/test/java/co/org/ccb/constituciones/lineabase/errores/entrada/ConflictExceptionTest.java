package co.org.ccb.constituciones.lineabase.errores.entrada;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConflictExceptionTest {

    @Test
    void constructor_DeberiaAsignarMensajeYError() {
        // Arrange
        String mensaje = "Error de conflicto";
        Object error = new RuntimeException("Error detallado");

        // Act
        ConflictException exception = new ConflictException(error, mensaje);

        // Assert
        assertEquals(mensaje, exception.getMessage(), "El mensaje debería coincidir");
        assertEquals(error, exception.getError(), "El objeto de error debería coincidir");
    }

    @Test
    void constructor_DeberiaFuncionarConErrorNull() {
        // Arrange
        String mensaje = "Error de conflicto";

        // Act
        ConflictException exception = new ConflictException(null, mensaje);

        // Assert
        assertEquals(mensaje, exception.getMessage(), "El mensaje debería coincidir");
        assertNull(exception.getError(), "El error debería ser null");
    }

    @Test
    void constructor_DeberiaFuncionarConMensajeNull() {
        // Arrange
        Object error = new RuntimeException("Error detallado");

        // Act
        ConflictException exception = new ConflictException(error, null);

        // Assert
        assertNull(exception.getMessage(), "El mensaje debería ser null");
        assertEquals(error, exception.getError(), "El objeto de error debería coincidir");
    }

    @Test
    void exception_DeberiaSerRuntimeException() {
        // Arrange & Act
        ConflictException exception = new ConflictException(null, "mensaje");

        // Assert
        assertTrue(exception instanceof RuntimeException, "Debería ser una RuntimeException");
    }
}