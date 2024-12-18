package co.org.ccb.constituciones.lineabase.transversal.util;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaBase {
    private boolean exito;
    private String mensaje;
    private transient Object detalle;
}
