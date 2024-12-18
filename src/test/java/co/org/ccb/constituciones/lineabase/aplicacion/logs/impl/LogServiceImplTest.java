package co.org.ccb.constituciones.lineabase.aplicacion.logs.impl;

import co.org.ccb.constituciones.lineabase.infraestructura.cliente.TransversalClient;
import co.org.ccb.constituciones.lineabase.infraestructura.cliente.request.LogRequest;
import co.org.ccb.constituciones.lineabase.transversal.util.RespuestaBase;
import co.org.ccb.constituciones.lineabase.transversal.util.UsuarioSesion;
import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogServiceImplTest {

    @Mock
    private TransversalClient transversalClient;

    @InjectMocks
    private LogServiceImpl logService;

    private static final String COMPONENTE = "test-component";
    private static final String SERVICIO = "test-service";
    private static final String MENSAJE = "test message";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(logService, "componente", COMPONENTE);
        UtilidadesApi.session = mock(UsuarioSesion.class);
        when(UtilidadesApi.session.getService()).thenReturn(SERVICIO);
    }

    @Test
    void info_deberiaRegistrarLogInfoExitoso() {
        // Arrange
        RespuestaBase respuestaExitosa = new RespuestaBase();
        respuestaExitosa.setExito(true);
        var req = new LogRequest();
        req.setTipo("INFO");
        System.out.println(req.getTipo());
        when(transversalClient.registrarLog(any(LogRequest.class))).thenReturn(respuestaExitosa);

        // Act
        logService.info(MENSAJE);

        // Assert
        verify(transversalClient).registrarLog(argThat(request ->
                request.getComponente().equals(COMPONENTE) &&
                        request.getTipo().equals("INFO") &&
                        request.getServicio().equals(SERVICIO) &&
                        request.getMensaje().equals(MENSAJE)
        ));
    }

    @Test
    void error_deberiaRegistrarLogErrorExitoso() {
        // Arrange
        RespuestaBase respuestaExitosa = new RespuestaBase();
        respuestaExitosa.setExito(true);
        when(transversalClient.registrarLog(any(LogRequest.class))).thenReturn(respuestaExitosa);

        // Act
        logService.error(MENSAJE);

        // Assert
        verify(transversalClient).registrarLog(argThat(request ->
                request.getComponente().equals(COMPONENTE) &&
                        request.getTipo().equals("ERROR") &&
                        request.getServicio().equals(SERVICIO) &&
                        request.getMensaje().equals(MENSAJE)
        ));
    }

    @Test
    void registrar_deberiaManejearRespuestaNoExitosa() {
        // Arrange
        RespuestaBase respuestaNoExitosa = new RespuestaBase();
        respuestaNoExitosa.setExito(false);
        respuestaNoExitosa.setMensaje("Error en el servicio");
        when(transversalClient.registrarLog(any(LogRequest.class))).thenReturn(respuestaNoExitosa);

        // Act
        logService.info(MENSAJE);

        // Assert
        verify(transversalClient).registrarLog(any(LogRequest.class));
    }

    @Test
    void registrar_deberiaManejarExcepcion() {
        // Arrange
        when(transversalClient.registrarLog(any(LogRequest.class))).thenThrow(new RuntimeException("Error de conexión"));

        // Act
        logService.info(MENSAJE);

        // Assert
        verify(transversalClient).registrarLog(any(LogRequest.class));
    }
}