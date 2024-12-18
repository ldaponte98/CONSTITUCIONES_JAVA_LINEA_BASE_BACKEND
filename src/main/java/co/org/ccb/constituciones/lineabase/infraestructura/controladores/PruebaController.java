package co.org.ccb.constituciones.lineabase.infraestructura.controladores;

import co.org.ccb.constituciones.lineabase.infraestructura.entrada.PruebaRequest;
import co.org.ccb.constituciones.lineabase.transversal.util.RespuestaBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PruebaController {
    @Tag(name = "Prueba")
    @SecurityRequirement(name="bearerAuth")
    @Operation(summary = "Hola mundo", description = "Devuelve un hola mundo y consume el servicio transversal de logs registrando una actividad en el log.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Exitoso",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = RespuestaBase.class))),

            })
    ResponseEntity<RespuestaBase> probar(@RequestBody PruebaRequest request);
}
