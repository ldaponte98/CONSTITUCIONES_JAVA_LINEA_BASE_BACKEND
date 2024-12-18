package co.org.ccb.constituciones.lineabase.configuracion;

import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import feign.Logger;
import feign.RequestInterceptor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Value("${client.transversal.access-key}")
    private String transversalAccessKey;
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Aquí puedes agregar headers comunes
            if(UtilidadesApi.session != null && !Strings.isEmpty(UtilidadesApi.session.getToken())) {
                requestTemplate.header("Authorization", "Bearer " + UtilidadesApi.session.getToken());
            }else {
                requestTemplate.header("access-key", transversalAccessKey);
            }
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
