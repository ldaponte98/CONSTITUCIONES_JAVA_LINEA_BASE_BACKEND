package co.org.ccb.constituciones.lineabase.configuracion;

import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Aquí puedes agregar headers comunes
            requestTemplate.header("Authorization", "Bearer " + UtilidadesApi.session.getToken());
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
