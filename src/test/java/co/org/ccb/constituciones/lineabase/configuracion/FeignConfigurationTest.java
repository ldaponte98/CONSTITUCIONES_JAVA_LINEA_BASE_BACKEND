package co.org.ccb.constituciones.lineabase.configuracion;

import co.org.ccb.constituciones.lineabase.transversal.util.UsuarioSesion;
import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FeignConfigurationTest {

    private FeignConfiguration feignConfiguration;

    @Mock
    private RequestTemplate requestTemplate;

    @Mock
    private UsuarioSesion session;

    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        feignConfiguration = new FeignConfiguration();
        UtilidadesApi.session = session;
        UtilidadesApi.session.setToken(TOKEN);
        when(session.getToken()).thenReturn(TOKEN);
    }

    @Test
    void feignLoggerLevel_deberiaRetornarNivelFull() {
        // Act
        var level = feignConfiguration.feignLoggerLevel();

        // Assert
        assertThat(level).isEqualTo(feign.Logger.Level.FULL);
    }

    @Test
    void requestInterceptor_deberiaAgregarHeadersCorrectos() {
        // Arrange
        var interceptor = feignConfiguration.requestInterceptor();

        // Act
        interceptor.apply(requestTemplate);

        // Assert
        verify(requestTemplate).header("Authorization", "Bearer " + TOKEN);
        verify(requestTemplate).header("Content-Type", "application/json");
    }

    @Test
    void requestInterceptor_deberiaAgregarHeadersConTokenNulo() {
        // Arrange
        when(session.getToken()).thenReturn(null);
        var interceptor = feignConfiguration.requestInterceptor();

        // Act
        interceptor.apply(requestTemplate);

        // Assert
        verify(requestTemplate).header("Authorization", "Bearer " + null);
        verify(requestTemplate).header("Content-Type", "application/json");
    }
}