package co.org.ccb.constituciones.lineabase.errores.entrada;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneralErrorResponseTest {

    @Test
    void constructorVacio_DeberiaCrearInstancia() {
        // Act
        GeneralErrorResponse response = new GeneralErrorResponse();

        // Assert
        assertNotNull(response);
        assertNull(response.getMessage());
        assertNull(response.getError());
        assertNull(response.getPath());
        assertNull(response.getStatus());
    }

    @Test
    void constructorString_DeberiaAsignarValores() {
        // Arrange
        String message = "Error message";
        String error = "Error details";
        String path = "/api/test";
        Integer status = 400;

        // Act
        GeneralErrorResponse response = new GeneralErrorResponse(message, error, path, status);

        // Assert
        assertEquals(message, response.getMessage());
        assertEquals(error, response.getError());
        assertEquals(path, response.getPath());
        assertEquals(status, response.getStatus());
    }

    @Test
    void constructorObject_DeberiaAsignarValores() {
        // Arrange
        String message = "Error message";
        Object error = new RuntimeException("Error object");
        String path = "/api/test";
        Integer status = 400;

        // Act
        GeneralErrorResponse response = new GeneralErrorResponse(message, error, path, status);

        // Assert
        assertEquals(message, response.getMessage());
        assertEquals(error, response.getError());
        assertEquals(path, response.getPath());
        assertEquals(status, response.getStatus());
    }

    @Test
    void builder_DeberiaCrearInstanciaCorrectamente() {
        // Arrange
        String message = "Error message";
        Object error = "Error details";
        String path = "/api/test";
        Integer status = 400;

        // Act
        GeneralErrorResponse response = GeneralErrorResponse.builder()
                .message(message)
                .error(error)
                .path(path)
                .status(status)
                .build();

        // Assert
        assertEquals(message, response.getMessage());
        assertEquals(error, response.getError());
        assertEquals(path, response.getPath());
        assertEquals(status, response.getStatus());
    }

    @Test
    void settersAndGetters_DeberianFuncionarCorrectamente() {
        // Arrange
        GeneralErrorResponse response = new GeneralErrorResponse();
        String message = "New message";
        Object error = "New error";
        String path = "New path";
        Integer status = 500;

        // Act
        response.setMessage(message);
        response.setError(error);
        response.setPath(path);
        response.setStatus(status);

        // Assert
        assertEquals(message, response.getMessage());
        assertEquals(error, response.getError());
        assertEquals(path, response.getPath());
        assertEquals(status, response.getStatus());
    }

    @Test
    void equals_DeberiaCompararObjetosCorrectamente() {
        // Arrange
        GeneralErrorResponse response1 = new GeneralErrorResponse("msg", "error", "/path", 400);
        GeneralErrorResponse response2 = new GeneralErrorResponse("msg", "error", "/path", 400);
        GeneralErrorResponse response3 = new GeneralErrorResponse("different", "error", "/path", 400);

        // Assert
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    void hashCode_DeberiaSerConsistente() {
        // Arrange
        GeneralErrorResponse response1 = new GeneralErrorResponse("msg", "error", "/path", 400);
        GeneralErrorResponse response2 = new GeneralErrorResponse("msg", "error", "/path", 400);

        // Assert
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}