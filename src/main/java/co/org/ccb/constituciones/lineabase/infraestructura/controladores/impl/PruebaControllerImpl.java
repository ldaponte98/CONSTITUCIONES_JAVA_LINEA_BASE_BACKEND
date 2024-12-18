package co.org.ccb.constituciones.lineabase.infraestructura.controladores.impl;

import co.org.ccb.constituciones.lineabase.aplicacion.logs.LogService;
import co.org.ccb.constituciones.lineabase.infraestructura.controladores.PruebaController;
import co.org.ccb.constituciones.lineabase.infraestructura.entrada.PruebaRequest;
import co.org.ccb.constituciones.lineabase.transversal.util.RespuestaBase;
import co.org.ccb.constituciones.lineabase.transversal.util.UtilidadesAplicacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class PruebaControllerImpl implements PruebaController {
    private final LogService logService;
    @Override
    @PostMapping("/hola-mundo")
    public ResponseEntity<RespuestaBase> probar(PruebaRequest request) {
        this.logService.info("Hola mundo desde linea base");
        var respuesta = UtilidadesAplicacion.responder("OK", null);
        return ResponseEntity.ok(respuesta);
    }
}
