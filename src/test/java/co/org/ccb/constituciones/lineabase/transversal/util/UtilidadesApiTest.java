package co.org.ccb.constituciones.lineabase.transversal.util;

import co.org.ccb.constituciones.lineabase.errores.entrada.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UtilidadesApiTest {

    @Mock
    private HttpServletRequest request;

    private static final String VALID_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MzQxMjE5MzgsInVzZXJfbmFtZSI6Ijc4MDMxNjQ2IiwianRpIjoiMmMxZTgwZGQtYjAzNy00YmUxLWIxYmQtZDk3ZGEwZTY4MjhhIiwiY2xpZW50X2lkIjoidGVzdGp3dGNsaWVudGlkIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.dmRsDnbwByR_vm85ksEl1oJFrQSjjTI6oexrqHb4iro";
    private static final String REQUEST_URI = "/api/test";
    private static final String USERNAME = "78031646";

    @BeforeEach
    void setUp() {
        UtilidadesApi.session = null;
    }

    @AfterEach
    void tearDown() {
        UtilidadesApi.session = null;
    }

    @Test
    void initSession_DeberiaInicializarSesionCorrectamente() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_JWT);
        when(request.getRequestURI()).thenReturn(REQUEST_URI);

        // Act
        UtilidadesApi.initSession(request);

        // Assert
        assertNotNull(UtilidadesApi.session);
        assertEquals(REQUEST_URI, UtilidadesApi.session.getService());
        assertEquals(USERNAME, UtilidadesApi.session.getUsuario());
    }

    @Test
    void initSession_DeberiaLanzarExcepcionSiNoHayAutorizacion() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> UtilidadesApi.initSession(request));
    }

    @Test
    void initSession_DeberiaLanzarExcepcionSiAutorizacionEstaVacia() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("");

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> UtilidadesApi.initSession(request));
    }

    @Test
    void initSession_DeberiaLanzarExcepcionSiTokenNoEsValido() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token.here");

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> UtilidadesApi.initSession(request));
    }

    @Test
    void decodificarJwt_DeberiaDecodificarTokenValido() {
        // Act
        Map<String, Object> resultado = UtilidadesApi.decodificarJwt(VALID_JWT);

        // Assert
        assertNotNull(resultado);
        assertEquals(USERNAME, resultado.get("user_name"));
    }

    @Test
    void decodificarJwt_DeberiaLanzarExcepcionConTokenInvalido() {
        // Arrange
        String tokenInvalido = "invalid.token.here";

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> UtilidadesApi.decodificarJwt(tokenInvalido));
    }

    @Test
    void decodificarJwt_DeberiaLanzarExcepcionConTokenMalFormado() {
        // Arrange
        String tokenMalFormado = "solo.una.parte";

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> UtilidadesApi.decodificarJwt(tokenMalFormado));
    }

    @Test
    void session_DeberiaSerAccesibleYModificable() {
        // Arrange
        UsuarioSesion usuarioSesion = UsuarioSesion.builder()
                .usuario("testUser")
                .service("/test")
                .build();

        // Act
        UtilidadesApi.session = usuarioSesion;

        // Assert
        assertNotNull(UtilidadesApi.session);
        assertEquals("testUser", UtilidadesApi.session.getUsuario());
        assertEquals("/test", UtilidadesApi.session.getService());
    }
}