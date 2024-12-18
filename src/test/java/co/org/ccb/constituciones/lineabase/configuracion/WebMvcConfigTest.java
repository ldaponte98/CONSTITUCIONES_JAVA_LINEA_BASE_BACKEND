package co.org.ccb.constituciones.lineabase.configuracion;

import co.org.ccb.constituciones.lineabase.infraestructura.interceptores.ValidarAutenticacionInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebMvcConfigTest {

    @Mock
    private ValidarAutenticacionInterceptor validarAutenticacionInterceptor;

    @Mock
    private InterceptorRegistry registry;

    @Mock
    private InterceptorRegistration interceptorRegistration;

    @InjectMocks
    private WebMvcConfig webMvcConfig;

    @BeforeEach
    void setUp() {
        when(registry.addInterceptor(any(ValidarAutenticacionInterceptor.class)))
                .thenReturn(interceptorRegistration);
        when(interceptorRegistration.addPathPatterns(any(String.class)))
                .thenReturn(interceptorRegistration);
        when(interceptorRegistration.excludePathPatterns(any(String[].class)))
                .thenReturn(interceptorRegistration);
    }

    @Test
    void addInterceptors_DeberiaManternerOrdenCorrecto() {
        // Act
        webMvcConfig.addInterceptors(registry);

        // Assert
        InOrder inOrder = inOrder(registry, interceptorRegistration);

        // Verifica el orden de las llamadas
        inOrder.verify(registry).addInterceptor(validarAutenticacionInterceptor);
        inOrder.verify(interceptorRegistration).addPathPatterns("/**");
        inOrder.verify(interceptorRegistration).excludePathPatterns(any(String[].class));
    }
}