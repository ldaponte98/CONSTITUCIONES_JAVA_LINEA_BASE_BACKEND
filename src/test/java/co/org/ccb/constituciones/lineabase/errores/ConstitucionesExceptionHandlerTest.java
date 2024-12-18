package co.org.ccb.constituciones.lineabase.errores;

import co.org.ccb.constituciones.lineabase.aplicacion.logs.LogService;
import co.org.ccb.constituciones.lineabase.errores.entrada.ConflictException;
import co.org.ccb.constituciones.lineabase.errores.entrada.GeneralErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConstitucionesExceptionHandlerTest {

    @Mock
    private LogService logService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ConstitucionesExceptionHandler exceptionHandler;

    private static final String TEST_URI = "/api/test";

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn(TEST_URI);
        doNothing().when(logService).error(anyString());
    }

    @Test
    void handleConflictExceptions_DeberiaRetornarResponseEntityCorrecto() {
        // Arrange
        String errorMessage = "Error de conflicto";
        Object errorDetail = "Detalle del error";
        ConflictException ex = new ConflictException(errorDetail, errorMessage);

        // Act
        ResponseEntity<GeneralErrorResponse> response = exceptionHandler.handleConflictExceptions(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(errorDetail, response.getBody().getError());
        assertEquals(TEST_URI, response.getBody().getPath());
        verify(logService).error(contains("ConflictException"));
    }

    @Test
    void handleAllExceptions_DeberiaRetornarResponseEntityCorrecto() {
        // Arrange
        String errorMessage = "Error general";
        Exception ex = new Exception(errorMessage);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        GeneralErrorResponse errorResponse = (GeneralErrorResponse) response.getBody();
        assertEquals("Internal server error", errorResponse.getMessage());
        assertEquals(errorMessage, errorResponse.getError());
        assertEquals(TEST_URI, errorResponse.getPath());
        verify(logService).error(contains("Exception"));
    }

    @Test
    void handleArgumentNotValidExceptions_DeberiaRetornarResponseEntityCorrecto() throws Exception {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        List<FieldError> fieldErrors = Arrays.asList(
                new FieldError("object", "field1", "error1"),
                new FieldError("object", "field2", "error2")
        );

        List<ObjectError> globalErrors = Arrays.asList(
                new ObjectError("object1", "global error1"),
                new ObjectError("object2", "global error2")
        );

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        when(bindingResult.getGlobalErrors()).thenReturn(globalErrors);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleArgumentNotValidExceptions(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GeneralErrorResponse errorResponse = (GeneralErrorResponse) response.getBody();
        assertEquals("Bad Request", errorResponse.getMessage());
        assertTrue(errorResponse.getError().toString().contains("field1"));
        assertTrue(errorResponse.getError().toString().contains("field2"));
        assertTrue(errorResponse.getError().toString().contains("object1"));
        assertTrue(errorResponse.getError().toString().contains("object2"));
        assertEquals(TEST_URI, errorResponse.getPath());
        verify(logService).error(contains("MethodArgumentNotValidException"));
    }

    @Test
    void handleMethodArgumentTypeMismatchException_DeberiaRetornarResponseEntityCorrecto() {
        // Arrange
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        Throwable cause = mock(Throwable.class);
        Throwable rootCause = mock(Throwable.class);

        when(ex.getCause()).thenReturn(cause);
        when(cause.getCause()).thenReturn(rootCause);
        when(rootCause.getMessage()).thenReturn("Error de tipo");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentTypeMismatchException(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GeneralErrorResponse errorResponse = (GeneralErrorResponse) response.getBody();
        assertEquals("Bad Request", errorResponse.getMessage());
        assertEquals("Error de tipo", errorResponse.getError());
        assertEquals(TEST_URI, errorResponse.getPath());
        verify(logService).error(contains("MethodArgumentTypeMismatchException"));
    }
}