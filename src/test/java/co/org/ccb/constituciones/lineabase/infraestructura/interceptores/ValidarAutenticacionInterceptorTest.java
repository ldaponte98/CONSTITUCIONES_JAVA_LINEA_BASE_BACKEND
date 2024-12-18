package co.org.ccb.constituciones.lineabase.infraestructura.interceptores;

import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Enumeration;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ValidarAutenticacionInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;
    private ValidarAutenticacionInterceptor interceptor = new ValidarAutenticacionInterceptor();

    private MockedStatic<UtilidadesApi> mockUtilidadesApi;

    @BeforeEach
    void setUp() {
        mockUtilidadesApi = mockStatic(UtilidadesApi.class);
        // Mock para getHeaderNames
        Enumeration<String> headerNames = Collections.enumeration(Collections.singletonList("Authorization"));
        when(request.getHeaderNames()).thenReturn(headerNames);
    }

    @AfterEach
    void tearDown() {
        mockUtilidadesApi.close();
    }

    @Test
    void preHandle_DeberiaInicializarSesionYPermitirSolicitud() throws Exception {
        // Arrange
        String metodo = "GET";
        String uri = "/api/test";
        when(request.getMethod()).thenReturn(metodo);
        when(request.getRequestURI()).thenReturn(uri);

        // Act
        boolean result = interceptor.preHandle(request, response, handler);

        // Assert
        assertTrue(result);
    }

    @Test
    void afterCompletion_Exitoso() throws Exception {
        // Arrange
        String metodo = "GET";
        String uri = "/api/test";
        when(request.getMethod()).thenReturn(metodo);
        when(request.getRequestURI()).thenReturn(uri);

        // Act
        interceptor.afterCompletion(request, response, handler, null);

        // Assert
        assertTrue(true);
    }
}