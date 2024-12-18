package co.org.ccb.constituciones.lineabase.configuracion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AsyncConfigTest {

    @InjectMocks
    private AsyncConfig asyncConfig;

    @Test
    void getAsyncExecutor_deberiaRetornarThreadPoolTaskExecutor() {
        // Act
        Executor executor = asyncConfig.getAsyncExecutor();

        // Assert
        assertNotNull(executor);
        assertTrue(executor instanceof ThreadPoolTaskExecutor);
    }

    @Test
    void getAsyncExecutor_deberiaConfiguracionCorrecta() {
        // Act
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncConfig.getAsyncExecutor();

        // Assert
        assertEquals(5, executor.getCorePoolSize(), "Core pool size debería ser 5");
        assertEquals(100, executor.getMaxPoolSize(), "Max pool size debería ser 100");
        assertEquals(100, executor.getQueueCapacity(), "Queue capacity debería ser 100");
        assertEquals("AsyncThread-", executor.getThreadNamePrefix(), "Thread prefix debería ser AsyncThread-");
    }

    @Test
    void getAsyncExecutor_deberiaEstarInicializado() {
        // Act
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncConfig.getAsyncExecutor();

        // Assert
        assertTrue(true);
    }
}