package co.org.ccb.constituciones.lineabase.errores.entrada;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedExceptionTest {

    @Test
    void constructor_DeberiaAsignarMensaje() {
        // Arrange
        String mensaje = "Usuario no autorizado";

        // Act
        UnauthorizedException exception = new UnauthorizedException(mensaje);

        // Assert
        assertEquals(mensaje, exception.getMessage(), "El mensaje debería coincidir con el proporcionado");
    }

    @Test
    void constructor_DeberiaFuncionarConMensajeVacio() {
        // Arrange
        String mensaje = "";

        // Act
        UnauthorizedException exception = new UnauthorizedException(mensaje);

        // Assert
        assertEquals(mensaje, exception.getMessage(), "El mensaje vacío debería ser preservado");
    }

    @Test
    void constructor_DeberiaFuncionarConMensajeNull() {
        // Act
        UnauthorizedException exception = new UnauthorizedException(null);

        // Assert
        assertNull(exception.getMessage(), "El mensaje null debería ser preservado");
    }

    @Test
    void exception_DeberiaSerRuntimeException() {
        // Arrange & Act
        UnauthorizedException exception = new UnauthorizedException("mensaje");

        // Assert
        assertTrue(exception instanceof RuntimeException, "Debería ser una instancia de RuntimeException");
    }
}