package co.org.ccb.constituciones.lineabase.aplicacion.logs.impl;

import co.org.ccb.constituciones.lineabase.aplicacion.logs.LogService;
import co.org.ccb.constituciones.lineabase.infraestructura.cliente.TransversalClient;
import co.org.ccb.constituciones.lineabase.infraestructura.cliente.request.LogRequest;
import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final TransversalClient transversalClient;
    @Value("${spring.application.name}")
    private String componente;

    @Override
    @Async
    public void info(String mensaje) {
        this.registrar(mensaje, "INFO");
    }

    @Override
    @Async
    public void error(String mensaje) {
        this.registrar(mensaje, "ERROR");
    }
    private void registrar(String mensaje, String tipo){
        log.info("Registrando log asyncronicamente");
        try {
            LogRequest request = LogRequest.builder()
                    .componente(componente)
                    .tipo(tipo)
                    .servicio(UtilidadesApi.session.getService())
                    .mensaje(mensaje)
                    .build();
            var respuesta = this.transversalClient.registrarLog(request);
            if (!respuesta.isExito()){
                log.error("Ocurrio un error con la respuesta exitosa del servicio transversal de registro de logs: " + respuesta.getMensaje());
            }
        }catch (Exception e){
            log.error("Ocurrio un error consumiendo el servicio transversal de registro de logs: " + e.getMessage());
        }
    }
}
