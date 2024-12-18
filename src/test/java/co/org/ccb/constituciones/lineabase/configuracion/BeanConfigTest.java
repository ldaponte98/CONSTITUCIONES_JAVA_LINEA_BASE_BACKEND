package co.org.ccb.constituciones.lineabase.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigTest {

    @InjectMocks
    private BeanConfig beanConfig;

    @Test
    void objectMapper_DeberiaCrearNuevaInstancia() {
        // Act
        ObjectMapper objectMapper = beanConfig.objectMapper();

        // Assert
        assertNotNull(objectMapper, "El ObjectMapper no debería ser null");
        assertTrue(objectMapper instanceof ObjectMapper, "Debería ser una instancia de ObjectMapper");
    }

    @Test
    void objectMapper_DeberiaCrearNuevaInstanciaCadaVez() {
        // Act
        ObjectMapper primerMapper = beanConfig.objectMapper();
        ObjectMapper segundoMapper = beanConfig.objectMapper();

        // Assert
        assertNotNull(primerMapper, "El primer ObjectMapper no debería ser null");
        assertNotNull(segundoMapper, "El segundo ObjectMapper no debería ser null");
        assertNotSame(primerMapper, segundoMapper, "Cada llamada debería crear una nueva instancia");
    }
}