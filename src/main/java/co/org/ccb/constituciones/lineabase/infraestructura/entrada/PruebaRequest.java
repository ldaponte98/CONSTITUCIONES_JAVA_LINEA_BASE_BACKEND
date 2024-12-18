package co.org.ccb.constituciones.lineabase.infraestructura.entrada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PruebaRequest {
    private String variable;
}
